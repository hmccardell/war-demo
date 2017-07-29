package com.hmccardell;

import com.hmccardell.entities.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by hmccardell on 7/27/2017.
 */
public class BoardTests {

    Board board;
    GameState gameState;
    List<Player> testPlayers;
    Player player1;
    Player player2;
    Player playerWithNoCards;
    Player player4;
    WarCard testCard;

    @Before
    public void setup(){
        testPlayers = new ArrayList<>();
        board = new Board();
        gameState = new GameState();
        player1 = new Player("Hayes");
        player2 = new Player("Greg");
        playerWithNoCards = new Player("Dan");
        player1.addCardToPlayerDeck(new WarCard(2, Suit.SPADES));
        player2.addCardToPlayerDeck(new WarCard(3, Suit.HEARTS));
        testPlayers.add(player1);
        testPlayers.add(player2);
        gameState.setPlayers(testPlayers);
        testCard = new WarCard(2, Suit.SPADES);

    }

    @Test
    public void dealCardToPlayerIncreasesTheirDeckSizeByOneAndDecreasesTheGameDeckSizeByOne(){
        List<WarCard> playerDeck = new ArrayList<>();
        List<WarCard> gameDeck = new ArrayList<>();
        gameDeck.add(testCard);
        Board.dealCardToPlayer(gameDeck, playerDeck);
        assertEquals(1, playerDeck.size());
        assertEquals(0, gameDeck.size());
    }

    @Test
    public void populateDeckShouldInsert52Cards(){
        List<WarCard> deck = new ArrayList<>();
        Board.populateDeck(deck);
        assertEquals(52, deck.size());
    }

    @Test
    public void theFlipShouldResultInListWithSizeEqualToNumberOfPlayers(){
        List<TrickCard> cardPool = new ArrayList<>();
        cardPool = board.theFlip(gameState);
        assertEquals(cardPool.size(), gameState.getPlayers().size());
    }

    @Test
    public void theFlipShouldReturnNullWhenCalledWithNoPlayers(){
        gameState.setPlayers(null);
        List<TrickCard> cardPool = new ArrayList<>();
        cardPool = board.theFlip(gameState);
        assertNull(cardPool);
    }

    @Test
    public void theFlipShouldReturnNullWhenAPlayerHasNoCardsInTheirDeck(){
        List<Player> testList = new ArrayList<>();
        testList.add(playerWithNoCards);
        gameState.setPlayers(testList);
        List<TrickCard> cardPool = new ArrayList<>();
        cardPool = board.theFlip(gameState);
        assertNull(cardPool);
    }

//    @Test
//    public void determineWarReturnsNullWhenCardValuesMatch(){
//        List<TrickCard> testPool = new ArrayList<>();
//        TrickCard card1 = new TrickCard(new WarCard(10, Suit.SPADES), player1);
//        TrickCard card2 = new TrickCard(new WarCard(9, Suit.SPADES), player2);
//        testPool.add(card1);
//        testPool.add(card2);
//        List<Player> resultingSet = board.determineWarOrWinnerOfTrick(testPool);
//        assertNull(resultingSet);
//    }

    @Test
    public void determineWarReturnsNullWhenAnEmptyPoolIsPassed(){
        List<TrickCard> testPool = new ArrayList<>();
        List<Player> resultingSet = board.determineWarOrWinnerOfTrick(testPool);
        assertNull(resultingSet);
    }

    @Test
    public void determineWarReturnsTwoPlayersWhenTwoValuesMatch(){
        List<TrickCard> testPool = new ArrayList<>();
        TrickCard card1 = new TrickCard(new WarCard(10, Suit.SPADES), player1);
        TrickCard card2 = new TrickCard(new WarCard(10, Suit.SPADES), player2);
        testPool.add(card1);
        testPool.add(card2);
        int actualSize = board.determineWarOrWinnerOfTrick(testPool).size();
        int expectedSize = 2;
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void determineWarReturnsTwoPlayersWhenThreeValuesMatch(){
        List<TrickCard> testPool = new ArrayList<>();
        TrickCard card1 = new TrickCard(new WarCard(10, Suit.SPADES), player1);
        TrickCard card2 = new TrickCard(new WarCard(10, Suit.SPADES), player2);
        TrickCard card3 = new TrickCard(new WarCard(10, Suit.SPADES), playerWithNoCards);
        testPool.add(card1);
        testPool.add(card2);
        testPool.add(card3);
        int actualSize = board.determineWarOrWinnerOfTrick(testPool).size();
        int expectedSize = 3;
        assertEquals(expectedSize, actualSize);
    }
}
