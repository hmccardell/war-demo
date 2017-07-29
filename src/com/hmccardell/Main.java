package com.hmccardell;

import com.hmccardell.entities.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        GameState gameState = new GameState();
        Board board = new Board();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<WarCard> deck = new ArrayList<>();
        Board.populateDeck(deck);
        Collections.shuffle(deck);
        //Board.setupPlayers(gameState, br);

        //test block
        {
            Player player1 = new Player("Hayes");
            Player player2 = new Player("Greg");
            Player player3 = new Player("Dan");
            List<Player> gamePlayers = new ArrayList<>();
            gamePlayers.add(player1);
            gamePlayers.add(player2);
            gamePlayers.add(player3);
            gameState.setPlayers(gamePlayers);
            Board.dealCards(deck, gameState);
        }
        while(!gameState.isGameOver()){
            List<TrickCard> pot = new ArrayList<>();
            pot = board.gatherCardsFromPlayers(gameState.getPlayers(), gameState, true);
            Thread.sleep(500);
            board.handleTrick(gameState, pot);
            gameState.displayTotalCardsInState();
        }

        System.out.println("End program");

    }
}
