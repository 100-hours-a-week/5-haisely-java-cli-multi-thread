package com.buckshot.Items;

import com.buckshot.Core.User;

public class Cigarette extends UserItem {
    private int heal;
    public Cigarette(User target) {
        super(target);
        this.name = "담배  ";
        this.description = "자신의 체력을 증가시킵니다.";
        this.heal = 1;
    }

    @Override
    public void useUser(User user){
        incHealth(user);
    }

    public void incHealth(User user){
        if (user.getHealth()<6) {
            user.changeHealth(heal);
            this.netMessage = user.getName() + "의 체력이 "+this.heal+" 회복되었습니다!\n";
        } else {
            this.netMessage = user.getName() + "의 체력이 최대여서 회복되지 않았습니다!\n";
        }
    }

    public void setHeal(int heal) {
        this.heal = heal;
    }
}
