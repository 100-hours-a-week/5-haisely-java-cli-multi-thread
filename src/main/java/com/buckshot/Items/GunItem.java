package com.buckshot.Items;

import com.buckshot.Core.Gun;

public abstract class GunItem extends Item{
    Gun gun;
    public GunItem(Gun gun) {
        this.gun = gun;
    }
    public abstract void useGun(Gun gun);
    public abstract void describeGun();
    @Override
    public void use(){
        decribeItem();
        useGun(this.gun);
        describeGun();
    }
}
