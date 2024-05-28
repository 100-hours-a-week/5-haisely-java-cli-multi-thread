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
    private boolean readMessageFlag = false;
    private volatile String message = null;

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
            while (isConnected) {
                String msg = _readMessage();
                synchronized (lock) {
                    if (!readMessageFlag) {
                         enemyThread.sendMessage("상대 : " + msg);
                    } else {
                        message = msg;
                        lock.notify();
                        lock.wait();
                    }
                }
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                closeSocket();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeSocket() throws IOException {
        isConnected = false;
        socket.close();
    }

    public void sendMessage(String message) {
        if (isConnected) {
            String encodedMessage = Base64.getEncoder().encodeToString(message.getBytes());
            writer.println(encodedMessage);
        }
    }

    public String readMessage() throws SocketException, IOException, InterruptedException {
        synchronized (lock) {
            readMessageFlag = true;
            lock.wait();
            readMessageFlag = false;
            lock.notify();
            return message;
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