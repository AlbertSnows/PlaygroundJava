package playground.assessment.citadel;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;

public class MinKnightDistance {
    public static List<Pair<Integer, Integer>> moveModifications = List.of(
            Pair.of(-1, -2),
            Pair.of(-2, -1),
            Pair.of(1, -2),
            Pair.of(-1, 2),
            Pair.of(-2, 1),
            Pair.of(2, -1),
            Pair.of(1, 2),
            Pair.of(2, 1));
    @Contract(pure = true)
    public static @NotNull Boolean withinBounds(Integer boardSize, Integer x, Integer y) {
        return x >= 0 && x <= boardSize && y >= 0 && y <= boardSize;
    }
    public static ArrayDeque<DistancePoint> possibleSpaces = new ArrayDeque<>();
    public static HashSet<DistancePoint> visited = new HashSet<>();
    private static Integer findMinimumDistance(int boardSize,
            int startCol, int startRow,
            int endCol, int endRow) {
        var startingPoint = new DistancePoint(startCol, startRow, 0);
        possibleSpaces.add(startingPoint);
        return searchForPoint(boardSize, endCol, endRow);
    }

    private static Integer searchForPoint(int boardSize, int endCol, int endRow) {
        while(!possibleSpaces.isEmpty()) {
            var travelPoint = possibleSpaces.pop();
            if(travelPoint.x == endCol && travelPoint.y == endRow) {
                return travelPoint.dist;
            }
            for(var moveAdjustment : moveModifications) {
                var newSpot = new DistancePoint(
                        travelPoint.x + moveAdjustment.getKey(),
                        travelPoint.y + moveAdjustment.getValue(),
                        travelPoint.dist + 1);
                if(withinBounds(boardSize, newSpot.x, newSpot.y) && !visited.contains(newSpot)) {
                    possibleSpaces.add(newSpot);
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        var boardSize = 9;
        var startCol = 5;
        var startRow = 4;
        var endCol = 9;
        var endRow = 4;
        var minimumDistance = findMinimumDistance(boardSize, startCol, startRow, endCol, endRow);
        System.out.println(minimumDistance);
    }
}
