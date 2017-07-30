package com.hmccardell.entities;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.hmccardell.Constants.*;

/**
 * A class for the main engine of the game.
 *
 * @author hmccardell
 */
public class Board {

    private int _millisecondDelay;
    private boolean _verboseMode;
    private GameState _gameState;

    public GameState get_gameState() {
        return _gameState;
    }

    public void set_gameState(GameState _gameState) {
        this._gameState = _gameState;
    }

    public int get_millisecondDelay() {
        return _millisecondDelay;
    }

    public void set_millisecondDelay(int _millisecondDelay) {
        this._millisecondDelay = _millisecondDelay;
    }

    public boolean is_verboseMode() {
        return _verboseMode;
    }

    public void set_verboseMode(boolean _verboseMode) {
        this._verboseMode = _verboseMode;
    }

    /**
     * Checks the game state for the number of players and divides the game deck into even player decks.
     * It also checks for remainder cards and deals them out to each player until no cards remain.
     *
     * @param gameDeck a deck of cards to divide among players
     * @return true if the deck is empty, false if it was null or not empty
     */
    public boolean dealCards(List<Card> gameDeck) {

        if (gameDeck == null || gameDeck.isEmpty()) {
            return false;
        }

        int remainder = 52 % _gameState.getPlayers().size();
        int numberOfCards = 52 / _gameState.getPlayers().size();
        for (Player player : _gameState.getPlayers()) {
            List<Card> playerDeck = new ArrayList<>();
            for (int i = 0; i <= numberOfCards - 1; ++i) {
                dealCardToPlayer(gameDeck, playerDeck);
            }
            if (remainder > 0) {
                dealCardToPlayer(gameDeck, playerDeck);
                --remainder;
            }
            player.setDeck(playerDeck);
        }

        return (gameDeck.isEmpty());
    }

    /**
     * Removes a card from the gameDeck and adds it to a playerDeck
     *
     * @param gameDeck   a list of cards to remove a card from
     * @param playerDeck a list of cards to add the removed card to
     */
    public void dealCardToPlayer(List<Card> gameDeck, List<Card> playerDeck) {

        if (!gameDeck.isEmpty()) {
            Card card = gameDeck.remove(0);
            playerDeck.add(card);
        }
    }

    /**
     * Populates a list with the standard deck of 52 playing cards
     *
     * @param deck the list to populate
     */
    public static void populateDeck(List<Card> deck) {

        for (int i = 2; i < 15; i++) {
            deck.add(new Card(i, Suit.CLUBS));
            deck.add(new Card(i, Suit.DIAMONDS));
            deck.add(new Card(i, Suit.HEARTS));
            deck.add(new Card(i, Suit.SPADES));
        }
    }

    /**
     * Prompts the user for the number of players and a prompts the user for a name for each player
     *
     * @param br a buffered reader to use for reading lines from the console
     */
    public void setupPlayers(BufferedReader br) {

        GameState gameState = new GameState();

        int numberOfPlayers = 0;

        do {
            System.out.print(INTRODUCTION);

            try {
                numberOfPlayers = Integer.parseInt(br.readLine());
            } catch (Exception e) {
                System.out.println(UNACCEPTIBLE_INPUT_TRY_AGAIN);
            }

        }
        while (numberOfPlayers < 2 || numberOfPlayers > 4);

        List<Player> playerList = new ArrayList<>();

        for (int i = 1; i <= numberOfPlayers; i++) {
            Player player = new Player();
            String promptedName = promptName(br);
            if (promptedName == null || promptedName.isEmpty()) {
                player.setName("Player " + i);
            } else {
                player.setName(promptedName);
            }

            playerList.add(player);
        }

        _gameState.setPlayers(playerList);
    }

