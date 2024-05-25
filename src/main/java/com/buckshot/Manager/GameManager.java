package com.buckshot.Manager;

import com.buckshot.Core.Gun;
import com.buckshot.Core.User;
import com.buckshot.Items.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GameManager {
    private final User p1;
    private final User p2;
    private final Gun gun;
    private int round = 1;
    private static final Scanner scanner = new Scanner(System.in);

    final int ITEM_COUNT = 4;
    final int ITEM_NUMBER = 5;

    public GameManager(User p1, User p2, Gun gun) {
        this.p1 = p1;
        this.p2 = p2;
        this.gun = gun;
        initializeGame();
    }

    private void initializeGame(){
        this.p1.setGun(gun);
        this.p2.setGun(gun);
        this.p1.setScanner(this.scanner);
        this.p2.setScanner(this.scanner);
        this.p1.setEnemy(p2);
        this.p2.setEnemy(p1);
        this.p1.setGm(this);
        this.p2.setGm(this);
    }


    public void randomBullets(Gun gun){
        ArrayList<Integer> newBullets = new ArrayList<Integer>();
        int num = 3+(int)(Math.random()*6);
        int limit = (int)(num/3);
        int real = limit + (int)(Math.random()*(num-2*limit+1));
        AsciiArt.printCenteredStringPretty("총알이 "+num+" 개 장전됩니다.", 3);
        AsciiArt.sleepMillis(1000);

        for (int i = 0; i<num; i++){
            if(i<real){
                newBullets.add(1);
            }else{
                newBullets.add(0);
            }
            AsciiArt.printCenteredString("찰칵\n", 0);
            AsciiArt.sleepMillis(230);
        }
        AsciiArt.sleepMillis(270);
        AsciiArt.printCenteredStringPretty("실탄 "+real+" 개, 공포탄 "+ (num-real)+"개가 장전되었습니다.", 4);
        AsciiArt.sleepMillis(2000);
        Collections.shuffle(newBullets);
        gun.setBullets(newBullets);
        return;
    }

    public void randomItems(User user){
        ArrayList<Item> newItems = new ArrayList<Item>();
        for (int i = 0; i<ITEM_COUNT ; i++){
            int randNum = (int)(Math.random()*ITEM_NUMBER);
            newItems.add(getItemById(randNum, user));
        }
        user.setItems(newItems);
        return;
    }

    public Item getItemById(int randNum, User user) {
        return switch (randNum) {
            case 0 -> new Beer(this.gun);
            case 1 -> new Cigarette(user);
            case 2 -> new Handcuff(user.getEnemy());
            case 3 -> new Knife(this.gun);
            case 4 -> new Magnifier(this.gun);
            default -> throw new IllegalArgumentException("Invalid item ID: " + randNum);
        };
    }


    public void startGame(){
        for (int i = 0; i < 10; i++) {
            // 메시지 출력
            try {
                AsciiArt.printCenteredStringPretty("Player 1의 이름을 작성하세요.", 3);
                AsciiArt.printCenteredString("   >  ", 8);
                String playerName1 = scanner.nextLine();
                p1.setName(playerName1);
                AsciiArt.printCenteredString("Player 1: " + playerName1 + '\n', 0);
                AsciiArt.sleepMillis(500);
                AsciiArt.printCenteredStringPretty("Player 2의 이름을 작성하세요.", 3);
                AsciiArt.printCenteredString("   >  ", 8);
                String playerName2 = scanner.nextLine();
                p2.setName(playerName2);
                AsciiArt.printCenteredString("Player 2: " + playerName2 + '\n', 0);
                AsciiArt.sleepMillis(500);
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

    public void startRound(){
        AsciiArt.printCenteredStringPretty(this.round + " Round가 시작됩니다.", 1);
        AsciiArt.sleepMillis(1000);
        randomBullets(this.gun);
        this.round +=1;
    }

    public boolean canRound(){
        return ((p1.getHealth()>0) && (p2.getHealth()>0));
    }

    public boolean canTurn(){
        return (!gun.isEmptyBullet()&&canRound());
    }

    public void endGame(){
        AsciiArt.sleepMillis(500);
        if (p1.getHealth()<=0){
            AsciiArt.printCenteredStringPretty(p2.getName() +"가 승리했습니다!", 3);
        } else{
            AsciiArt.printCenteredStringPretty(p1.getName() +"가 승리했습니다!", 3);
        }
        scannerClose();
    }

    public void drawGame(){
        AsciiArt.sleepMillis(500);
        AsciiArt.printCenteredStringPretty("무승부입니다!", 3);
        scannerClose();
    }

    public void scannerClose(){
        scanner.close();
    }

    public User getP1() {
        return p1;
    }

    public User getP2() {
        return p2;
    }

    public Gun getGun() {
        return gun;
    }

    public int getRound() {
        return round;
    }
}
