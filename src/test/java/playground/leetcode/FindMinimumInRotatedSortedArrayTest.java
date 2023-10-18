package playground.leetcode;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindMinimumInRotatedSortedArrayTest {
    @Test
    public void findMinimumInRotatedSortedArrayTest() {
        var testCases = List.of(
                Pair.of(1, List.of(3, 4, 5, 1, 2)),
                Pair.of(0, List.of(4, 5, 6, 7, 0, 1, 2)),
                Pair.of(11, List.of(11, 13, 15, 17)));
        var outcome = testCases.stream()
                .map(pair -> Pair.of(pair.getLeft(),
                        MinimumInRotatedSortedArray.findMinimumInRotatedSortedArrayTest(pair.getRight())))
                .toList();
        outcome.forEach(pair -> assertEquals(pair.getLeft(), pair.getRight()));
        outcome.forEach(pair -> System.out.println(pair.toString()));
    }
}
