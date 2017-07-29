package com.hmccardell.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hmccardell on 7/26/2017.
 */
public class GameState {

    private List<Player> players;

    private boolean gameOver;

    public GameState(){
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

    public Player getPlayerByIndex(int index){
        return players.get(index);
    }

    public void setPlayerDeck(int indexOfPlayer, List<WarCard> deck){
        Player playerToUpdate = players.get(indexOfPlayer);
        playerToUpdate.setDeck(deck);
    }
}
