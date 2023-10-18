package playground.leetcode;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumberOfIslandsTest {
    @Test
    public void findNumberOfIslands() {
        var testCases = List.of(
                Pair.of(List.of(1, 4, 2, 3),
                        new LinkedList<>(List.of(1, 2, 3, 4))),
                Pair.of(List.of(1, 5, 2, 4, 3),
                        new LinkedList<>(List.of(1, 2, 3, 4, 5))));
        var outcome = testCases.stream()
                .map(pair -> Pair.of(pair.getLeft(),
                        NumberOfIslands.findNumberOfIslands(pair.getRight())))
                .toList();
        outcome.forEach(pair -> assertEquals(pair.getLeft(), pair.getRight()));
        outcome.forEach(pair -> System.out.println(pair.toString()));
    }
}
