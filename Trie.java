/*
  Author: Jaden Krekow, Artar Quarra
  Email: jkrekow2021@my.fit.edu, aquarra2020@my.fit.edu
  Course: CSE2010
  Section: 12
  Description of this file: Create Trie organization structure, manages already guessed words
*/

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/*
    This class uses a tree to store nodes.
    It can add word, guess word and reset tree.

 */
public class Trie {

    // default constructor
    public Trie() {
        root = new TrieNode();
        listOfGuessedWords = new ArrayList<>();
    }

    // this function adds a word in the tree
    public TrieNode addWord(String word) {
        return root.
                addWord(word.
                        toLowerCase());
    }

    // constrcuts a map if charcaters at every level
    public boolean constructTreeMap(char c) {
        // get node
        TrieNode node = root.getNode(c);
        //return false if c doesn't exist in the Trie
        if (node == null) {
            return false;
        }
        List<TrieNode> list = node.guessWords();
        while (!list.isEmpty()) {
            TrieNode n = list.remove(0);
            n.constructMap();
        }
        return true;
    }
    //guessWords guess 3 words in the Trie with the given prefix

    public String[] guessWords(String pref) {
        TrieNode node = root;

        // find node that contains prefix
        for (int i = 0; i < pref.length(); i = i + 1) {
            node = node.getNode(pref.charAt(i));
            //return empty string if the wanted node doesn't exist in the Trie
            if (node == null) {
                return new String[3];
            }
        }

        List<Integer> mapKeys = new ArrayList<>(node.childrenTree.keySet());
        List<String> guessedWord = new ArrayList<>();
        Comparator<Integer> descendingSorter = Collections.reverseOrder();
        // sort keys in descending order
        Collections.sort(mapKeys, descendingSorter);

        int j = 0;
        // add words
        while (guessedWord.size() < 3 && j < mapKeys.size()) {
            int k = mapKeys.get(j);
            TrieNode n = node.childrenTree.get(k);
            if (n.isGuessedNode == false) {
                guessedWord.add(n.toString());
                n.isGuessedNode = true;
                listOfGuessedWords.add(n);
            }

            j = j + 1;
        }
        return guessedWord.toArray(new String[3]);
    }

    // reset clears the list that stores guessed words
    public void reset() {
        for (int j = 0; j < listOfGuessedWords.size(); j = j + 1) {
            listOfGuessedWords.get(j).isGuessedNode = false;
        }
        listOfGuessedWords.clear();
    }

    //getRoot returns root of the Trie
    public TrieNode getRoot() {
        return this.root;
    }

    private TrieNode root;
    // stores list of previously guessed words
    List<TrieNode> listOfGuessedWords;
    //Constructor
}
