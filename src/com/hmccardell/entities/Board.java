package com.hmccardell.entities;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.hmccardell.entities.Constants.*;

/**
 * A class for the main engine of the game.
 *
 * @author hmccardell
 */
public class Board {

    private int _millisecondDelay;
    private boolean _verboseMode;

    public static void dealCards(List<WarCard> gameDeck, GameState gameState) {
        int remainder = 52 % gameState.getPlayers().size();
        int numberOfCards = 52 / gameState.getPlayers().size();
        //System.out.println(gameState.getPlayers().size() + " players");
        //System.out.println(numberOfCards + " cards per player with " + remainder + " cards leftover and distributed to players in order");
        for (Player player : gameState.getPlayers()) {
            List<WarCard> playerDeck = new ArrayList<>();
            for (int i = 0; i <= numberOfCards - 1; ++i) {
                dealCardToPlayer(gameDeck, playerDeck);
            }
            if (remainder > 0) {
                dealCardToPlayer(gameDeck, playerDeck);
                --remainder;
            }
            player.setDeck(playerDeck);
            //System.out.println(player.getName() + " has " + playerDeck.size() + " cards");
        }
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

    public static void dealCardToPlayer(List<WarCard> gameDeck, List<WarCard> playerDeck) {

        if (!gameDeck.isEmpty()) {
            WarCard card = gameDeck.remove(0);
            playerDeck.add(card);
        }
    }

    public static void populateDeck(List<WarCard> deck) {

        for (int i = 2; i < 15; i++) {
            deck.add(new WarCard(i, Suit.CLUBS));
            deck.add(new WarCard(i, Suit.DIAMONDS));
            deck.add(new WarCard(i, Suit.HEARTS));
            deck.add(new WarCard(i, Suit.SPADES));
        }
    }

    public static void setupPlayers(GameState gameState, BufferedReader br) {

        int numberOfPlayers = 0;

        do {
            System.out.print(INTRODUCTION);

            try {
                numberOfPlayers = Integer.parseInt(br.readLine());
            } catch (Exception e) {
                System.out.println(UNACCEPTIBLE_INPUT_TRY_AGAIN);
            }

            if (numberOfPlayers == 9) {
                System.out.println(SORRY_NO_GAME);
                break;
            }
        }
        while (numberOfPlayers < 2 || numberOfPlayers > 4);

        if (numberOfPlayers == 9) {
            return;
        }

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

        gameState.setPlayers(playerList);
    }

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

    private boolean checkIfGameOver(GameState gameState) {

        if (gameState.getPlayers() == null) {
            System.out.println(GAME_OVER_LESS_THAN_TWO_PLAYERS);
            gameState.setGameOver(true);
            return true;
        }

        for (Player player : gameState.getPlayers()) {
            if (player.getDeck() == null || player.getDeck().isEmpty()) {
                gameState.getPlayers().remove(player);
            }
            if (gameState.getPlayers().size() < 2) {
                System.out.println(GAME_OVER_LESS_THAN_TWO_PLAYERS);
                gameState.setGameOver(true);
                return true;
            }
        }

        return false;
    }

    public List<TrickCard> gatherCardsFromPlayers(List<Player> playersToGatherFrom, GameState gameState, boolean faceUp) {
        List<TrickCard> pool = new ArrayList<>();
        boolean gameOver = checkIfGameOver(gameState);
        StringBuilder builder = new StringBuilder();
        if (!gameOver) {
            //remove a card from every player's deck and put it into a pool
            for (Player player : playersToGatherFrom) {
                WarCard playerCard = player.getDeck().remove(0);
                if (faceUp) {
                    pool.add(new TrickCard(playerCard, player, true));
                    builder.append(player.getName() + " plays a " + playerCard.getName() + ". ");
                } else {
                    pool.add(new TrickCard(playerCard, player, false));
                    builder.append("Card was added to the pot face down for " + player.getName() + " ");
                }
            }
            System.out.println(builder.toString());
            return pool;
        } else {
            return null;
        }

    }

    public TrickCard determineHighestCard(List<TrickCard> pool) {

        if (pool == null) {
            return null;
        }

        int valueOfHighestCard = -1;
        TrickCard winningCard = null;
        List<Player> winnerList = new ArrayList<>();

        //find the highest value and track the player
        for (int i = 0; i < pool.size(); i++) {
            if (pool.get(i).getValue() > valueOfHighestCard && pool.get(i).isFaceUp()) {
                valueOfHighestCard = pool.get(i).getValue();
                winningCard = pool.get(i);
            }
        }
        return winningCard;
    }

    public List<Player> determineWar(List<TrickCard> pool, TrickCard winningCard) {

        if (pool == null || winningCard == null) {
            return null;
        }

        List<Player> winnerList = new ArrayList<>();

        //check the pool to see if any values match the value of the highest card
        if (!pool.isEmpty() && pool.size() > 0) {
            for (int i = 0; i < pool.size(); i++) {
                //if they match, add that player to the winner list
                if (pool.get(i).getValue() == winningCard.getValue()
                        && pool.get(i).getPlayer().getName() != winningCard.getPlayer().getName()
                        && pool.get(i).isFaceUp() == true) {
                    winnerList.add(pool.get(i).getPlayer());
                    System.out.println(pool.get(i).getPlayer().getName() + " joined the war");
                }
            }
        }

        //whether or not war occurs, add the initial cardholder
        winnerList.add(winningCard.getPlayer());
        if (winnerList.size() > 1) {
            System.out.println(winningCard.getPlayer().getName() + " joined the war");
        }

        return winnerList;
    }

    public void handleTrick(GameState gameState, List<TrickCard> pot) {
        TrickCard winningCard = determineHighestCard(pot);
        List<Player> warList = determineWar(pot, winningCard);
        if (warList.size() > 1) {
            pot.addAll(gatherCardsFromPlayers(warList, gameState, false));
            for (TrickCard card : pot) {
                card.setFaceUp(false);
            }
            pot.addAll(gatherCardsFromPlayers(warList, gameState, true));
            handleTrick(gameState, pot);
        } else {
            cleanUpTheTrick(gameState, winningCard.getPlayer(), pot);
        }
    }

    public void cleanUpTheTrick(GameState gameState, Player winningPlayer, List<TrickCard> pot) {

        StringBuilder builder = new StringBuilder();
        builder.append(winningPlayer.getName() + " wins the trick, ");

        if (pot != null && winningPlayer != null && !checkIfGameOver(gameState)) {
            for (TrickCard cardGoingToWinner : pot) {
                builder.append(cardGoingToWinner.getName(cardGoingToWinner.getValue()));
                winningPlayer.addCardToPlayerDeck((new WarCard(cardGoingToWinner.getValue(), cardGoingToWinner.suit)));
            }
        }

        builder.append("added to their deck");
        System.out.println(builder.toString());
    }

}




