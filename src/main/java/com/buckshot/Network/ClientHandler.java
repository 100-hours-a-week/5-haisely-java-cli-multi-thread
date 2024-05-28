package com.buckshot.Network;

import com.buckshot.Manager.GameManager;

import java.io.*;
import java.net.*;
import java.util.Base64;

public class ClientHandler implements Runnable {
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private final Object lock = new Object();
    private boolean isConnected;
    private ClientHandler enemyThread;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        this.isConnected = true;
        try {
            InputStream input = socket.getInputStream();
            this.reader = new BufferedReader(new InputStreamReader(input));
            OutputStream output = socket.getOutputStream();
            this.writer = new PrintWriter(output, true);
        } catch (IOException e) {
            System.out.println("Error setting up streams: " + e.getMessage());
            e.printStackTrace();
            this.isConnected = false;
        }
    }

    @Override
    public void run() {
        try {
            String message = _readMessage();
            enemyThread.sendMessage("상대 : "+message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Implement any code needed to handle client communication
    }

    public void closeSocket() throws IOException {
        socket.close();
    }

    public void sendMessage(String message) {
        if (isConnected) {
            String encodedMessage = Base64.getEncoder().encodeToString(message.getBytes());
            writer.println(encodedMessage);
        }
    }

    public String readMessage() throws SocketException, IOException {
        synchronized (lock) {
            return _readMessage();
        }
    }

    private String _readMessage() throws SocketException, IOException {
        return reader.readLine();
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setEnemyThread(ClientHandler thread){
        this.enemyThread = thread;
    }
}