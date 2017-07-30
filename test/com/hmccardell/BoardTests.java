package com.hmccardell;

import com.hmccardell.entities.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by hmccardell on 7/27/2017.
 */
public class BoardTests extends BaseTest {

    Board board;
    GameState gameState;
    List<Player> testPlayers;
    Player player1;
    Player player2;
    Player playerWithNoCards;
    Player player4;
    WarCard testCard;

    @Before
    public void setup() {
        setupTestBoard();
        ;

    }

    public void setupTestBoard() {
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
        board.set_gameState(gameState);
    }

    //    Board board = new Board;
    //    Board boardSpy = Mockito.spy(board);
    //    Mockito.when(boardSpy.()).thenReturn(5l);
    @Test
    public void dealCardsDistrubitesEntireDeck() {
        List<WarCard> gameDeck = new ArrayList<>();
        gameDeck = createDeck();
        boolean goodDeal = board.dealCards(gameDeck);
        assertTrue(goodDeal);
    }

    @Test
    public void dealCardsReturnsFalseWhenDeckIsNull() {
        List<WarCard> gameDeck = null;
        boolean badDeal = board.dealCards(gameDeck);
        assertFalse(badDeal);
    }

    @Test
    public void dealCardsReturnsFalseWhenDeckIsEmpty() {
        List<WarCard> gameDeck = new ArrayList<>();
        boolean badDeal = board.dealCards(gameDeck);
        assertFalse(badDeal);
    }

    @Test
    public void dealCardsDealsRemainderCardsWhenUnevenDeck() {
        List<WarCard> gameDeck = new ArrayList<>();
        gameDeck = createDeck();
        List<Player> playerList = new ArrayList<>();
        playerList = gameState.getPlayers();
        playerList.add(playerWithNoCards);
        gameState.setPlayers(playerList);
        board.set_gameState(gameState);
        boolean goodDeal = board.dealCards(gameDeck);
        assertTrue(goodDeal);
    }

    @Test
    public void determineHighestCardReturnsNullWhenPassedNullPool() {
        TrickCard testCard = new TrickCard();
        testCard = board.determineHighestCard(null);
        assertNull(testCard);
    }

    @Test
    public void determineHighestCardReturnsHighestCardWhenFaceUp() {
        TrickCard card1 = new TrickCard(new WarCard(2, Suit.DIAMONDS), player1, true);
        TrickCard card2 = new TrickCard(new WarCard(10, Suit.DIAMONDS), player1, true);
        List<TrickCard> testList = new ArrayList<>();
        testList.add(card1);
        testList.add(card2);
        TrickCard highestCard = board.determineHighestCard(testList);
        assertEquals(10, highestCard.getValue());
    }

    @Test
    public void determineHighestCardReturnsNullWhenCardsAreFacedown() {
        TrickCard card1 = new TrickCard(new WarCard(2, Suit.DIAMONDS), player1, false);
        TrickCard card2 = new TrickCard(new WarCard(10, Suit.DIAMONDS), player1, false);
        List<TrickCard> testList = new ArrayList<>();
        testList.add(card1);
        testList.add(card2);
        TrickCard highestCard = board.determineHighestCard(testList);
        assertNull(highestCard);
    }

    @Test
    public void dealCardToPlayerIncreasesTheirDeckSizeByOneAndDecreasesTheGameDeckSizeByOne() {
        List<WarCard> playerDeck = new ArrayList<>();
        List<WarCard> gameDeck = new ArrayList<>();
        gameDeck.add(testCard);
        board.dealCardToPlayer(gameDeck, playerDeck);
        assertEquals(1, playerDeck.size());
        assertEquals(0, gameDeck.size());
    }

    @Test
    public void populateDeckShouldInsert52Cards() {
        List<WarCard> deck = new ArrayList<>();
        Board.populateDeck(deck);
        assertEquals(52, deck.size());
    }

    @Test
    public void gatherCardsFromPlayersShouldResultInListWithSizeEqualToNumberOfPlayers() {
        List<TrickCard> cardPool = new ArrayList<>();
        cardPool = board.gatherCardsFromPlayers(gameState.getPlayers(), true);
        assertEquals(cardPool.size(), gameState.getPlayers().size());
    }

