package com.buckshot.Network;

import com.buckshot.Core.Gun;
import com.buckshot.Manager.GameManager;

public class NetworkGameManager extends GameManager {
    private final ClientHandler p1Thread;
    private final ClientHandler p2Thread;
    public NetworkGameManager(NetworkUser p1, NetworkUser p2, Gun gun, ClientHandler p1Thread, ClientHandler p2Thread) {
        super(p1, p2, gun);
        this.p1Thread = p1Thread;
        this.p2Thread = p2Thread;
    }

}
