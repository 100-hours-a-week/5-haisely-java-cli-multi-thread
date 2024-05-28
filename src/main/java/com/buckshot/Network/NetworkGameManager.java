package com.buckshot.Network;

import com.buckshot.Core.Gun;
import com.buckshot.Core.User;
import com.buckshot.Manager.AsciiArt;
import com.buckshot.Manager.GameManager;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
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
                randomItems(p1);
                randomItems(p2);
                return;
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
        broadcastMessage(this.round + " Round가 시작됩니다.\n");
        AsciiArt.sleepMillis(1000);
        randomBullets(this.gun);
        this.round +=1;
    }

    @Override
    public void drawGame(){

    }

    @Override
    public void randomBullets(Gun gun){
        ArrayList<Integer> newBullets = new ArrayList<Integer>();
        int num = 3+(int)(Math.random()*6);
        int limit = (int)(num/3);
        int real = limit + (int)(Math.random()*(num-2*limit+1));
        broadcastMessage("총알이 "+num+" 개 장전됩니다.\n");
        AsciiArt.sleepMillis(1000);

        for (int i = 0; i<num; i++){
            if(i<real){
                newBullets.add(1);
            }else{
                newBullets.add(0);
            }
            broadcastMessage("찰칵\n");
            AsciiArt.sleepMillis(230);
        }
        AsciiArt.sleepMillis(270);
        broadcastMessage("실탄 "+real+" 개, 공포탄 "+ (num-real)+"개가 장전되었습니다.\n");
        AsciiArt.sleepMillis(2000);
        Collections.shuffle(newBullets);
        gun.setBullets(newBullets);
        return;
    }

    public void UserTurn(User user){

    }

    public void broadcastMessage(String message){
        p1thread.sendMessage(message);
        p2thread.sendMessage(message);
    }


}