    @Test
    public void gatherCardsFromPlayersShouldReturnNullWhenCalledWithNoPlayers() {
        gameState.setPlayers(null);
        List<TrickCard> cardPool = new ArrayList<>();
        cardPool = board.gatherCardsFromPlayers(gameState.getPlayers(), true);
        assertNull(cardPool);
    }

    @Test
    public void gatherCardsFromPlayersdReturnNullWhenAPlayerHasNoCardsInTheirDeck() {
        List<Player> testList = new ArrayList<>();
        testList.add(playerWithNoCards);
        testList.add(playerWithNoCards);
        gameState.setPlayers(testList);
        List<TrickCard> cardPool = new ArrayList<>();
        cardPool = board.gatherCardsFromPlayers(testList, true);
        assertNull(cardPool);
    }

    @Test
    public void gatherCardsFromPlayersShouldReturnNullWhenPlayerHasNullDeck() {
        List<Player> playerList = new ArrayList<>();
        playerList.add(playerWithNoCards);
        gameState.setPlayers(playerList);
        assertNull(board.gatherCardsFromPlayers(playerList, true));
    }

    @Test
    public void determineWarReturnsNullWhenAnEmptyPoolIsPassed() {
        TrickCard card = new TrickCard(testCard, player1);
        List<Player> resultingSet = board.determineWar(null, card);
        assertNull(resultingSet);
    }

    @Test
    public void determineWarReturnsOnePlayerWhenThereAreNoWars() {
        List<TrickCard> testPool = new ArrayList<>();
        TrickCard card1 = new TrickCard(new WarCard(11, Suit.SPADES), player1);
        TrickCard card2 = new TrickCard(new WarCard(10, Suit.SPADES), player2);
        testPool.add(card1);
        testPool.add(card2);
        int actualSize = board.determineWar(testPool, card1).size();
        int expectedSize = 1;
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void determineWarReturnsTwoPlayersWhenTwoValuesMatch() {
        List<TrickCard> testPool = new ArrayList<>();
        TrickCard card1 = new TrickCard(new WarCard(10, Suit.SPADES), player1, true);
        TrickCard card2 = new TrickCard(new WarCard(10, Suit.SPADES), player2, true);
        testPool.add(card1);
        testPool.add(card2);
        int actualSize = board.determineWar(testPool, card1).size();
        int expectedSize = 2;
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void determineWarReturnsThreePlayersWhenThreeValuesMatch() {
        List<TrickCard> testPool = new ArrayList<>();
        TrickCard card1 = new TrickCard(new WarCard(10, Suit.SPADES), player1, true);
        TrickCard card2 = new TrickCard(new WarCard(10, Suit.SPADES), player2, true);
        TrickCard card3 = new TrickCard(new WarCard(10, Suit.SPADES), playerWithNoCards, true);
        testPool.add(card1);
        testPool.add(card2);
        testPool.add(card3);
        int actualSize = board.determineWar(testPool, card1).size();
        int expectedSize = 3;
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void determineWarReturnsFourPlayersWhenFourValuesMatch() {
        List<TrickCard> testPool = new ArrayList<>();
        TrickCard card1 = new TrickCard(new WarCard(10, Suit.SPADES), player1, true);
        TrickCard card2 = new TrickCard(new WarCard(10, Suit.SPADES), player2, true);
        TrickCard card3 = new TrickCard(new WarCard(10, Suit.SPADES), playerWithNoCards, true);
        TrickCard card4 = new TrickCard(new WarCard(10, Suit.SPADES), playerWithNoCards, true);
        testPool.add(card1);
        testPool.add(card2);
        testPool.add(card3);
        testPool.add(card4);
        int actualSize = board.determineWar(testPool, card1).size();
        int expectedSize = 4;
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void totalCardsInTheSystemDoesNotChangeWhenWarOccurs() {
        setGameStateFor2PlayerWar(gameState, board);
        int actual = gameState.getPlayerByIndex(0).getDeck().size() + gameState.getPlayerByIndex(1).getDeck().size();
        int expectedTotalCardsInSystem = 8;
        assertEquals(expectedTotalCardsInSystem, actual);
    }

}
