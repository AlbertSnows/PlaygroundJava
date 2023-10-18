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
                Pair.of(1, List.of(
                        List.of(1, 1, 1, 1, 0),
                        List.of(1, 1, 0, 1, 0),
                        List.of(1, 1, 0, 0, 0),
                        List.of(0, 0, 0, 0, 0))),
                Pair.of(3, List.of(
                        List.of(1, 1, 0, 0, 0),
                        List.of(1, 1, 0, 0, 0),
                        List.of(0, 0, 1, 0, 0),
                        List.of(0, 0, 0, 1, 1))));
        var outcome = testCases.stream()
                .map(pair -> Pair.of(pair.getLeft(),
                        NumberOfIslands.findNumberOfIslands(pair.getRight())))
                .toList();
        outcome.forEach(pair -> assertEquals(pair.getLeft(), pair.getRight()));
        outcome.forEach(pair -> System.out.println(pair.toString()));
    }
}
