package playground.leetcode;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GroupAnagramsTest {
    @Test
    public void getAnagramGroups() {
        var testCases = List.of(
                Pair.of(List.of(List.of("bat"), List.of("nat","tan"), List.of("ate","eat","tea")),
                        List.of("eat","tea","tan","ate","nat","bat")),
                Pair.of(List.of(List.of("")), List.of("")),
                Pair.of(List.of(List.of("a")), List.of("a")));
        var outcome = testCases.stream()
                .map(pair -> Pair.of(pair.getLeft(), GroupAnagrams.getAnagramGroups(pair.getRight())))
                .toList();
        outcome.forEach(pair -> System.out.println(pair.toString()));
        outcome.forEach(pair -> {
            var sortedO = pair.getRight().stream()
                            .map(l -> {
                                Collections.sort(l);
                                return l;
                            }).toList();
            var sameSize = pair.getLeft().size() == pair.getRight().size();
            var answerHasInput = pair.getLeft().containsAll(sortedO);
            var inputHasAnswer = pair.getRight().containsAll(pair.getLeft());
            assertTrue(sameSize &&
                            answerHasInput &&
                    inputHasAnswer);
        });
    }
}
