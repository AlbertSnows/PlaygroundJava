package playground.leetcode;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValidParenthesisTest {
    @Test
    public void validateParenthesisTest() {
        var testCases = List.of(
                Pair.of(true, "()"),
                Pair.of(true, "()[]{}"),
                Pair.of(false, "(]"));
        var outcome = testCases.stream()
                .map(pair -> Pair.of(pair.getLeft(),
                        ValidParenthesis.validateParenthesis(pair.getRight())))
                .toList();
        outcome.forEach(pair -> assertEquals(pair.getLeft(), pair.getRight()));
        outcome.forEach(pair -> System.out.println(pair.toString()));
    }
}
