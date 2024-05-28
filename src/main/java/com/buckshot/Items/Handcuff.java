package com.buckshot.Items;

import com.buckshot.Core.User;

public class Handcuff extends UserItem {
    public Handcuff(User target) {
        super(target);
        if (target == null) {
            throw new IllegalArgumentException("Target cannot be null");
        }
        this.name = "수갑  ";
        this.description = "상대방이 다음턴을 못하도록 막습니다.";
    }

    @Override
    public void useUser(User user){
        lockUser(user);
    }

    public void lockUser(User user){
        user.setFree(false);
        this.netMessage = user.getName()+"가 수갑에 묶였습니다!\n";
    }

}
