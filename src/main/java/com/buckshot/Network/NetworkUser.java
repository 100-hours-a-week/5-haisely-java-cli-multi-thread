package com.buckshot.Network;


import com.buckshot.Core.User;

public class NetworkUser extends User {
    private Thread handler;

    @Override
    public void myTurn(){

    }

    @Override
    public void useItem(int index){

    }

    public Thread getHandler() {
        return handler;
    }

    public void setHandler(Thread handler) {
        this.handler = handler;
    }
}
