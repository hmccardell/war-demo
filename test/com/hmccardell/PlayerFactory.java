package com.hmccardell;

import com.hmccardell.entities.Card;
import com.hmccardell.entities.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * A factory used to generate specific player types for ease of unit testing.
 *
 * @author hmccardell
 */
public class PlayerFactory {

    public final static String NAME_NO_CARDS = "test player with no cards";
    public final static String NAME_EMPTY_DECK = "test player with empty deck";

    public static Player buildPlayerWithNoCards(String name){
        Player player = new Player(name);
        player.setName(NAME_NO_CARDS);
        return player;
    }

    public static Player buildPlayerWithEmptyDeck(){
        Player player = buildPlayerWithNoCards(NAME_EMPTY_DECK);
        List<Card> deck = new ArrayList<>();
        player.setDeck(deck);
        return player;
    }

}
