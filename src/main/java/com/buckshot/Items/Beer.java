package com.buckshot.Items;

import com.buckshot.Core.Gun;

public class Beer extends GunItem {
    private int removed;
    public Beer(Gun gun) {
        super(gun);
        this.name = "맥주  ";
        this.description = "장전된 총알을 버립니다.";
    }

    @Override
    public void useGun(Gun gun){
        dumpBullet(gun);
    }

    @Override
    public void describeGun(){
        if (this.removed!=0){
            System.out.println("실탄이 제거되었습니다.\n");
        } else {
            System.out.println("공포탄이 제거되었습니다\n");
        }
    }

    public void dumpBullet(Gun gun){
        this.removed = gun.removeBullet();
    }
}