    /**
     * Prompts the user for game settings: verbosity and delay
     *
     * @param br the buffered reader for reading lines in from the console
     */
    public void promptSettings(BufferedReader br) {
        System.out.println(VERBOSITY_PROMPT);
        String verbosity = "";

        try {
            verbosity = br.readLine().trim();
        } catch (IOException e) {
            System.out.println(VERBOSITY_ERROR);
            _verboseMode = false;
        }

        if (verbosity != null && verbosity.equals("y") || verbosity.equals("Y")) {
            _verboseMode = true;
        } else {
            _verboseMode = false;
        }

        System.out.println(DELAY_PROMPT);
        int delay = 0;
        String lineFeed = "";
        try {
            lineFeed = br.readLine().trim().toString();
        } catch (IOException e) {
            System.out.println(DELAY_ERROR);
            _millisecondDelay = 0;
        }
        try {
            delay = Integer.parseInt(lineFeed);
        } catch (Exception e) {
            System.out.println(DELAY_ERROR);
            _millisecondDelay = 0;
        }

        _millisecondDelay = delay;

    }

    /**
     * Prompts the players for their name and captures the input
     *
     * @param br the buffered reader to use to read in lines from the console
     * @return the String name the user entered
     */
    private static String promptName(BufferedReader br) {
        System.out.println(PROMPT_PLAYER_NAME);
        String name = "";

        //TODO Improvement: detect if two players names are the same
        //TODO Refactor: pull buffer readlines out to a reusable method to keep it DRY
        try {
            name = br.readLine().trim();
        } catch (IOException e) {
            System.out.println(ERROR_NAME_TRY_AGAIN);
            name = null;
        }

        while (name == null || name.equals("")) {
            System.out.println(REPEATING_NAME_PROMPT);
            try {
                name = br.readLine().trim();
            } catch (IOException e) {
                System.out.println(ERROR_NAME_TRY_AGAIN);
                name = null;
            }
        }

        System.out.println("Thanks for the input, " + name + ". Next...");

        return name;
    }

