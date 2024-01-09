package playground.leetcode;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Solution {

    public static boolean searchWord(
            char[][] board,
            AbstractMap.SimpleEntry<Integer, Integer> currentPosition,
            char[] letters,
            HashSet<AbstractMap.SimpleEntry<Integer, Integer>> visited) {
        if(letters.length == 0) {
            return true;
        } else if(
                currentPosition.getKey() < 0 ||
                        currentPosition.getValue() < 0 ||
                        currentPosition.getValue() >= board.length ||
                        currentPosition.getKey() >= board[0].length ||
                        visited.contains(currentPosition)) {
            return false;
        }
        // check visited
        var columnIndex = currentPosition.getKey();
        var rowIndex = currentPosition.getValue();
        var letter = board[rowIndex][columnIndex];
        var letterMatches = letter == letters[0]; // if no more word, done
        var remainingLetters = Arrays.copyOfRange(letters, 1, letters.length);
        System.out.println(remainingLetters);
        Function<AbstractMap.SimpleEntry<Integer, Integer>, Boolean> searchForNextLetter =
                pair -> searchWord(board, pair, Arrays.copyOfRange(letters, 1, letters.length), visited);
        if(letterMatches) {
            visited.add(new AbstractMap.SimpleEntry<>(columnIndex, rowIndex));
            var up = new AbstractMap.SimpleEntry<>(columnIndex, rowIndex - 1);
            var down = new AbstractMap.SimpleEntry<>(columnIndex, rowIndex + 1);
            var left = new AbstractMap.SimpleEntry<>(columnIndex - 1, rowIndex);
            var right = new AbstractMap.SimpleEntry<>(columnIndex, rowIndex - 1);
            var foundRestOfWord =
                    searchForNextLetter.apply(up) ||
                            searchForNextLetter.apply(down) ||
                            searchForNextLetter.apply(left) ||
                            searchForNextLetter.apply(right);
            if(foundRestOfWord) {
                return true;
            }
        }

        return false;
    }

    public static boolean exist(char[][] board, String word) {
        if(board == null || board.length == 0 || board[0].length == 0) {
            return false;
        }
        var wordAsCharArray = word.toCharArray();
        var visited = new HashSet<AbstractMap.SimpleEntry<Integer, Integer>>();
        var rowIndexes = IntStream.rangeClosed(0, board.length - 1).boxed().toList();
        var columnIndexes = IntStream.rangeClosed(0, board[0].length - 1).boxed().toList();
        var found = false;
        for(var columnIndex : columnIndexes) {
            for(var rowIndex : rowIndexes) {
                var startingPosition = new AbstractMap.SimpleEntry<>(columnIndex, rowIndex);
                var foundWord = searchWord(board, startingPosition, wordAsCharArray, visited);
                if(foundWord) {
                    found = true;
                    break;
                }
            }
        }
        return found;
    }
    public static void main(String[] args) {
//                {{'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}};

        char[][] board =
                {{'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}};
//        [['A','B','C','E'],
//        ['S','F','C','S'],
//        ['A','D','E','E']];
     var word = "ABCCED";
     var output = exist(board, word);
     System.out.println(output);
    }
}