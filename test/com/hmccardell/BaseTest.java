package com.hmccardell;

import com.hmccardell.entities.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hmccardell on 7/29/2017.
 */
public abstract class BaseTest {

    public List<WarCard> createDeck(){
        List<WarCard> deckToReturn = new ArrayList<>();
        for (int i = 2; i < 15; i++) {
            deckToReturn.add(new WarCard(i, Suit.CLUBS));
            deckToReturn.add(new WarCard(i, Suit.DIAMONDS));
            deckToReturn.add(new WarCard(i, Suit.HEARTS));
            deckToReturn.add(new WarCard(i, Suit.SPADES));
        }

        return deckToReturn;
    }

    public void setGameStateFor2PlayerWar(GameState gameState, Board board){

        WarCard card1 = new WarCard(2, Suit.SPADES);
        WarCard card2 = new WarCard(2, Suit.SPADES);
        WarCard card3 = new WarCard(10, Suit.SPADES);
        WarCard card4 = new WarCard(11, Suit.SPADES);
        WarCard card5 = new WarCard(8, Suit.SPADES);
        WarCard card6 = new WarCard(6, Suit.SPADES);
        WarCard card7 = new WarCard(5, Suit.SPADES);
        WarCard card8 = new WarCard(7, Suit.SPADES);
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
