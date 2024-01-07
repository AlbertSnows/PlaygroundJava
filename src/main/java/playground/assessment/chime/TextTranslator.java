package playground.assessment.chime;

import designs.utility.stream.CharacterStream;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class TextTranslator {
    public static final Map<Character, Integer> letter_to_digit = Map.ofEntries(
            Map.entry('a', 2),
            Map.entry('b', 2),
            Map.entry('c', 2),
            Map.entry('d', 3),
            Map.entry('e', 3),
            Map.entry('f', 3),
            Map.entry('g', 4),
            Map.entry('h', 4),
            Map.entry('i', 4),
            Map.entry('j', 5),
            Map.entry('k', 5),
            Map.entry('l', 5),
            Map.entry('m', 6),
            Map.entry('n', 6),
            Map.entry('o', 6),
            Map.entry('p', 7),
            Map.entry('q', 7),
            Map.entry('r', 7),
            Map.entry('s', 7),
            Map.entry('t', 8),
            Map.entry('u', 8),
            Map.entry('v', 8),
            Map.entry('w', 9),
            Map.entry('x', 9),
            Map.entry('y', 9),
            Map.entry('z', 9));
    public static final Map<Integer, Set<Character>> digit_to_letter = Map.of(
            2, Set.of('a', 'b', 'c'),
            3, Set.of('d', 'e', 'f'),
            4, Set.of('g', 'h', 'i'),
            5, Set.of('j', 'k', 'l'),
            6, Set.of('m', 'n', 'o'),
            7, Set.of('p', 'q', 'r', 's'),
            8, Set.of('t', 'u', 'v'),
            9, Set.of('w', 'x', 'y', 'z'));

    @Contract(pure = true)
    public static @NotNull String
    convert_code_to_word(String digit_as_string) {
        return "abc";
    }
    private static @NotNull bundle
    find_valid_word_combinations_for_input(int[] input_numbers,
                                           @NotNull HashMap<String, String> valid_words_to_digits,
                                           HashMap<String, String> visited) {
        var valid_word_combinations_of_input = new ArrayList<List<String>>();
        for(String valid_word : valid_words_to_digits.keySet()) {
            var valid_digits = valid_words_to_digits.get((valid_word));
            if(!visited.containsKey(valid_digits)) {
                var digit_length = valid_digits.length();
                var subset_to_check = Arrays.copyOfRange(input_numbers, 0, digit_length);
                var input_subset_as_string = Arrays.stream(subset_to_check).mapToObj(String::valueOf).collect(Collectors.joining());
                var matches_valid_digits = valid_digits.equals(input_subset_as_string);

                // if matches
                if(matches_valid_digits && input_numbers.length == digit_length) {
                    var newList = new ArrayList<>(List.of(valid_word));
                    valid_word_combinations_of_input.add(newList);
                    visited.put(valid_word, valid_digits);
                } else if(matches_valid_digits && digit_length < input_numbers.length) {
                    var remaining_subset = Arrays.copyOfRange(input_numbers, digit_length, input_numbers.length);
                    var subset_bundle =
                            find_valid_word_combinations_for_input(remaining_subset, valid_words_to_digits, visited);
                    var matching_words_for_remaining_subset = subset_bundle.matching_word_sets();
                    visited.putAll(subset_bundle.visited());
                    var matching_words_for_subset = matching_words_for_remaining_subset.stream()
                            .peek(set -> set.add(valid_word))
                            .collect(Collectors.toCollection(ArrayList::new));
                    valid_word_combinations_of_input.addAll(matching_words_for_subset);
                    visited.put(valid_word, valid_digits);
                }
            }
        }
        return new bundle(valid_word_combinations_of_input, visited);
    }

    @Contract(pure = true)
    private static @NotNull String
    convert_word_to_code(@NotNull String validWord) {
        var letters = validWord.toCharArray();
        return CharacterStream.of(letters)
                .map(letter_to_digit::get)
                .map(Object::toString)
                .collect(Collectors.joining());
    }
    public static void main(String[] args) {
        var valid_digit_combinations = getLists();
        List<List<String>> valid_word_combinations = valid_digit_combinations.stream()
                .map(set -> set.stream()
//                        .map(TextTranslator::convert_code_to_word)
                        .toList()
                )
                .toList();
        var output = valid_word_combinations.stream()
                .map((List<String> s) ->  s.toArray(new String[0]))
                .toList()
                .toArray(new String[0][0]);
        System.out.println(Arrays.deepToString(output));
    }

    @NotNull
    private static List<List<String>> getLists() {
        int[] input_numbers = {2, 2, 8, 2, 2, 8, 7,6,6,3,8,4,6,3};
        String[] valid_words = {"bat", "cat", "some", "time", "sometime"};

        var word_to_code = new HashMap<String, String>();
        for(var valid_word : valid_words) {
            var word_as_code = convert_word_to_code(valid_word);
            word_to_code.put(valid_word, word_as_code);
        }
        var valid_words_as_digits = new ArrayList<>(word_to_code.values());
        var output = find_valid_word_combinations_for_input(input_numbers, word_to_code, new HashMap<>());
        return output.matching_word_sets();
    }
}
