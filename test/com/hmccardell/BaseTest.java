package com.hmccardell;

import com.hmccardell.entities.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hmccardell on 7/29/2017.
 */
public abstract class BaseTest {

    public List<Card> createDeck(){
        List<Card> deckToReturn = new ArrayList<>();
        for (int i = 2; i < 15; i++) {
            deckToReturn.add(new Card(i, Suit.CLUBS));
            deckToReturn.add(new Card(i, Suit.DIAMONDS));
            deckToReturn.add(new Card(i, Suit.HEARTS));
            deckToReturn.add(new Card(i, Suit.SPADES));
        }

        return deckToReturn;
    }

    public void setGameStateFor2PlayerWar(GameState gameState, Board board){

        Card card1 = new Card(2, Suit.SPADES);
        Card card2 = new Card(2, Suit.SPADES);
        Card card3 = new Card(10, Suit.SPADES);
        Card card4 = new Card(11, Suit.SPADES);
        Card card5 = new Card(8, Suit.SPADES);
        Card card6 = new Card(6, Suit.SPADES);
        Card card7 = new Card(5, Suit.SPADES);
        Card card8 = new Card(7, Suit.SPADES);
        Player player1 = new Player("Hayes");
        Player player2 = new Player("Greg");
        player1.addCardToPlayerDeck(card1);
        player2.addCardToPlayerDeck(card2);
        player1.addCardToPlayerDeck(card3);
        player2.addCardToPlayerDeck(card4);
        player1.addCardToPlayerDeck(card5);
        player2.addCardToPlayerDeck(card6);
        player1.addCardToPlayerDeck(card7);
        player2.addCardToPlayerDeck(card8);
        List<Player> playerList = new ArrayList<>();
        playerList.add(player1);
        playerList.add(player2);
        gameState.setPlayers(playerList);
        board.set_gameState(gameState);
    }

}
