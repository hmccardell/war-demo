package com.hmccardell.entities;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.hmccardell.entities.Constants.*;

/**
 * Created by hmccardell on 7/26/2017.
 */
public class Board {

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

    public List<TrickCard> gatherCardsFromPlayers(GameState gameState) {
        List<TrickCard> pool = new ArrayList<>();
        if (gameState.getPlayers() == null || gameState.getPlayers().size() < 2) {
            System.out.println(GAME_OVER_LESS_THAN_TWO_PLAYERS);
            gameState.setGameOver(true);
            return null;
        }

        //remove a card from every player's deck and put it into a pool
        for (Player player : gameState.getPlayers()) {
            if (player.getDeck() == null || player.getDeck().size() < 1) {
                List<Player> gamePlayers = gameState.getPlayers();
                gamePlayers.remove(player);
                gameState.setPlayers(gamePlayers);
                if (gamePlayers.size() <= 1) {
                    System.out.println(GAME_OVER_LESS_THAN_TWO_PLAYERS);
                    gameState.setGameOver(true);
                    return null;
                }
            }
            WarCard playerCard = player.getDeck().remove(0);
            pool.add(new TrickCard(playerCard, player));
            //System.out.println(player.getName() + " plays a " + playerCard.getValue() + " of " + playerCard.getSuit());
        }

        return pool;
    }

    public List<Player> determineWarOrWinnerOfTrick(List<TrickCard> pool) {

        if (pool == null || pool.isEmpty()) {
            return null;
        }

        int valueOfHighestCard = -1;
        TrickCard winningCard = null;
        List<Player> winnerList = new ArrayList<>();

        //find the highest value and track the player
        for (int i = 0; i < pool.size(); i++) {
            if (pool.get(i).getValue() > valueOfHighestCard) {
                valueOfHighestCard = pool.get(i).getValue();
                winningCard = pool.get(i);
            }
        }

        //check the rest of the pool to see if any values match the value of the highest card
        if (!pool.isEmpty() && pool.size() > 0) {
            for (int i = 0; i < pool.size(); i++) {
                //if they match, update the war flag and add that player to the winner list
                if (pool.get(i).getValue() == valueOfHighestCard && pool.get(i).getPlayer().getName() != winningCard.getPlayer().getName()) {
                    valueOfHighestCard = pool.get(i).getValue();
                    winnerList.add(pool.get(i).getPlayer());
                    System.out.println("WAR DETECTED!");
                }
            }
        }

        //whether or not war occurs, add the initial cardholder
        winnerList.add(winningCard.getPlayer());

        return winnerList;
    }

    public void cleanUpTheTrick(GameState gameState, List<Player> playersWithMatchingCards, List<TrickCard> pool) {

        if (playersWithMatchingCards == null || playersWithMatchingCards.isEmpty() || pool.isEmpty()) {
            System.out.println("PROBLEM WITH GAMESTATE");
            gameState.setGameOver(true);
        } else if (playersWithMatchingCards.size() >= 2) {
            gatherCardsFromPlayers(gameState);
        } else if (playersWithMatchingCards.size() == 1) {
            Player winnerOfTrick = playersWithMatchingCards.remove(0);
            //System.out.println(winnerOfTrick.getName() + " won the trick");
            for (TrickCard cardGoingToWinner : pool) {
                //System.out.println(cardGoingToWinner.getValue() + " of " + cardGoingToWinner.getSuit() + " added to their deck");
                winnerOfTrick.addCardToPlayerDeck((new WarCard(cardGoingToWinner.getValue(), cardGoingToWinner.suit)));
            }
        }
    }

}




