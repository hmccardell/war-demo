package com.hmccardell;

import com.hmccardell.entities.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException {


        Board board = new Board();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<Card> deck = new ArrayList<>();
        Board.populateDeck(deck);
        Collections.shuffle(deck);
//        board.setupPlayers(br);
//        board.promptSettings(br);
//        board.dealCards(deck);

//        //test block
        {
            GameState gameState = new GameState();
            board.set_gameState(gameState);
            Player player1 = new Player("Hayes");
            Player player2 = new Player("Greg");
            //Player player3 = new Player("Dan");
            List<Player> gamePlayers = new ArrayList<>();
            gamePlayers.add(player1);
            gamePlayers.add(player2);
            //gamePlayers.add(player3);
            board.get_gameState().setPlayers(gamePlayers);
            board.dealCards(deck);
        }
        while(!board.get_gameState().isGameOver()){
            List<TrickCard> pot = new ArrayList<>();
            pot = board.gatherCardsFromPlayers(board.get_gameState().getPlayers(), true);
            Thread.sleep(500);
            board.handleTrick( pot);
            board.get_gameState().displayTotalCardsInState();
        }

        System.out.println("End program");

    }
}
