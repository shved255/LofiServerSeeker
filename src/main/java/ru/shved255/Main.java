package ru.shved255;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.LinkedHashSet;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

    private int timeout;
    private int threadsAmount;
    private boolean mode;
    private int record;
    private boolean botOn;
    private boolean core;
    private boolean optim;
    private ForkJoinPool forkJoinPool;
    private Bot bot = new Bot();
    private LinkedHashSet<HostingAddress> addresses = new LinkedHashSet<>();
    private volatile boolean running;
    private int serverCount = 0;
    private int cycleCount = 0;
    private static TextArea console;
    private Button stopButton;

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #2E2E2E;");
        VBox settingsPanel = new VBox(10);
        settingsPanel.setPadding(new Insets(20));
        settingsPanel.setStyle("-fx-background-color: #3C3F41;");
        HBox threadsBox = createInputBox("Количество потоков:", "15");
        TextField threadsField = (TextField) threadsBox.getChildren().get(1);
        HBox timeoutBox = createInputBox("Таймаут (мс):", "100");
        TextField timeoutField = (TextField) timeoutBox.getChildren().get(1);
        HBox recordBox = createInputBox("Рекорд игроков:", "20");
        TextField recordField = (TextField) recordBox.getChildren().get(1);
        CheckBox modeCheck = new CheckBox("Включить ли попытки");
        modeCheck.setTextFill(Color.WHITE);
        modeCheck.setSelected(true);
        modeCheck.setFont(Font.font("Arial", 14));
        CheckBox botCheck = new CheckBox("Включить бота");
        botCheck.setTextFill(Color.WHITE);
        botCheck.setSelected(true);
        botCheck.setFont(Font.font("Arial", 14));
        CheckBox coreCheck = new CheckBox("Включить многопоточность");
        coreCheck.setTextFill(Color.WHITE);
        coreCheck.setSelected(true);
        coreCheck.setFont(Font.font("Arial", 14));
        CheckBox optimCheck = new CheckBox("Включить ли оптимизацию поиска?");
        optimCheck.setTextFill(Color.WHITE);
        optimCheck.setSelected(true);
        optimCheck.setFont(Font.font("Arial", 14));
        Label statsLabel = new Label("Найдено серверов: 0 | Прокрутов: 0");
        statsLabel.setTextFill(Color.WHITE);
        statsLabel.setFont(Font.font("Arial", 16));
        Button startButton = new Button("Старт");
        startButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");
        startButton.setPrefWidth(100);
        startButton.setOnAction(e -> {
            try {
                threadsAmount = Integer.parseInt(threadsField.getText());
                timeout = Integer.parseInt(timeoutField.getText());
                record = Integer.parseInt(recordField.getText());
                mode = modeCheck.isSelected();
                botOn = botCheck.isSelected();
                core = coreCheck.isSelected();
                optim = optimCheck.isSelected();
                running = true;
                startButton.setDisable(true);
                stopButton.setDisable(false);
                startScanning(statsLabel);
            } catch (NumberFormatException ex) {
                console.appendText("Ошибка: Введите корректные числовые значения\n");
            }
        });
        stopButton = new Button("Стоп");
        stopButton.setStyle("-fx-background-color: #E53935; -fx-text-fill: white; -fx-font-size: 14px;");
        stopButton.setPrefWidth(100);
        stopButton.setOnAction(e -> {
        	startButton.setDisable(false);
            running = false;
        	stopButton.setDisable(true);
        });
        settingsPanel.getChildren().addAll(threadsBox, timeoutBox, recordBox, modeCheck, botCheck, coreCheck, optimCheck, startButton, stopButton, statsLabel);
        console = new TextArea();
        console.setEditable(false);
        console.setStyle("-fx-control-inner-background: #1E1E1E; -fx-text-fill: white; -fx-font-family: 'Courier New'; -fx-font-size: 12px;");
        console.setPrefHeight(400);
        root.setTop(settingsPanel);
        root.setCenter(console);
        Scene scene = new Scene(root, 600, 600);
        primaryStage.setTitle("Сканер серверов");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(e -> {
            shutdownForkJoinPool();
            Platform.exit();
            System.exit(0);
        });
        primaryStage.show();
    }

    private HBox createInputBox(String labelText, String defaultValue) {
        HBox box = new HBox(10);
        Label label = new Label(labelText);
        label.setTextFill(Color.WHITE);
        label.setFont(Font.font("Arial", 14));
        TextField field = new TextField(defaultValue);
        field.setStyle("-fx-background-color: #4B4B4B; -fx-text-fill: white;");
        box.getChildren().addAll(label, field);
        return box;
    }

    private void startScanning(Label statsLabel) {
        File file = new File("servers.txt");
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            console.appendText("Ошибка создания файла: " + e.getMessage() + "\n");
        }

        if(core) {
            forkJoinPool = new ForkJoinPool(threadsAmount);
            Semaphore semaphore = new Semaphore(threadsAmount);
            new Thread(() -> {
                while(running) {
                    try {
                        semaphore.acquire();
                        CompletableFuture.runAsync(() -> {
                            try {
                                processAddress(statsLabel);
                            } finally {
                                semaphore.release();
                            }
                        }, forkJoinPool);
                    } catch (InterruptedException e) {
                        console.appendText("Ошибка: " + e.getMessage() + "\n");
                    }
                }
            }).start();
        } else {
            new Thread(() -> {
                while(running) {
                    processAddress(statsLabel);
                    System.gc();
                }
            }).start();
        }
    }

    private void processAddress(Label statsLabel) {
        HostingAddress address;
        if(optim) {
        	address = HostingAddress.generateOp(true, timeout);
        } else {
            address = HostingAddress.generate(true, timeout);
        }
        cycleCount++;
        Platform.runLater(() -> {
            statsLabel.setText(String.format("Найдено серверов: %d | Прокрутов: %d", serverCount, cycleCount));
        });
        if(addresses.add(address)) {
            if(fetchServerData(timeout, address)) {
                serverCount++;
                String kick = "not";
                Platform.runLater(() -> {
                    console.appendText("Найден новый сервер: " + address.getIp() + ":" + address.getPort() + "\n");
                });
                if(botOn) {
                    kick = bot.startBot(address.getIp(), Integer.parseInt(address.getPort()), address.getPinger().getGameVersion(), mode);
                }
                String serverInfo = formatServerInfo(address, kick);
                writeServerInfoAsync(serverInfo);
            }
        }
    }

    private boolean fetchServerData(int timeout, HostingAddress address) {
        return timeout == 0 ? address.fetch(false) : timeout > 0 && address.fetch(true);
    }

    private String formatServerInfo(HostingAddress address, String kickS) {
        String ip = address.getIp() + ":" + address.getPort();
        int players = address.getPinger().getPlayersOnline();
        String recordTag = players >= record ? " RECORD!!!" : "";
        String kick = !kickS.equals("not") ? "\tКик бота: " + kickS : "";
        return String.format("%d. %s\tИгроки: %d/%d\tВерсия: %s\tОписание: %s %s \t%s\n",
                getLineCount() + 1, ip, players, address.getPinger().getMaxPlayers(),
                address.getPinger().getGameVersion(), address.getPinger().getMotd(), kick, recordTag);
    }

    public static int getLineCount() {
        String filePath = "servers.txt";
        int lineCount = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            while(reader.readLine() != null) {
                lineCount++;
            }
        } catch (IOException e) {
            console.appendText("Ошибка чтения файла: " + e.getMessage() + "\n");
        }
        return lineCount;
    }

    private void writeServerInfoAsync(String serverInfo) {
        Path path = Path.of("servers.txt");
        try (AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE, StandardOpenOption.CREATE)) {
            ByteBuffer buffer = ByteBuffer.wrap(serverInfo.getBytes());
            Future<Integer> writeResult = fileChannel.write(buffer, fileChannel.size());
            writeResult.get();
        } catch (Exception e) {
            console.appendText("Ошибка записи в файл: " + e.getMessage() + "\n");
        }
    }

    private void shutdownForkJoinPool() {
        if(forkJoinPool != null) {
            forkJoinPool.shutdown();
            try {
                forkJoinPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException e) {
                console.appendText("Ошибка завершения пула: " + e.getMessage() + "\n");
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}