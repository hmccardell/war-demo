package com.hmccardell;

import com.hmccardell.entities.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hmccardell on 7/29/2017.
 */
public abstract class BaseTest {

    public List<WarCard> createDeck(){
        List<WarCard> deckToReturn = new ArrayList<>();
        for (int i = 2; i < 15; i++) {
            deckToReturn.add(new WarCard(i, Suit.CLUBS));
            deckToReturn.add(new WarCard(i, Suit.DIAMONDS));
            deckToReturn.add(new WarCard(i, Suit.HEARTS));
            deckToReturn.add(new WarCard(i, Suit.SPADES));
        }

        return deckToReturn;
    }

}
