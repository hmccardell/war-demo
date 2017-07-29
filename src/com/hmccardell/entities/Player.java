package com.hmccardell.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hmccardell on 7/26/2017.
 */
public class Player {

    private String name;
    private int gamesWon;
    private List<WarCard> deck;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    public List<WarCard> getDeck() {
        return deck;
    }

    public void setDeck(List<WarCard> deck) {
        this.deck = deck;
    }

    public void addCardToPlayerDeck(WarCard cardToAdd){
        deck.add(cardToAdd);
    }

    public Player(String name) {

        this.name = name;
        gamesWon = 0;
        deck = new ArrayList<>();
    }

    public Player() {
    }
}
