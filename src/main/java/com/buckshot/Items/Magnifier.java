package com.buckshot.Items;

import com.buckshot.Core.Gun;

public class Magnifier extends GunItem {
    private int index;
    private boolean isReal;
    public Magnifier(Gun gun) {
        super(gun);
        this.name = "돋보기";
        this.description = "장착된 총알의 실탄 여부를 확인합니다.";
        this.index = 0;
    }

    @Override
    public void useGun(Gun gun){
        checkReal(gun);
    }

    public void checkReal(Gun gun){
        this.isReal = gun.isReal();
        if (this.isReal){
            this.netMessage =  "실탄이 장착되어 있습니다.\n";
        } else {
            this.netMessage =  "공포탄이 장착되어 있습니다.\n";
        }
    }

    public void checkRealByIndex(Gun gun){
        this.isReal = gun.isReal(this.index);
    }

    public void setIndex(int i){
        this.index = i;
    }
}
