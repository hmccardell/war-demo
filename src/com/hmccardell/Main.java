package com.hmccardell;

import com.hmccardell.entities.Board;
import com.hmccardell.entities.GameState;
import com.hmccardell.entities.TrickCard;
import com.hmccardell.entities.WarCard;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        GameState gameState = new GameState();
        Board board = new Board();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<WarCard> deck = new ArrayList<>();
        Board.populateDeck(deck);
        Collections.shuffle(deck);
        Board.setupPlayers(gameState, br);
        Board.dealCards(deck, gameState);

        while(!gameState.isGameOver()){
            List<TrickCard> pool = new ArrayList<>();
            pool = board.theFlip(gameState);
        }

        System.out.println("End program");

    }
}
