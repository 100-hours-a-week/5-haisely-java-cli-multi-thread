package com.buckshot.Network;


import com.buckshot.Core.User;
import com.buckshot.Items.Item;
import com.buckshot.Manager.AsciiArt;

public class NetworkUser extends User {
    private ClientHandler handler;

    public String useNetItem(int index){
        String message;
        Item i = items.get(index -1);
        message = this.name+"가 "+ i.getName()+"을 사용했습니다.\n";
        message += i.use();
        items.remove(index-1);
        return message;
    }

    public ClientHandler getHandler() {
        return handler;
    }

    public void setHandler(ClientHandler handler) {
        this.handler = handler;
    }
}
