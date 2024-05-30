package com.buckshot;

import com.buckshot.Network.ClientHandler;
import com.buckshot.Network.NetworkGameManager;
import com.buckshot.Network.NetworkGun;
import com.buckshot.Network.NetworkUser;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkMain {
    private static final int PORT = 13579;
    private static final int MAX_CLIENTS = 2;
    private static NetworkUser[] players = new NetworkUser[MAX_CLIENTS];
    private static Thread[] playerThreads = new Thread[MAX_CLIENTS];
    private static volatile boolean serverRunning = true;

    public static void main(String[] args) {
        players[0] = new NetworkUser();
        players[1] = new NetworkUser();
        NetworkGun gun = new NetworkGun();

        Thread.UncaughtExceptionHandler handler = (thread, throwable) -> {
            System.out.println("Exception in thread: " + thread.getName() + " - " + throwable.getMessage());
            throwable.printStackTrace();
            stopServer();
        };

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);

            for (int i = 0; i < MAX_CLIENTS; i++) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                ClientHandler clientHandler = new ClientHandler(socket);
                Thread thread = new Thread(clientHandler);
                thread.setUncaughtExceptionHandler(handler);
                playerThreads[i] = thread;
                players[i].setHandler(clientHandler);
                playerThreads[i].start();
            }

            System.out.println("Both players connected. Starting the game...");
            game(players[0], players[1], gun, players[0].getHandler(), players[1].getHandler());
        } catch (IOException e) {
            System.out.println("Server exception: " + e.getMessage());
        } finally {
            stopServer();
        }
    }

    private static void stopServer() {
        if (!serverRunning) return;
        serverRunning = false;
        try {
            if (!players[0].getHandler().isConnected()) {
                players[1].getHandler().sendMessage("상대 플레이어가 떠나 게임이 종료됩니다.");
            } else if (!players[1].getHandler().isConnected()) {
                players[0].getHandler().sendMessage("상대 플레이어가 떠나 게임이 종료됩니다.");
            }
        } catch (Exception e) {
            System.out.println("Server exception: " + e.getMessage());
        }

        for (int i = 0; i < MAX_CLIENTS; i++) {
            if (players[i].getHandler() != null) {
                try {
                    players[i].getHandler().closeSocket();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.exit(0);
    }


    public static void game(NetworkUser player1, NetworkUser player2, NetworkGun gun, ClientHandler p1, ClientHandler p2){
        NetworkGameManager gm = new NetworkGameManager(player1, player2, gun, p1, p2);
        try {
            gm.startGame();
            for (int i = 0; i < 10; i++) {
                gm.startRound();
                while (gm.canTurn()&&gm.isConnectedUsers()) {
                    if (player1.getMyTurn()) {
                        gm.userTurn(player1);
                    }
                    if (!gm.canTurn()||!gm.isConnectedUsers()) break;
                    gm.userTurn(player2);
                }
                if (!gm.canRound()) {
                    gm.endGame();
                    return;
                }
                if (!gm.isConnectedUsers()){
                    gm.handleDisconnection();
                    return;
                }
            }
            gm.drawGame();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}