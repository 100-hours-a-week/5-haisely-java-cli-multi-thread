package com.buckshot.Items;

public abstract class Item {
    String name;
    String description;
    String netMessage;
    public abstract String use();


    public String getName() {
        return name;
    }
    public void decribeItem() {
        System.out.println(this.name + " 설명 : "+this.description);
    }
    public String getNetMessage(){return this.netMessage;}
}
