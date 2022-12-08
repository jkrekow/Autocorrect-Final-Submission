/*
  Author: Jaden Krekow, Artar Quarra
  Email: jkrekow2021@my.fit.edu, aquarra2020@my.fit.edu
  Course: CSE2010
  Section: 12
  Description of this file: Creates Trie nodes for Trie organizational structure
*/

import java.util.TreeMap;
import java.util.ArrayList;

import java.util.List;
import java.util.Collections;
import java.util.Map;

import java.util.Comparator;

public class TrieNode {

    // default constructor
    public TrieNode() {
        this.isLastCharacter = false;
        this.isGuessedNode = false;
        this.children = new TrieNode[26];
        this.isLeafNode = true;
        frequency = 0;
    }

    // parameterized constructor
    public TrieNode(char ch) {
        this();
        this.isGuessedNode = false;
        frequency = 0;
        this.character = ch;
    }

    // add word in the tree and count its frquency too
    public TrieNode addWord(String word) {
        // intialize isGuess and isLeaf to false
        isGuessedNode = false;
        isLeafNode = false;
        // get index from first character of the word
        int index = getIndex(word.charAt(0));

        // If index does not exist then add the char in the tree
        if (children[index] == null) {
            // create and store new node
            children[index] = new TrieNode(word.charAt(0));
            children[index].parent = this;
        }

        if (word.length() <= 1) {
            // base case of recursion
            children[index].isLastCharacter = true;
            children[index].frequency += 1;
            return children[index];

        }

        // generate recursive call for substring exclusing the current 
        // character
        return children[index].addWord(word.substring(1));
    }

    //getNode returns the child TrieNode that contains char c, or null if no node exists.
    public TrieNode getNode(char c) {
        int index = getIndex(c);
        if (index < 0) {
            return null;
        }
        return children[index];
    }

    //guessWords returns 3 words that have givien prefix and have most frequencys in the Trie.
    public List<TrieNode> guessWords() {
        List<TrieNode> wordList = new ArrayList<>();              //List store of all words of this node
        //If this node represents a word, add it to the list
        if (isLastCharacter) {
            wordList.add(this);
        }

        //If this node isn't a word, keep travelling until reach the leaf (which is word)
        if (!isLeafNode) {
            //Add any words belonging to any children
            for (int i = 0; i < children.length; i++) {
                if (children[i] != null) {
                    wordList.addAll(children[i].guessWords());
                }

            }
        }
        return wordList;
    }

    //getIndex returns index of this node
    public int getIndex(char c) {
        //This used to calculate the index begin from a = 0 to z = 26
        return c - 'a';
    }

    //toString returns the String that this node represents
    @Override
    public String toString() {
        if (parent != null) {
            return parent.toString() + new String(new char[]{character});
        }
        return "";
    }

    // constrcut frequency map
    public void constructMap() {
        TrieNode ref = this;
        // traverse the tree
        while (ref.parent != null) {
            // create list of map keys
            List<Integer> mapKeys = new ArrayList<>(ref.parent.childrenTree.keySet());
            Comparator<Integer> descendingSorter = Collections.reverseOrder();
            // sort key if map in reverse order
            Collections.sort(mapKeys, descendingSorter);
            // if key size is greater than 8
            if (mapKeys.size() > 8) {
                ref.parent.childrenTree.remove(mapKeys.get(mapKeys.size() - 1));
                ref.parent.childrenTree.put(this.frequency, this);

            } else {
                ref.parent.childrenTree.put(this.frequency, this);
            }
            ref = ref.parent;
        }
    }
    // stores reference if parent node
    public TrieNode parent;
    // stores character
    public char character;
    // flag to indicate leaf node    
    public boolean isLeafNode;
    // stores the frequency of word
    public int frequency = 0;
    // stores list of children node
    public TrieNode[] children;
    public Map<Integer, TrieNode> childrenTree = new TreeMap<>();
    // it indicates whether the charcater is last charcater of the word    
    public boolean isLastCharacter;
    // this flag indicates whether the word is guessed or not
    public boolean isGuessedNode;
}
