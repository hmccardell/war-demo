package com.hmccardell.entities;

/**
 * A class to model a card in a trick, a WarCard decorated with the player who threw it.
 *
 * @author hmccardell
 */
public class TrickCard implements Card {

    int value;
    Suit suit;
    Player player;

    public TrickCard() {
    }

    ;

    public TrickCard(WarCard warCard, Player player) {
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
}
