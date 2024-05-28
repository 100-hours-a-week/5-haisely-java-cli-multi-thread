package com.buckshot.Network;

import com.buckshot.Core.Gun;
import com.buckshot.Core.User;
import com.buckshot.Manager.AsciiArt;

public class NetworkGun extends Gun {

    @Override
    public String shoot(User target){
        int b = this.getBullets().removeFirst();
        if (b!=0) {

            target.changeHealth(-b);
            return(AsciiArt.printBang());
        }
        return("틱...\n");
    }
}
