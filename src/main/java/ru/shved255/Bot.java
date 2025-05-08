package ru.shved255;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Bot {

	public String startBot(String host, int port, String version, boolean mode) {
	    StringBuilder output = new StringBuilder();
	    int attempt = 0;
	    int maxAttempts = mode ? 2 : 1;
	    while(attempt < maxAttempts) {
	        try {
	            ProcessBuilder processBuilder = new ProcessBuilder(
	                "node", "script.js", host, String.valueOf(port), version
	            );
	            processBuilder.redirectErrorStream(true);
	            Process botProcess = processBuilder.start();
	            BufferedReader reader = new BufferedReader(new InputStreamReader(botProcess.getInputStream()));
	            String line;
	            while((line = reader.readLine()) != null) {
	                output.append(line).append("\n");
	            }
	            int exitCode = botProcess.waitFor();
	            if(exitCode == 0) {
	                String result = output.toString().trim();
	                if(result.startsWith("Kicked:")) {
	                	botProcess.destroy();
	                    return result.substring("Kicked:".length()).trim();
	                } else if(result.startsWith("Kicked: Bot")) {
	                	botProcess.destroy();
	                    return result;
	                } else {
	                    attempt++;
	                    if(attempt < maxAttempts) {
	                        output.setLength(0); 
	                        continue;
	                    } else {
	                    	botProcess.destroy();
	                        return "Error";
	                    }
	                }
	            } else {
	                attempt++;
	                if(attempt < maxAttempts) {
	                    output.setLength(0);
	                    continue;
	                } else {
	                	botProcess.destroy();
	                    return "Error";
	                }
	            }
	        } catch (IOException | InterruptedException e) {
	            e.printStackTrace();
	            attempt++;
	            if(attempt < maxAttempts) {
	                output.setLength(0);
	                continue;
	            } else {
	                return "Error: " + e.getMessage();
	            }
	        }
	    }
	    return "Error";
	}
}