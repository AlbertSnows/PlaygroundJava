package playground.leetcode;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HouseRobber2Test {
    @Test
    public void houseRobber2Test() {
        var input = List.of(2, 3, 10, 20);
        var outcome = HouseRobber2.maxRobbableInCircle(input);
        assertEquals(23, outcome);

    }
}
