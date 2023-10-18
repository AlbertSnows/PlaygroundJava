package playground.leetcode;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ThreeSumTest {
    @Test
    public void findThreeSumTest() {
        var testCases = List.of(
                Pair.of(List.of(List.of(-1, -1, 2), List.of(-1, 0, 1)), List.of(-1,0,1,2,-1,-4)),
                Pair.of(List.of(), List.of(0, 1, 1)),
                Pair.of(List.of(List.of(0, 0, 0)), List.of(0, 0, 0)));
        var outcome = testCases.stream()
                .map(pair -> Pair.of(pair.getLeft(), ThreeSum.findThreeSum(pair.getRight())))
                .toList();
        outcome.forEach(pair -> System.out.println(pair.toString()));
        outcome.forEach(pair -> assertEquals(pair.getLeft(), pair.getRight()));

    }
}
