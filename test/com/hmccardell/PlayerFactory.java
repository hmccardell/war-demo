package com.hmccardell;

import com.hmccardell.entities.Player;

/**
 * Created by hmccardell on 7/29/2017.
 */
public class PlayerFactory {

    public static Player buildPlayerWithNoCards(){
        Player player = new Player();
        player.setName("test player with no cards");
        return player;
    }

}
