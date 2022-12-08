/*
  Author: Jaden Krekow, Artar Quarra
  Email: jkrekow2021@my.fit.edu, aquarra2020@my.fit.edu
  Course: CSE2010
  Section: 12
  Description of this file: Processes inputted txt files, uses Trie data structure to get guessed words
*/

import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;


public class SmartWord {

    // initialize SmartWord with a file of English words
    public SmartWord(String fileName) throws FileNotFoundException {
        this.previousGuess = 0;
        File file = new File(fileName);
        Scanner sc = new Scanner(file);
        this.guessedWords = new String[3];
        this.characterSquence = new String();
        tree = new Trie();
        while (sc.hasNext()) {
            String word = sc.next();
            tree.addWord(word);
        }
    }


    // based on a letter typed in by the user, return 3 word guesses in an array
    // letter: letter typed in by the user
    // lPos:  position of the letter in the word, starts from 0
    // wPos: position of the word in a message, starts from 0
    public String[] guess(char letter, int lPos, int wPos) {
        if (wPos != previousGuess) {
            previousGuess = wPos;
            resetWord();
        }
        // add the new letter to character sequence
        characterSquence = characterSquence + letter;
        // get list of guessed words
        guessedWords = tree.guessWords(characterSquence);
        // return list
        return guessedWords;
    }

    //resetWord() resets the userWord to an empty string and reset all guess words
    public void resetWord() {
        characterSquence = "";
        tree.reset();
    }

    public void processOldMessages(String fileName) throws FileNotFoundException {
        // open file
        File file = new File(fileName);
        // create scanner
        Scanner sc = new Scanner(file);
        System.out.println("processOldMessages ");
        // read lines untill end of file is reached
        while (sc.hasNextLine()) {
            // split line into words
            String inputLine = sc.nextLine().
                    replaceAll("\\s+", " ");

            String[] splitList = inputLine.split(" ");
            // traverse words
            for (int ind = 0; ind < splitList.length; ind = ind + 1) {
                splitList[ind] = splitList[ind].
                        replaceAll("[^a-zA-Z]", "").toLowerCase();

                // remove unecessary punctuation and add word
                if (!splitList[ind].matches("^.*[^a-zA-Z0-9 ].*$") && !splitList[ind].isEmpty()) {
                    tree.addWord(splitList[ind]);
                }
            }
        }
        // construct map for each level
        for (char ch = 'a'; ch <= 'z'; ch++) {
            tree.constructTreeMap(ch);
        }
    }
    
    // Utilize feedback given by the user
    // if guess was correct then reset te word
    public void feedback(boolean isGuessCorrect, String correctWord) {
        if (isGuessCorrect) {
            resetWord();
        }
    }

    String[] guessedWords;
    String characterSquence;
    int previousGuess;
    static Trie tree;
}
