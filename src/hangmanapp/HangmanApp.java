/*
 * Copyright 2019 Christopher Coe
 * https://github.com/chrisdcoe
 */

package hangmanapp;

import java.io.IOException;
import java.util.Scanner;

/* @author Chris */
public class HangmanApp {

    public static void PrintInstructions() {
        System.out.println("Welcome to Hangman! I will pick a word, and you will "
                + "try to guess it one letter at a time.\nIf you guess wrong 6 times, then I win. "
                + "If you can guess it before then, you win.");
        System.out.println();
        System.out.println("I have picked my word.\nBelow is a picture, and below "
                + "that is your current guess, which starts off as nothing.\n"
                + "Every time you guess incorrectly, I add a body part to the picture.\n"
                + "When there is a full person, you lose.");
        System.out.println();
    }
    
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        PrintInstructions();
        boolean doYouWantToPlay = true;
        while (doYouWantToPlay) {
            //Setting up the game
            System.out.println("Alright, let's play.");
            Hangman game = new Hangman();
            
            // Initial drawing
            System.out.println();
            System.out.println(game.drawPicture());
                
            do {
                // Show guess status
                System.out.println(game.getFormalCurrentGuess());
                //System.out.println(game.mysteryWord);
            
                // Get guess
                System.out.println("Enter a letter: ");
                char guess = (sc.next().toLowerCase()).charAt(0);
                System.out.println();
                
                // Check if character has been guessed already
                while (game.isGuessedAlready(guess)) {
                    System.out.println("Already guessed that letter. Try another one.");
                    guess = (sc.next().toLowerCase()).charAt(0);
                }
                
                // Play guess
                
                if (game.playGuess(guess)) {
                    System.out.println("Success. You guessed a letter correctly.");
                } else {
                    System.out.println("Incorrect guess.");
                }
                
                // Update drawing
                System.out.println();
                System.out.println(game.drawPicture());
            }
            while (!game.gameOver());
                

            // keep playing
            System.out.println();
            System.out.println("Do you want to play again? Enter Y if you do.");
            Character response = (sc.next().toUpperCase()).charAt(0);
            doYouWantToPlay = (response == 'Y');
        }
    }

}
