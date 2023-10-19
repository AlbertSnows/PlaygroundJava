package playground.assessment;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import playground.leetcode.MinimumInRotatedSortedArray;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MinimumChangesTest {
    @Test
    public void calcMinimumMovesTest() {
        var testCases = List.of(
                Pair.of(3, new int[]{1, 1, 3, 4, 4, 4}),
                Pair.of(4, new int[]{1, 2, 2, 2, 5, 5, 5, 8}));
        var outcome = testCases.stream()
                .map(pair -> Pair.of(pair.getLeft(),
                        MinimumChanges.calcMinimumChanges(pair.getRight())))
                .toList();
        outcome.forEach(pair -> assertEquals(pair.getLeft(), pair.getRight()));
        outcome.forEach(pair -> System.out.println(pair.toString()));
    }
}
