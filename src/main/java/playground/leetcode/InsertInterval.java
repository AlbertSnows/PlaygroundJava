package playground.leetcode;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class InsertInterval {
    @Contract("_, _ -> param1")
    public static @NotNull ArrayList<Pair<Integer, Integer>>
    insertInterval(@NotNull ArrayList<Pair<Integer, Integer>> currentIntervals,
                   @NotNull Pair<Integer, Integer> newInterval) {
        var newStart = newInterval.getLeft();
        var newEnd = newInterval.getRight();
        var found = false;
        var index = 0;
        var size = currentIntervals.size();
        var newIntervals = new ArrayList<Pair<Integer, Integer>>();
        for(var pair : currentIntervals) {
            var currentStart = pair.getLeft();
            var currentEnd = pair.getRight();
            var updatedStart = Math.max(currentStart, newStart);
            var updatedEnd = Math.max(currentEnd, newEnd);
        }


        return currentIntervals;
    }
}
