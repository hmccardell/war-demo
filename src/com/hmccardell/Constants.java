package com.hmccardell;

/**
 * A class to track constants for prompts and error messages.
 *
 * @author hmccardell
 */
public class Constants {

    public final static String UNACCEPTIBLE_INPUT_TRY_AGAIN = "Sorry, that wasn't acceptible. Try again.";
    public final static String SORRY_NO_GAME = "Sorry you don't want to play, bye for now!";
    public final static String PROMPT_PLAYER_NAME = "Please enter the name for the new player: ";
    public final static String REPEATING_NAME_PROMPT = "Here's another shot, enter a good name for your new player: ";
    public final static String ERROR_NAME_TRY_AGAIN = "An unexpected error occurred, can you try that again?";
    public final static String INTRODUCTION = "\n\nWelcome to War!\nHow many will be playing? (Enter a numeral between 2 and 4 or 9 to cancel the game): ";
    public final static String GAME_OVER_LESS_THAN_TWO_PLAYERS = "The game has ended because only one player remains";
    public final static String VERBOSITY_PROMPT = "Verbose mode for logging out the results of each trick? [ Enter 'y' or 'n' ] ";
    public final static String VERBOSITY_ERROR = "Unacceptable input, verbosity set to false.";
    public final static String DELAY_PROMPT = "If you would like a delay between each trick, enter a number of milliseconds";
    public final static String DELAY_ERROR = "Unacceptable input, delay set to zero.";

}
