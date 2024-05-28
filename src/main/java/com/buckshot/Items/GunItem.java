package com.buckshot.Items;

import com.buckshot.Core.Gun;

public abstract class GunItem extends Item{
    Gun gun;
    public GunItem(Gun gun) {
        this.gun = gun;
    }
    public abstract void useGun(Gun gun);
    public void describeGun(){
        System.out.println(this.netMessage);
    }
    @Override
    public String use(){
        decribeItem();
        useGun(this.gun);
        describeGun();
        return this.netMessage;
    }
}
