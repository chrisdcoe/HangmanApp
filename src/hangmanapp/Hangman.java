/*
 * Copyright 2019 Christopher Coe
 * https://github.com/chrisdcoe
 */

package hangmanapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/* @author Chris */
public class Hangman {
    String mysteryWord;
    StringBuilder currentGuess;
    ArrayList<Character> previousGuesses = new ArrayList<>();
    
    int maxTries = 6;
    int currentTry = 0;
    
    ArrayList<String> dictionary = new ArrayList<>();
    private static FileReader fileReader;
    private static BufferedReader bufferedFileReader;
    
    public Hangman() throws IOException {
        initializeStreams();
        mysteryWord = pickWord();
        currentGuess = initializeCurrentGuess();
        
    }

    public void initializeStreams() throws IOException {
        try {
            File inFile = new File("dictionary.txt");
            fileReader = new FileReader(inFile);
            bufferedFileReader = new BufferedReader(fileReader);
            String currentLine = bufferedFileReader.readLine();
            while (currentLine != null) {
                dictionary.add(currentLine);
                currentLine = bufferedFileReader.readLine();
            }
            bufferedFileReader.close();
            fileReader.close();
        } catch(IOException e) {
            System.out.println("Could not init streams");
        }
    }

    public String pickWord() {
        Random rand = new Random();
        int wordIndex = Math.abs(rand.nextInt()) % dictionary.size();
        return dictionary.get(wordIndex);
    }

    public StringBuilder initializeCurrentGuess() {
        StringBuilder current = new StringBuilder();
        for (int i = 0; i < mysteryWord.length() * 2; i++) {
            if (i % 2 == 0) {
                current.append("_");
            } else {
                current.append(" ");
            }
        }
        return current;
    }
    
    public String getFormalCurrentGuess() {
        return "Current Guess: " + currentGuess.toString();
    }
    
    public boolean gameOver() {
        System.out.println();
        if (didWeWin()) {
            System.out.println("Congratulations! You guessed the word.");
            return true;
        } else if (didWeLose()) {
            System.out.println("You failed. Game over. "
                    + "\nThe correct word was " + mysteryWord + ".");
            return true;
        } // Else, the game isn't over yet. Keep playing!
        return false;
    }
    
    public boolean didWeWin() {
        // Condense guess into a word with no spaces
        String guess = getCondensedCurrentGuess();
        return guess.equals(mysteryWord);
    }
    
    public String getCondensedCurrentGuess() {
        String guess = currentGuess.toString();
        return guess.replace(" ", "");
    }
    
    public boolean didWeLose() {
        return currentTry >= maxTries;
    }
    
    public String drawPicture() {
        switch(currentTry) {
            case 0: return noPersonDraw();
            case 1: return addHeadDraw();
            case 2: return addBodyDraw();
            case 3: return addOneArmDraw();
            case 4: return addSecondArmDraw();
            case 5: return addOneLegDraw();
            default: return fullPersonDraw();
        }
    }

    private String noPersonDraw() {
        return " - - - - -\n" +
               "|        |\n" +
               "|         \n" +
               "|            \n" +
               "|         \n" +
               "|           \n" +
               "|\n" +
               "|\n";
    }

    private String addHeadDraw() {
        return " - - - - -\n" +
               "|        |\n" +
               "|        O\n" +
               "|            \n"+
               "|         \n" +
               "|           \n" +
               "|\n" +
               "|\n";
    }

    private String addBodyDraw() {
        return " - - - - -\n"+
               "|        |\n"+
               "|        O\n" +
               "|        |   \n"+
               "|        |\n" +
               "|           \n" +
               "|\n" +
               "|\n";
    }

    private String addOneArmDraw() {
        return " - - - - -\n"+
               "|        |\n"+
               "|        O\n" +
               "|      / |   \n"+
               "|        |\n" +
               "|           \n" +
               "|\n" +
               "|\n";
    }

    private String addSecondArmDraw() {
        return " - - - - -\n"+
               "|        |\n"+
               "|        O\n" +
               "|      / | \\ \n"+
               "|        |\n" +
               "|           \n" +
               "|\n" +
               "|\n";
    }

    private String addOneLegDraw() {
        return " - - - - -\n"+
               "|        |\n"+
               "|        O\n" +
               "|      / | \\ \n"+
               "|        |\n" +
               "|       /   \n" +
               "|\n" +
               "|\n";
    }

    private String fullPersonDraw() {
        return " - - - - -\n"+
               "|        |\n"+
               "|        O\n" +
               "|      / | \\ \n"+
               "|        |\n" +
               "|       / \\ \n" +
               "|\n" +
               "|\n";
    }

    boolean isGuessedAlready(char guess) {
        return previousGuesses.contains(guess);
    }

    boolean playGuess(char guess) {
        boolean isItCorrect = false;
        previousGuesses.add(guess);
        for (int i = 0; i < mysteryWord.length(); i++) {
            if (mysteryWord.charAt(i) == guess) {
                currentGuess.setCharAt(i * 2, guess);
                isItCorrect = true;
            }
        }
        
        if (!isItCorrect) {
            currentTry++;
        }
        
        return isItCorrect;
    }
    
}
