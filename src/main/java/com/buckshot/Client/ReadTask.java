package com.buckshot.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReadTask implements Runnable {
    private BufferedReader reader;
    private Socket socket;
    private CustomInputStream customInputStream;

    public ReadTask(Socket socket, CustomInputStream customInputStream) {
        this.socket = socket;
        this.customInputStream = customInputStream;

        try {
            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
        } catch (IOException ex) {
            System.out.println("Error getting input stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void run() {
        try {
            while (true) {
                String response = reader.readLine();
                if (response == null) {
                    System.out.println("Server closed the connection.");
                    break;
                }
                System.out.println("Server: " + response);
            }
        } catch (IOException ex) {
            if (socket.isClosed()) {
                System.out.println("Socket closed, stopping read task.");
            } else {
                System.out.println("Error reading from server: " + ex.getMessage());
                ex.printStackTrace();
            }
        } finally {
            try {
                socket.close();
            } catch (IOException ex) {
                System.out.println("Error closing socket: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
}
