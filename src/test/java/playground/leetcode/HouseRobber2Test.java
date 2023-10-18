package playground.leetcode;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HouseRobber2Test {
    @Test
    public void houseRobber2Test() {
        var input = List.of(2, 3, 2);
        var outcome = HouseRobber2.maxRobbable(input);
        assertEquals(3, outcome);

    }
}
