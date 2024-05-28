package com.buckshot.Core;

import com.buckshot.Items.Item;
import com.buckshot.Manager.AsciiArt;
import com.buckshot.Manager.GameManager;

import java.util.ArrayList;
import java.util.Scanner;

public class User {
    private int health =6;
    protected String name;
    protected ArrayList<Item> items = new ArrayList<Item>();
    private Gun gun;
    private User enemy;
    private Boolean isFree = true;
    private Boolean myTurn = true;
    private Scanner scanner;
    protected GameManager gm;

    public void useItem(int index){
        Item i = items.get(index -1);
        System.out.println(this.name+"가 "+ i.getName()+"을 사용했습니다.");
        AsciiArt.sleepMillis(1000);
        i.use();
        AsciiArt.sleepMillis(1000);
        items.remove(index-1);
        return;
    }

    public void myTurn(){
        // 1. 아이템 사용 // 2. 나에게 쏘기 // 3. 적에게 쏘기
        AsciiArt.printCenteredString(this.name + "의 차례입니다.\n", 0);
        AsciiArt.sleepMillis(800);

        if(!isFree){
            System.out.println("\n"+this.name + "가 수갑에 묶여있어 차례가 넘어갑니다.\n");
            setFree(true);
            this.enemy.setMyTurn(true);
            AsciiArt.sleepMillis(1000);
            return;
        }
        for(int i = 0; i<10; i++){
            if(!myTurn || gun.isEmptyBullet()){break;}
            AsciiArt.printState(this.gm);
            AsciiArt.printCenteredStringPretty("1. 아이템 사용 2. 나에게 쏘기 3. 적에게 쏘기", 6);
            AsciiArt.printCenteredString("   >  ", 8);
            try {
                int option = Integer.parseInt(scanner.nextLine());
                switch (option) {
                    case 1:
                        AsciiArt.printCenteredStringPretty("아이템을 선택하세요.", 3);
                        AsciiArt.printCenteredString("   >  ", 8);
                        int itemIndex = Integer.parseInt(scanner.nextLine());
                        useItem(itemIndex);
                        break;
                    case 2:
                        System.out.println("\n" + this.name + "가 자신에게 총을 쏩니다.\n");
                        AsciiArt.sleepMillis(500);
                        boolean cur = gun.isReal();
                        gun.shoot(this);
                        if (cur) {
                            setMyTurn(false);
                            this.enemy.setMyTurn(true);
                        }
                        break;
                    case 3:
                        System.out.println("\n" + this.name + "가 " + enemy.name + "에게 총을 쏩니다.\n");
                        AsciiArt.sleepMillis(500);
                        gun.shoot(enemy);
                        setMyTurn(false);
                        this.enemy.setMyTurn(true);
                        break;
                    default:
                        System.out.println("잘못된 입력입니다.");
                        break;
                }
            }catch (NumberFormatException | IndexOutOfBoundsException e) {
                System.out.println("잘못된 입력입니다.");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("턴을 너무 많이 사용하여, 턴이 종료됩니다.");
        setMyTurn(false);
        this.enemy.setMyTurn(true);
    }


    public void changeHealth(int damage){
        this.health += damage;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public Gun getGun() {
        return gun;
    }

    public void setGun(Gun gun) {
        this.gun = gun;
    }

    public User getEnemy() {
        return enemy;
    }

    public void setEnemy(User enemy) {
        this.enemy = enemy;
    }

    public Boolean getFree() {
        return isFree;
    }

    public void setFree(Boolean free) {
        isFree = free;
    }

    public Boolean getMyTurn() {
        return myTurn;
    }

    public void setMyTurn(Boolean myTurn) {
        this.myTurn = myTurn;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public void setGm(GameManager gm) {
        this.gm = gm;
    }
}
