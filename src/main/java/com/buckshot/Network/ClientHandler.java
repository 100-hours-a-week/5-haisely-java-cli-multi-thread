package com.buckshot.Network;
import com.buckshot.Core.User;

import java.io.*;
import java.net.*;
import java.util.Base64;

public class ClientHandler implements Runnable {
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private String currentMessage;
    public ClientHandler(Socket socket) {
        this.socket = socket;
        try {
            InputStream input = socket.getInputStream();
            this.reader = new BufferedReader(new InputStreamReader(input));
            OutputStream output = socket.getOutputStream();
            this.writer = new PrintWriter(output, true);
        } catch (IOException e) {
            System.out.println("Error setting up streams: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
    }

    public void closeSocket() throws IOException {
        socket.close();
    }
    public void sendMessage(String message) {
        String encodedMessage = Base64.getEncoder().encodeToString(message.getBytes());
        writer.println(encodedMessage);
    }

    public String readMessage(){
        try {
            return reader.readLine();
        } catch (IOException e) {
            System.out.println("Error reading message: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
