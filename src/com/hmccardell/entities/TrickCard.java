package com.hmccardell.entities;

/**
 * A class to model a card in a trick, a Card decorated with the player who threw it.
 *
 * @author hmccardell
 */
public class TrickCard {

    int value;
    Suit suit;
    Player player;
    boolean faceUp;

    public boolean isFaceUp() {
        return faceUp;
    }

    public void setFaceUp(boolean faceUp) {
        this.faceUp = faceUp;
    }

    public TrickCard() {
    }

    public TrickCard(Card card, Player player) {
        this.value = card.getValue();
        this.suit = card.getSuit();
        this.player = player;
    }

    public TrickCard(Card card, Player player, boolean faceUp) {
        this.value = card.getValue();
        this.suit = card.getSuit();
        this.player = player;
        this.faceUp = faceUp;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrickCard trickCard = (TrickCard) o;

        if (value != trickCard.value) return false;
        if (suit != trickCard.suit) return false;
        return player.equals(trickCard.player);
    }

    @Override
    public int hashCode() {
        int result = value;
        result = 31 * result + suit.hashCode();
        result = 31 * result + player.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "TrickCard{" +
                "value=" + value +
                ", suit=" + suit +
                ", player=" + player +
                '}';
    }

    public String getName(int value) {

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
                cardName = "Jack of " + suit;
                break;
            case 12:
                cardName = "Queen of " + suit;
                break;
            case 13:
                cardName = "King of " + suit;
                break;
            case 14:
                cardName = "Ace of " + suit;
        }
        return cardName;
    }
}
