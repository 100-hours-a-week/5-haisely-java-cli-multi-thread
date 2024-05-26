package com.buckshot.Network;

import com.buckshot.Core.Gun;
import com.buckshot.Manager.AsciiArt;
import com.buckshot.Manager.GameManager;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.InputMismatchException;

public class NetworkGameManager extends GameManager {
    private ClientHandler p1thread;
    private ClientHandler p2thread;
    public NetworkGameManager(NetworkUser p1, NetworkUser p2, Gun gun, ClientHandler p1thread, ClientHandler p2thread) {
        super(p1, p2, gun);
        this.p1thread = p1thread;
        this.p2thread = p2thread;
    }

    @Override
    public void startGame(){
        for (int i = 0; i < 10; i++) {
            // 메시지 출력
            try {
                p1thread.sendMessage("Player 1의 이름을 작성하세요.");
                String playerName1 = p1thread.readMessage();
                p1.setName(playerName1);
                System.out.println(p1.getName());
                p2thread.sendMessage("Player 2의 이름을 작성하세요.");
                String playerName2 = p2thread.readMessage();
                p2.setName(playerName2);
                System.out.println(p2.getName());
                broadcastMessage("Player 1의 이름 : "+p1.getName() +"\nPlayer 2의 이름 : "+p2.getName()+"\n");
                return;
//                AsciiArt.printCenteredString("Player 1: " + playerName1 + '\n', 0);
//                AsciiArt.sleepMillis(500);
//                AsciiArt.printCenteredStringPretty("Player 2의 이름을 작성하세요.", 3);
//                AsciiArt.printCenteredString("   >  ", 8);
//                String playerName2 = scanner.nextLine();
//                p2.setName(playerName2);
//                AsciiArt.printCenteredString("Player 2: " + playerName2 + '\n', 0);
//                AsciiArt.sleepMillis(500);
//                randomItems(p1);
//                randomItems(p2);
//                return;
            } catch (InputMismatchException e) {
                System.out.println("잘못된 입력입니다.");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("정상적으로 게임이 시작되지 않아 종료합니다.");
        scannerClose();
        System.exit(1); // 프로그램 종료
    }

    @Override
    public void startRound(){

    }

    @Override
    public void drawGame(){

    }

    public void broadcastMessage(String message){
        p1thread.sendMessage(message);
        p2thread.sendMessage(message);
    }


}
