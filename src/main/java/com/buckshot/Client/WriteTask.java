package com.buckshot.Client;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class WriteTask implements Runnable {
    private PrintWriter writer;
    private Socket socket;
    private Scanner scanner;

    public WriteTask(Socket socket) {
        this.socket = socket;
        scanner = new Scanner(System.in);

        try {
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
        } catch (IOException ex) {
            System.out.println("Error getting output stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            String message = scanner.nextLine();
            writer.println(message);
        }
    }
}
