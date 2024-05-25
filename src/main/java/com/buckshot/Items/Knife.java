package com.buckshot.Items;

import com.buckshot.Core.Gun;

public class Knife extends GunItem {
    private int damage;
    public Knife(Gun gun) {
        super(gun);
        this.name = "식칼  ";
        this.description = "총의 데미지를 변경합니다.";
        this.damage = 2;
    }

    @Override
    public void useGun(Gun gun){
        setGunDamage(gun);
    }

    @Override
    public void describeGun(){
        System.out.println("장착된 총알의 데미지가 "+this.damage+ "가 되었습니다!\n");
    }

    private void setGunDamage(Gun gun){
        gun.setBulletDamage(this.damage);
    }

    public void setDamage(int d){
        this.damage = d;
    }
}
