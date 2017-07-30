package com.hmccardell.entities;

/**
 * A class that models a standard playing card.
 *
 * @author hmccardell
 */
public class Card {

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

    public Card(int value, Suit suit){
        this.value = value;
        this.suit = suit;
    }

    @Override
    public String toString() {
        return "Card{" +
                "value=" + value +
                ", suit=" + suit +
                '}';
    }

    public String getName() {

        String cardName = "";

        switch (value) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                cardName = String.valueOf(value) + " of " + suit;
                break;
            case 11:
                cardName =  "Jack of " + suit;
                break;
            case 12:
                cardName = "Queen of " + suit;
                break;
            case 13:
                cardName = "King of " + suit;
                break;
            case 14:
                cardName = "Ace of " + suit;
                break;
        }
        return cardName;
    }
}
