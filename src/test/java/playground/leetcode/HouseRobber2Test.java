package playground.leetcode;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class HouseRobber2Test {
    @Test
    public void houseRobber2Test() {
        var testCases = List.of(
                Pair.of(23, List.of(2, 3, 10, 20)),
                Pair.of(3, List.of(2, 3, 2)),
                Pair.of(4, List.of(1, 2, 3, 1)),
                Pair.of(3, List.of(1, 2, 3)));
        var outcome = testCases.stream()
                .map(pair -> Pair.of(pair.getLeft(), HouseRobber2.maxRobbableInCircle(pair.getRight())));
        outcome.forEach(pair -> System.out.println(pair.toString()));
    }
}
