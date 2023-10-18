package playground.leetcode;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class NumberOfIslands {
    @Contract(pure = true)
    public static Integer findNumberOfIslands(@NotNull List<List<Integer>> islands) {
        var maxLength = islands.size();
        var maxWidth = islands.get(0).size();
        var lengthCounter = 0;
        var widthCounter = 0;
        var numOfIslands = 0;
        Boolean[][] visited = new Boolean[maxLength][maxWidth];
        for(var row : visited) {
            Arrays.fill(row, false);
        }
        while(lengthCounter < maxLength) {
            var currentSpace = islands.get(lengthCounter).get(widthCounter);
            var isWater = currentSpace == 0;
            var wasVisited = visited[lengthCounter][widthCounter];
            if(!wasVisited && isWater) {
                visited[lengthCounter][widthCounter] = true;
            } else if(!isWater && !wasVisited) {
                numOfIslands++;
                markVisited(islands, maxLength, maxWidth, visited, lengthCounter, widthCounter);
            }
            var reachedMax = widthCounter + 1 >= maxWidth;
            widthCounter = reachedMax? 0 : widthCounter + 1;
            lengthCounter = reachedMax? lengthCounter + 1: lengthCounter;
        }
        return numOfIslands;
    }

    private static void
    markVisited(@NotNull List<List<Integer>> islands,
                int maxLength, int maxWidth,
                Boolean[] @NotNull [] visited,
                int lengthCounter, int widthCounter) {
        visited[lengthCounter][widthCounter] = true;
        var eastIndex = widthCounter + 1;
        var southIndex = lengthCounter + 1;
        var eastOutOfBounds = eastIndex == maxWidth;
        var southOutOfBounds = southIndex == maxLength;
        var eastIsIsland = !eastOutOfBounds &&
                islands.get(lengthCounter).get(eastIndex) == 1;
        var southIsIsland = !southOutOfBounds &&
                islands.get(southIndex).get(widthCounter) == 1;
        if(eastIsIsland) {
            markVisited(islands, maxLength, maxWidth, visited, lengthCounter, eastIndex);
        }
        if(southIsIsland) {
            markVisited(islands, maxLength, maxWidth, visited, southIndex, widthCounter);
        }
    }
}
