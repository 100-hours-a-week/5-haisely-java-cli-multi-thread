package com.buckshot;
import java.io.*;
import java.net.Socket;
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

            // 새로운 스레드를 시작하여 서버로부터 메시지를 읽습니다.
            Thread readThread = new Thread(() -> {
                try {
                    String serverResponse;
                    while ((serverResponse = in.readLine()) != null) {
                        System.out.println("Server: \n" + serverResponse);
                    }
                } catch (IOException e) {
                    System.out.println("Error reading from server: " + e.getMessage());
                    e.printStackTrace();
                }
            });
            readThread.start();

            // 사용자 입력을 서버로 보냅니다.
            while (true) {
                System.out.print("You: ");
                String message = scanner.nextLine();
                out.println(message);
            }

        } catch (IOException e) {
            System.out.println("Client exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
