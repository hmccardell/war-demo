package com.hmccardell.entities;

/**
 * Created by hmccardell on 7/27/2017.
 */
public class WarCard implements Card {

    private int value;
    private Suit suit;

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

    public WarCard(int value, Suit suit){
        this.value = value;
        this.suit = suit;
    }
}
