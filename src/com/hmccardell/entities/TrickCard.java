package com.hmccardell.entities;

/**
 * Created by hmccardell on 7/28/2017.
 */
public class TrickCard implements Card {

    int value;
    Suit suit;
    Player player;

    public TrickCard(WarCard warCard, Player player){
        this.value = warCard.getValue();
        this.suit = warCard.getSuit();
        this.player = player;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
