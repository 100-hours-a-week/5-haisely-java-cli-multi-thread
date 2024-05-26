package com.buckshot.Network;
import com.buckshot.Core.User;

import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    private Socket socket;
    private PrintWriter writer;
    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (InputStream input = socket.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(input));
             OutputStream output = socket.getOutputStream();
             PrintWriter writer = new PrintWriter(output, true)) {

            String clientMessage;

            // 클라이언트가 연결을 끊을 때까지 메시지를 계속 읽음
            while ((clientMessage = reader.readLine()) != null) {
                System.out.println("Received from client: " + clientMessage);
                writer.println("Echo: " + clientMessage); // 클라이언트에게 에코 메시지 전송
            }

        } catch (IOException e) {
            System.out.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Couldn't close a socket");
            }
            System.out.println("Connection with client closed");
        }
    }

    public void closeSocket() throws IOException {
        socket.close();
    }
    public void sendMessage(String message) {
        writer.println(message);
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
