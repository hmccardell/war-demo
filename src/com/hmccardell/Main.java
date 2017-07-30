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
        GameState gameState = new GameState();
        board.set_gameState(gameState);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<Card> deck = new ArrayList<>();
        Board.populateDeck(deck);
        Collections.shuffle(deck);
        board.setupPlayers(br);
        board.promptSettings(br);
        board.dealCards(deck);

        while(!board.get_gameState().isGameOver()){
            Thread.sleep(board.get_millisecondDelay());
            List<TrickCard> pot = new ArrayList<>();
            pot = board.gatherCardsFromPlayers(board.get_gameState().getPlayers(), true);
            board.handleTrick( pot);
            board.get_gameState().displayTotalCardsInState();
            board.removePlayersWithEmptyDecksFromTheGame();
            board.checkIfGameOver();
        }

        System.out.println(gameState.getPlayerByIndex(0).getName() + " has won the game.");

    }
}
