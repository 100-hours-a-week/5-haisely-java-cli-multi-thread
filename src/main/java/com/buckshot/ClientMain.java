package com.buckshot;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Base64;
import java.util.Scanner;

public class ClientMain {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 13579;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to the server");

            Thread readThread = new Thread(() -> {
                try {
                    String serverResponse;
                    while ((serverResponse = in.readLine()) != null) {
                        byte[] decodedBytes = Base64.getDecoder().decode(serverResponse);
                        System.out.println(new String(decodedBytes));
                    }
                } catch (SocketException e) {
                    System.out.println("Connection reset by server");
                } catch (IOException e) {
                    System.out.println("Error reading from server: " + e.getMessage());
                    e.printStackTrace();
                } finally {
                    System.out.println("Server connection closed");
                    System.exit(0);  // 프로그램을 안전하게 종료
                }
            });
            readThread.start();

            // 사용자 입력을 서버로 보냅니다.
            while (true) {
                String message = scanner.nextLine();
                out.println(message);
            }

        } catch (IOException e) {
            System.out.println("Client exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
