package com.buckshot.Network;


import com.buckshot.Core.User;

public class NetworkUser extends User {
    private ClientHandler handler;

    @Override
    public void useItem(int index){

    }

    public ClientHandler getHandler() {
        return handler;
    }

    public void setHandler(ClientHandler handler) {
        this.handler = handler;
    }
}
