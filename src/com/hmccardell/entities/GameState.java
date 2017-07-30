package com.hmccardell.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to track the state of the game.
 *
 * @author hmccardell
 */
public class GameState {

    private List<Player> players;

    private boolean gameOver;

    public GameState() {
        gameOver = false;
        players = new ArrayList<>();
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Player getPlayerByIndex(int index) {
        return players.get(index);
    }

    public void setPlayerDeck(int indexOfPlayer, List<Card> deck) {
        Player playerToUpdate = players.get(indexOfPlayer);
        playerToUpdate.setDeck(deck);
    }

    public void displayTotalCardsInState() {
        int total = 0;
        StringBuilder individualTotals = new StringBuilder();
        for (Player player : getPlayers()) {
            total += player.getDeck().size();
            individualTotals.append(player.getName() + " : " + player.getDeck().size() + " ");
        }
        StringBuilder systemTotals = new StringBuilder();
        systemTotals.append("Total cards in system: " + total + " [ ");
        systemTotals.append(individualTotals.toString());
        systemTotals.append("]");
        System.out.println(systemTotals.toString());

    }

    @Override
    public String toString() {
        return "GameState{" +
                "players=" + players +
                ", gameOver=" + gameOver +
                '}';
    }
}
