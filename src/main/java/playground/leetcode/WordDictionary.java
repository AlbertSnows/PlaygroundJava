package playground.leetcode;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

class TrieNode {
    public HashMap<Character, TrieNode> children;
    public Character content;
    public boolean isWord;
    TrieNode() {
        children = new HashMap<>();
        isWord =  false;
    }
}

class WordDictionary {

    public HashMap<Character, TrieNode> children;

    public WordDictionary() {
        children = new HashMap<>();
    }

    public void addWord(@NotNull String word) {
        var startingChar = word.toCharArray()[0];
        TrieNode traversalNode = children.get(startingChar);
        if(traversalNode == null) {
            var newNode = new TrieNode();
            newNode.content = startingChar;
            children.put(startingChar, newNode);
            traversalNode = newNode;
        }
        for (char character: Arrays.copyOfRange(word.toCharArray(), 1, word.length())) {
            var nextNode = traversalNode.children.get(character);
            if(nextNode == null) {
                var newNode = new TrieNode();
                newNode.content = character;
                nextNode = newNode;
                traversalNode.children.put(character, nextNode);
            }
            traversalNode = nextNode;
        }
        traversalNode.isWord = true;
    }

    private boolean wordSearch(@NotNull  TrieNode node,
                               char @NotNull [] characters) {
        if(characters.length == 0) {
            return true;
        }
        var wordLength = characters.length;
        var letterIndex = 0;
        var stillSearching = true;
        var found = false;
        while(stillSearching && letterIndex < wordLength) {
            var currentLetter = characters[letterIndex];
            var isWildCard = currentLetter == '.';
            if(!isWildCard) {
                var nextNode = node.children.get(currentLetter);
                stillSearching = nextNode != null;
                node = nextNode;
                letterIndex++;
                found = letterIndex >= wordLength;
            } else {
                var remainingCharacters =
                        Arrays.copyOfRange(characters, letterIndex + 1, wordLength);
                found = node.children.values().stream()
                        .anyMatch(nextNode -> wordSearch(nextNode, remainingCharacters));
                stillSearching = false;
            }
        }
        return found;

    }

    public boolean search(@NotNull String word) {
        var wordAsCharArray = word.toCharArray();
        var currentNode = children.get(wordAsCharArray[0]);
        if(currentNode == null && wordAsCharArray[0] != '.') {
            return false;
        } else if(wordAsCharArray[0] == '.') {
            var remainingCharacters =
                    Arrays.copyOfRange(wordAsCharArray, 1, wordAsCharArray.length);
            var outcome = children.values().stream()
                    .anyMatch(pNode -> pNode.children.values().stream()
                            .anyMatch(nextNode -> {
                                //todo: fix the offset issue
                                return wordSearch(nextNode, remainingCharacters);
                            }));
            return outcome;
        }
        var outcome = wordSearch(currentNode, Arrays.copyOfRange(wordAsCharArray, 1, wordAsCharArray.length));
        return outcome;
    }



    public static void main(String[] args) {
        var x = new WordDictionary();
        x.addWord("bad");
        x.addWord("dad");
        x.addWord("mad");
        var a = x.search("pad");
        var b = x.search("bad");
        var c = x.search(".ad");
        var d = x.search("b..");
    }
}