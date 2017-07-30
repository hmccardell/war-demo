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
//        Mockito.when(boardSpy.()).thenReturn(5l);
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
        cardPool = board.gatherCardsFromPlayers(gameState.getPlayers(), gameState, true);
        assertEquals(cardPool.size(), gameState.getPlayers().size());
    }

    @Test
    public void gatherCardsFromPlayersShouldReturnNullWhenCalledWithNoPlayers() {
        gameState.setPlayers(null);
        List<TrickCard> cardPool = new ArrayList<>();
        cardPool = board.gatherCardsFromPlayers(gameState.getPlayers(), gameState, true);
        assertNull(cardPool);
    }

    @Test
    public void gatherCardsFromPlayersdReturnNullWhenAPlayerHasNoCardsInTheirDeck() {
        List<Player> testList = new ArrayList<>();
        testList.add(playerWithNoCards);
        testList.add(playerWithNoCards);
        gameState.setPlayers(testList);
        List<TrickCard> cardPool = new ArrayList<>();
        cardPool = board.gatherCardsFromPlayers(testList, gameState, true);
        assertNull(cardPool);
    }

    @Test
    public void gatherCardsFromPlayersShouldReturnNullWhenPlayerHasNullDeck() {
        List<Player> playerList = new ArrayList<>();
        playerList.add(playerWithNoCards);
        gameState.setPlayers(playerList);
        assertNull(board.gatherCardsFromPlayers(playerList, gameState, true));
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

//    @Test
//    public void totalCardsInTheSystemDoesNotChangeWhenWarOccurs() {
//        List<TrickCard> testPool = new ArrayList<>();
//        WarCard card1 = new WarCard(2, Suit.SPADES);
//        WarCard card2 = new WarCard(2, Suit.SPADES);
//        WarCard card3 = new WarCard(10, Suit.SPADES);
//        WarCard card4 = new WarCard(11, Suit.SPADES);
//        player1 = new Player("Hayes");
//        player2 = new Player("Greg");
//        player1.addCardToPlayerDeck(card1);
//        player2.addCardToPlayerDeck(card2);
//        player1.addCardToPlayerDeck(card3);
//        player2.addCardToPlayerDeck(card4);
//        List<Player> playerList = new ArrayList<>();
//        playerList.add(player1);
//        playerList.add(player2);
//        gameState.setPlayers(playerList);
//        testPool = board.gatherCardsFromPlayers(gameState, true);
//        System.out.println(gameState.getPlayerByIndex(0).getDeck().size() + gameState.getPlayerByIndex(1).getDeck().size());
//        List<Player> winners = new ArrayList<>();
//        TrickCard testCard = new TrickCard(card1, player1);
//        winners = board.determineWar(testPool, testCard);
//        System.out.println(gameState.getPlayerByIndex(0).getDeck().size() + gameState.getPlayerByIndex(1).getDeck().size());
//        int actual = gameState.getPlayerByIndex(0).getDeck().size() + gameState.getPlayerByIndex(1).getDeck().size();
//        int expectedTotalCardsInSystem = 4;
//        assertEquals(expectedTotalCardsInSystem, actual);
//    }
//
//    @Test
//    public void warTest() {
//        player1.addCardToPlayerDeck(new WarCard(10, Suit.CLUBS));
//        player1.addCardToPlayerDeck(new WarCard(11, Suit.CLUBS));
//        player2.addCardToPlayerDeck(new WarCard(12, Suit.DIAMONDS));
//        player2.addCardToPlayerDeck(new WarCard(15, Suit.DIAMONDS));
//        gameState.getPlayers().forEach(player -> System.out.println(player.toString()));
//        List<TrickCard> pool = new ArrayList<>();
//        System.out.println(gameState.getPlayerByIndex(0).getDeck().size() +
//                gameState.getPlayerByIndex(1).getDeck().size());
//
//        {
//            pool = board.gatherCardsFromPlayers(gameState);
//            System.out.println(gameState.getPlayerByIndex(0).getDeck().size() +
//                    gameState.getPlayerByIndex(1).getDeck().size());
//            List<Player> winners = new ArrayList<>();
//            winners.addAll(board.determineWarOrWinnerOfTrick(pool));
//
//            System.out.println(gameState.getPlayerByIndex(0).getDeck().size() +
//                    gameState.getPlayerByIndex(1).getDeck().size());
//
//            board.cleanUpTheTrick(gameState, winners, pool);
//
//            System.out.println(gameState.getPlayerByIndex(0).getDeck().size() +
//                    gameState.getPlayerByIndex(1).getDeck().size());
//
//            gameState.getPlayers().forEach(player -> System.out.println(player.toString()));
//        }
//
//        {
//            pool = board.gatherCardsFromPlayers(gameState);
//            System.out.println(gameState.getPlayerByIndex(0).getDeck().size() +
//                    gameState.getPlayerByIndex(1).getDeck().size());
//            List<Player> winners = new ArrayList<>();
//            winners.addAll(board.determineWarOrWinnerOfTrick(pool));
//
//            System.out.println(gameState.getPlayerByIndex(0).getDeck().size() +
//                    gameState.getPlayerByIndex(1).getDeck().size());
//
//            board.cleanUpTheTrick(gameState, winners, pool);
//
//            System.out.println(gameState.getPlayerByIndex(0).getDeck().size() +
//                    gameState.getPlayerByIndex(1).getDeck().size());
//
//            gameState.getPlayers().forEach(player -> System.out.println(player.toString()));
//        }
//
//        {
//            pool = board.gatherCardsFromPlayers(gameState);
//            System.out.println(gameState.getPlayerByIndex(0).getDeck().size() +
//                    gameState.getPlayerByIndex(1).getDeck().size());
//            List<Player> winners = new ArrayList<>();
//            winners.addAll(board.determineWarOrWinnerOfTrick(pool));
//
//            System.out.println(gameState.getPlayerByIndex(0).getDeck().size() +
//                    gameState.getPlayerByIndex(1).getDeck().size());
//
//            board.cleanUpTheTrick(gameState, winners, pool);
//
//            System.out.println(gameState.getPlayerByIndex(0).getDeck().size() +
//                    gameState.getPlayerByIndex(1).getDeck().size());
//
//            gameState.getPlayers().forEach(player -> System.out.println(player.toString()));
//        }
//
//        {
//            pool = board.gatherCardsFromPlayers(gameState);
//            if(pool == null){
//                System.out.println(gameState.getPlayers().get(0).getName() + " has won the game");
//            }
//            System.out.println(gameState.getPlayerByIndex(0).getDeck().size() +
//                    gameState.getPlayerByIndex(1).getDeck().size());
//            List<Player> winners = new ArrayList<>();
//            winners.addAll(board.determineWarOrWinnerOfTrick(pool));
//
//            System.out.println(gameState.getPlayerByIndex(0).getDeck().size() +
//                    gameState.getPlayerByIndex(1).getDeck().size());
//
//            board.cleanUpTheTrick(gameState, winners, pool);
//
//            System.out.println(gameState.getPlayerByIndex(0).getDeck().size() +
//                    gameState.getPlayerByIndex(1).getDeck().size());
//
//            gameState.getPlayers().forEach(player -> System.out.println(player.toString()));
//        }
//
//
//    }
}