    /**
     * Checks the gameState for the number of current players.
     * If only one player remains, they have won, and the gamestate is set.
     *
     * @return true if a player has won, otherwise false
     */
    public boolean checkIfGameOver() {
        if (_gameState.getPlayers() == null || _gameState.getPlayers().size() < 2) {
            System.out.println(GAME_OVER_LESS_THAN_TWO_PLAYERS);
            _gameState.setGameOver(true);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes any player with an empty deck from the game;
     */
    public void removePlayersWithEmptyDecksFromTheGame() {
        List<Player> playersToRemove = new ArrayList<>();
        for (Player player : _gameState.getPlayers()) {
            if (player.deckIsEmpty()) {
                displayRemovalFromGame(player);
                playersToRemove.add(player);
            }
        }

        _gameState.getPlayers().removeAll(playersToRemove);
    }

    /**
     * Gathers a card from each player in the game and puts them into a list. If the cards are to be
     * considered face up, eg: they are about to be used to determine the outcome of a trick, then
     * the state of the cards will be set to faceUp.
     * <p>
     * Otherwise, the gathered cards are to be used for the pot and should not be checked for winning
     * a trick, so they are added to the pot face down.
     *
     * @param playersToGatherFrom the players to gather cards from, usually all players for a regular
     *                            trick, or only the participants of a war inside a war
     * @param faceUp              the state of the cards to be gathered from the players
     * @return
     */
    public List<TrickCard> gatherCardsFromPlayers(List<Player> playersToGatherFrom, boolean faceUp) {
        List<TrickCard> pool = new ArrayList<>();
        boolean gameOver = checkIfGameOver();
        StringBuilder builder = new StringBuilder();
        //remove a card from every player's deck and put it into a pool
        for (Player player : playersToGatherFrom) {
            if (player.getDeck() != null && !player.deckIsEmpty()) {
                Card playerCard = player.getDeck().remove(0);
                //If the cards should be gathered face up, set their state to face up
                if (faceUp) {
                    pool.add(new TrickCard(playerCard, player, true));
                    builder.append("\n");
                    builder.append(player.getName() + " plays a " + playerCard.getName() + ". ");
                } else {
                    pool.add(new TrickCard(playerCard, player, false));
                    builder.append("Card was added to the pot face down for " + player.getName() + ". ");
                }
            }
        }
        if (_verboseMode) {
            System.out.println(builder.toString());
        }
        return pool;
    }

    /**
     * Takes in a list of cards, determines the card with the highest value and returns it.
     *
     * @param pool the list of cards to check the values of
     * @return null if the pool is null, otherwise the TrickCard with the highest value in the list
     */
    public TrickCard determineHighestCard(List<TrickCard> pool) {

        if (pool == null) {
            return null;
        }

        int valueOfHighestCard = -1;
        TrickCard winningCard = null;

        //find the card in the pool with the highest value
        for (TrickCard card : pool) {
            if (card.getValue() > valueOfHighestCard && card.isFaceUp()) {
                valueOfHighestCard = card.getValue();
                winningCard = card;
            }
        }

        return winningCard;
    }

    /**
     * Takes a list of cards and a winning card, and checks the pool to see if any
     * cards match that value. If one or more cards do match values with the card
     * that was passed in, then the owner of those cards are added to the list of
     * winners.  If no other cards match, then only the original winning card's
     * owner is added to the winner list.
     *
     * @param pool        a list of TrickCards to check the values agains the winningCard
     * @param winningCard the highvalue card to check against the pool
     * @return the list of players that have the matching highest value cards
     */
    public List<Player> determineWar(List<TrickCard> pool, TrickCard winningCard) {

        if (pool == null || winningCard == null) {
            return null;
        }

        List<Player> winnerList = new ArrayList<>();
        StringBuilder builder = new StringBuilder();

        //check the pool to see if any values match the value of the highest card
        if (!pool.isEmpty() && pool.size() > 0) {
            for (TrickCard card : pool) {
                if (card.getValue() == winningCard.getValue()
                        && card.getPlayer().getName() != winningCard.getPlayer().getName()
                        && card.isFaceUp()) {
                    winnerList.add(card.getPlayer());
                    builder.append(card.getPlayer().getName() + " joined the war. ");
                }
            }
        }

        //whether or not war occurs, add the initial cardholder
        winnerList.add(winningCard.getPlayer());
        if (winnerList.size() > 1) {
            builder.append(winningCard.getPlayer().getName() + " joined the war.");
        }

        if (_verboseMode) {
            System.out.println(builder.toString());
        }

        return winnerList;
    }

    /**
     * The recursive handler function that calls other functions to handle an entire trick.
     *
     * @param pot the list of cards for the method to handle
     */
    public void handleTrick(List<TrickCard> pot) {
        // The key for this method was tracking which cards were face up and which were face down.
        // Previous to that, cards which had been drawn "face down" were still being checked by the
        // game engine and were winning wars.

        if (pot == null || pot.isEmpty()) {
            return;
        }

        TrickCard winningCard = determineHighestCard(pot);
        List<Player> warList = determineWar(pot, winningCard);
        if (warList.size() > 1) {
            pot.addAll(gatherCardsFromPlayers(warList, false));
            for (TrickCard card : pot) {
                card.setFaceUp(false);
            }
            pot.addAll(gatherCardsFromPlayers(warList, true));
            handleTrick(pot);
        } else {
            cleanUpTheTrick(winningCard.getPlayer(), pot);
        }
    }

    /**
     * A method for cleaning up the state at the end of a trick. It takes the winning player
     * and the pot, displays the winner of the trick to the user, and adds the pot of cards
     * to the winning players deck.
     *
     * @param winningPlayer the player who won the trick
     * @param pot           the pot of cards that the winning player won
     */
    public void cleanUpTheTrick(Player winningPlayer, List<TrickCard> pot) {

        StringBuilder builder = new StringBuilder();

        if (pot != null && winningPlayer != null && !checkIfGameOver()) {
            builder.append(winningPlayer.getName() + " wins the trick, ");

            for (TrickCard cardGoingToWinner : pot) {
                builder.append(cardGoingToWinner.getName(cardGoingToWinner.getValue()) + " ");
                winningPlayer.addCardToPlayerDeck((new Card(cardGoingToWinner.getValue(), cardGoingToWinner.suit)));
            }
        }

        builder.append("added to their deck.");
        if (_verboseMode) {
            System.out.println(builder.toString().trim());
        }
    }

}




