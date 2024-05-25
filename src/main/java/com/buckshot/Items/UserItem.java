package com.buckshot.Items;

import com.buckshot.Core.User;

public abstract class UserItem extends Item {
    User target;
    public UserItem(User target) {
        this.target = target;
    }
    public abstract void useUser(User user);
    public abstract void describeUser();
    @Override
    public void use(){
        decribeItem();
        useUser(this.target);
        describeUser();
    }
}
