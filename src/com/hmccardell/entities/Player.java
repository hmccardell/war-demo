package com.hmccardell.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to track the state of a player
 *
 * @author hmccardell
 */
public class Player {

    private String name;
    private List<Card> deck;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Card> getDeck() {
        return deck;
    }

    public void setDeck(List<Card> deck) {
        this.deck = deck;
    }

    public void addCardToPlayerDeck(Card cardToAdd) {
        deck.add(cardToAdd);
    }

    public Player(String name) {

        this.name = name;
        deck = new ArrayList<>();
    }

    public Player() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        if (!name.equals(player.name)) return false;
        return deck.equals(player.deck);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + deck.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", deck=" + deck +
                '}';
    }
}
