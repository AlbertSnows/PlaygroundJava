package playground.leetcode;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GroupAnagrams {
    public static @NotNull @Unmodifiable List<List<String>> getAnagramGroups(@NotNull List<String> words) {
        Function<String, String> sortWord = word -> word.chars()
                .sorted()
                .collect(StringBuilder::new,
                        StringBuilder::appendCodePoint,
                        StringBuilder::append)
                .toString();
        Function<String, Pair<String, String>> wordWithSorted = word -> Pair.of(sortWord.apply(word), word);
        var wordsWithSorted = words.stream().map(wordWithSorted).toList();
        var wordFrequencies = wordsWithSorted.stream().reduce(new HashMap<String, List<String>>(), (accumulator, pair) -> {
            var sortedWord = pair.getLeft();
            var list = accumulator.get(sortedWord);
            var listExists = list != null;
            if(listExists) {
                list.add(pair.getRight());
                accumulator.put(sortedWord, list);
            } else {
                var newList = new ArrayList<String>();
                newList.add(pair.getRight());
                accumulator.put(sortedWord, newList);
            }
            return accumulator;
        }, (accumulator, streamOutcome) -> {
            accumulator.putAll(streamOutcome);
            return accumulator;
        });
//        var outcome = wordFrequencies.values().stream();
        return new ArrayList<>(wordFrequencies.values());
    }
}
