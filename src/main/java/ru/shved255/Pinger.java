package ru.shved255;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.Charset;

public class Pinger {
	
	public static int TIMEOUT;
    private String address;
    private int port;
    private int timeout;
    private int pingVersion;
    private int protocolVersion;
    private String gameVersion;
    private String motd;
    private int playersOnline;
    private int maxPlayers;
    private int ping;
    
    public Pinger(final String address, final int port, int timeout) {
        this.address = "localhost";
        this.port = 25565;
        this.timeout = timeout;
        this.pingVersion = -1;
        this.protocolVersion = -1;
        this.playersOnline = -1;
        this.maxPlayers = -1;
        this.setAddress(address);
        this.setPort(port);
    }
    
    public void setAddress(final String address) {
        this.address = address;
    }
    
    public String getAddress() {
        return this.address;
    }
    
    public void setPort(final int port) {
        this.port = port;
    }
    
    public int getPort() {
        return this.port;
    }
    
    public void setTimeout(final int timeout) {
        this.timeout = timeout;
    }
    
    public int getTimeout() {
        return this.timeout;
    }
    
    private void setPingVersion(final int pingVersion) {
        this.pingVersion = pingVersion;
    }
    
    public int getPingVersion() {
        return this.pingVersion;
    }
    
    private void setProtocolVersion(final int protocolVersion) {
        this.protocolVersion = protocolVersion;
    }
    
    public int getProtocolVersion() {
        return this.protocolVersion;
    }
    
    private void setGameVersion(final String gameVersion) {
        this.gameVersion = gameVersion;
    }
    
    public String getGameVersion() {
        return this.gameVersion;
    }
    
    private void setMotd(final String motd) {
        this.motd = motd;
    }
    
    public String getMotd() {
        return this.motd;
    }
    
    private void setPlayersOnline(final int playersOnline) {
        this.playersOnline = playersOnline;
    }
    
    public int getPlayersOnline() {
        return this.playersOnline;
    }
    
    private void setMaxPlayers(final int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }
    
    public int getMaxPlayers() {
        return this.maxPlayers;
    }
    
    public int getPing() {
    	return this.ping;
    }
    
    private void setPing(final int ping) {
        this.ping = ping;
    }
    
    public boolean fetchData(boolean timeout) {
        try {
            Socket socket = new Socket();
            long startTime = System.currentTimeMillis();
            if (timeout) {
                socket.connect(new InetSocketAddress(this.getAddress(), this.getPort()), this.getTimeout());
            } else {
                socket.connect(new InetSocketAddress(this.getAddress(), this.getPort()));
            }
            setPing((int) (System.currentTimeMillis() - startTime));
            final OutputStream outputStream = socket.getOutputStream();
            final DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            final InputStream inputStream = socket.getInputStream();
            final InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-16BE"));
            dataOutputStream.write(new byte[] { -2, 1 });
            final int packetId = inputStream.read();
            if (packetId == -1) {
                try {
                    socket.close();
                }
                catch (IOException ex) {}
                socket = null;
                return false;
            }
            if (packetId != 255) {
                try {
                    socket.close();
                }
                catch (IOException ex2) {}
                socket = null;
                return false;
            }
            final int length = inputStreamReader.read();
            if (length == -1) {
                try {
                    socket.close();
                }
                catch (IOException ex3) {}
                socket = null;
                return false;
            }
            if (length == 0) {
                try {
                    socket.close();
                }
                catch (IOException ex4) {}
                socket = null;
                return false;
            }
            final char[] chars = new char[length];
            if (inputStreamReader.read(chars, 0, length) != length) {
                try {
                    socket.close();
                }
                catch (IOException ex5) {}
                socket = null;
                return false;
            }
            final String string = new String(chars);
            if (string.startsWith("§")) {
                final String[] data = string.split("\u0000");
                this.setPingVersion(Integer.parseInt(data[0].substring(1)));
                this.setProtocolVersion(Integer.parseInt(data[1]));
                this.setGameVersion(data[2]);
                this.setMotd(data[3]);
                this.setPlayersOnline(Integer.parseInt(data[4]));
                this.setMaxPlayers(Integer.parseInt(data[5]));
            }
            else {
                final String[] data = string.split("§");
                this.setMotd(data[0]);
                this.setPlayersOnline(Integer.parseInt(data[1]));
                this.setMaxPlayers(Integer.parseInt(data[2]));
            }
            dataOutputStream.close();
            outputStream.close();
            inputStreamReader.close();
            inputStream.close();
            socket.close();
        }
        catch (SocketException exception) {
            return false;
        }
        catch (IOException exception2) {
            return false;
        }
        return true;
    }
}
