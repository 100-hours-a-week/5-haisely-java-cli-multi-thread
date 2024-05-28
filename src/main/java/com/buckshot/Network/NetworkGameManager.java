package com.buckshot.Network;

import com.buckshot.Core.Gun;
import com.buckshot.Manager.AsciiArt;
import com.buckshot.Manager.GameManager;


import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;

public class NetworkGameManager extends GameManager {
    private ClientHandler p1thread;
    private ClientHandler p2thread;
    public NetworkGameManager(NetworkUser p1, NetworkUser p2, NetworkGun gun, ClientHandler p1thread, ClientHandler p2thread) {
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
        AsciiArt.sleepMillis(500);
        broadcastMessage("무승부입니다!");
    }

    @Override
    public void endGame(){
        AsciiArt.sleepMillis(500);
        if (p1.getHealth()<=0){
            broadcastMessage(p2.getName() +"가 승리했습니다!\n");
        } else{
            broadcastMessage(p2.getName() +"가 승리했습니다!\n");
        }
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

    public void userTurn(NetworkUser user){
        // 1. 아이템 사용 // 2. 나에게 쏘기 // 3. 적에게 쏘기
        AsciiArt.sleepMillis(800);
        broadcastMessage(user.getName() + "의 차례입니다.\n");
        if(!user.getFree()){
            broadcastMessage(user.getName() + "가 수갑에 묶여있어 차례가 넘어갑니다.\n");
            user.setFree(true);
            user.getEnemy().setMyTurn(true);
            AsciiArt.sleepMillis(1000);
            return;
        }

        for(int i = 0; i<10; i++){
            if(!user.getMyTurn() || gun.isEmptyBullet()){break;}
            String stateMessage =  AsciiArt.printState(this);
            broadcastMessage(stateMessage);
            user.getHandler().sendMessage("1. 아이템 사용 2. 나에게 쏘기 3. 적에게 쏘기");
            broadcastMessage(user.getName()+"의 입력을 기다리고 있습니다...");
            try {
                String optionMessage = user.getHandler().readMessage();
                int option = Integer.parseInt(optionMessage);
                switch (option) {
                    case 1:
                        user.getHandler().sendMessage("아이템을 선택하세요.");
                        String indexMessage = user.getHandler().readMessage();
                        int index = Integer.parseInt(indexMessage);
                        String itemMessage = user.useNetItem(index);
                        broadcastMessage(itemMessage);
                        break;
                    case 2:
                        broadcastMessage(user.getName() + "가 자신에게 총을 쏩니다.\n");
                        AsciiArt.sleepMillis(500);
                        boolean cur = gun.isReal();
                        String gunMessage =  gun.shoot(user);
                        broadcastMessage(gunMessage);
                        if (cur) {
                            user.setMyTurn(false);
                            user.getEnemy().setMyTurn(true);
                        }
                        break;
                    case 3:
                        broadcastMessage(user.getName()+"가 " + user.getEnemy().getName() + "에게 총을 쏩니다.\n");
                        AsciiArt.sleepMillis(500);
                        String shootMessage =  gun.shoot(user.getEnemy());
                        broadcastMessage(shootMessage);
                        user.setMyTurn(false);
                        user.getEnemy().setMyTurn(true);
                        break;
                    default:
                        user.getHandler().sendMessage("잘못된 입력입니다.");
                        break;
                }
            }catch (NumberFormatException | IndexOutOfBoundsException e) {
                user.getHandler().sendMessage("잘못된 입력입니다.");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("턴을 너무 많이 사용하여, 턴이 종료됩니다.");
        user.setMyTurn(false);
        user.getEnemy().setMyTurn(true);
    }

    public void broadcastMessage(String message){
        p1thread.sendMessage(message);
        p2thread.sendMessage(message);
    }


}
