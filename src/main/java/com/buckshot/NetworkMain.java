package com.buckshot;

import com.buckshot.Core.Gun;
import com.buckshot.Core.User;
import com.buckshot.Manager.GameManager;
import com.buckshot.Network.ClientHandler;
import com.buckshot.Network.NetworkGameManager;
import com.buckshot.Network.NetworkUser;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkMain {
    private static final int PORT = 13579;
    private static final int MAX_CLIENTS = 2;
    private static NetworkUser[] players = new NetworkUser[MAX_CLIENTS];
    private static Thread[] playerThreads = new Thread[MAX_CLIENTS];

    public static void main(String[] args) {
        players[0] = new NetworkUser();
        players[1] = new NetworkUser();
        Gun gun = new Gun();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);

            for (int i = 0; i < MAX_CLIENTS; i++) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                ClientHandler clientHandler = new ClientHandler(socket);
                playerThreads[i] = new Thread(clientHandler);
                players[i].setHandler(clientHandler);
                playerThreads[i].start();
            }

            System.out.println("Both players connected. Starting the game...");
            // 게임 시작 로직
            game(players[0], players[1], gun, players[0].getHandler(), players[1].getHandler());
        } catch (IOException e) {
            System.out.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public static void game(NetworkUser player1, NetworkUser player2, Gun gun, ClientHandler p1, ClientHandler p2){
        NetworkGameManager gm = new NetworkGameManager(player1, player2, gun, p1, p2);
        try {
            gm.startGame();
            gm.startRound();
            player1.myTurn();
            player2.myTurn();
//            for (int i = 0; i < 10; i++) {
//                gm.startRound();
//                while (gm.canTurn()) {
//                    if (player1.getMyTurn()) {
//                        player1.myTurn();
//                    }
//                    if (!gm.canTurn()) break;
//                    player2.myTurn();
//                }
//                if (!gm.canRound()) {
//                    gm.endGame();
//                    return;
//                }
//            }
//            gm.drawGame();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            gm.scannerClose();
        }
    }
}