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
            List<Player> gamePlayers = new ArrayList<>();
            gamePlayers.add(player1);
            gamePlayers.add(player2);
            gameState.setPlayers(gamePlayers);
            Board.dealCards(deck, gameState);
        }
        while(!gameState.isGameOver()){
            List<TrickCard> pool = new ArrayList<>();
            pool = board.gatherCardsFromPlayers(gameState);
            List<Player> winners = new ArrayList<>();
            winners.addAll(board.determineWarOrWinnerOfTrick(pool));
            board.cleanUpTheTrick(gameState, winners, pool);
            System.out.println("Hayes cards: " + gameState.getPlayers().get(0).getDeck().size() + " Greg cards: " + gameState.getPlayers().get(1).getDeck().size());
            Thread.sleep(250);
        }

        System.out.println("End program");

    }
}
