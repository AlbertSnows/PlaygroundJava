package playground.leetcode;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.List;

public class GroupAnagramsTest {
    @Test
    public void getAnagramGroups() {
        var testCases = List.of(
                Pair.of(List.of(List.of("bat"), List.of("nat","tan"), List.of("ate","eat","tea")),
                        List.of("eat","tea","tan","ate","nat","bat")),
                Pair.of(List.of(List.of("")), List.of("")),
                Pair.of(List.of(List.of("a")), List.of("a")));
        var outcome = testCases.stream()
                .map(pair -> Pair.of(pair.getLeft(), GroupAnagrams.getAnagramGroups(pair.getRight())));
        outcome.forEach(pair -> System.out.println(pair.toString()));
    }
}
