package playground.assessment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MinimumChanges {

    // for a number in input N, it calculates the frequency F of that number in input
    // e.g. [1, 1, 3] => {1: 2, 3: 1}
    public static @NotNull HashMap<Integer, Integer>
    getFrequencies(int @NotNull [] input) {
        var frequencies = new HashMap<Integer, Integer>();
        for(var number : input) {
            var isInMap = frequencies.get(number) != null;
            if(isInMap) {
                var occurrences = frequencies.get(number);
                frequencies.put(number, occurrences + 1);
            } else {
                frequencies.put(number, 1);
            }
        }
        return frequencies;
    }

    // bonus: add header
    public static Integer
    calcMinimumChanges(int @NotNull [] input) {
        var frequencies = getFrequencies(input);
        var minimumChangesForNumber = calculateMinimumChangesForNumber(frequencies);
        // there is probably a better way to do this, but it's simple enough!
        var minimumsPerNumber = new ArrayList<Integer>();
        for(var pair : minimumChangesForNumber.entrySet()) {
            minimumsPerNumber.add(pair.getValue());
        }
        return minimumsPerNumber.stream().reduce(0, Integer::sum);
    }

    /* For a given number N, we have the frequency F. Now, to satisfy the question
    we can do one of two things.
    1) Remove occuurances of N entirely
    2) Add (or remove) until N = F
    For each number, we MUST do either 1 or 2. So because of the way the question
    is phrased, the minimum between 1 or 2 is our answer for that number. Every N
    is independent of one another, so we know that the minimum for a given N will
    not be contingent on its unique neighbors.
     */
    @NotNull
    private static HashMap<Integer, Integer>
    calculateMinimumChangesForNumber(@NotNull HashMap<Integer, Integer> frequencies) {
        var minimumChangesForNumber = new HashMap<Integer,Integer>();
        for(var pair : frequencies.entrySet()) {
            var number = pair.getKey();
            var frequency = pair.getValue();
            var difference = Math.abs(frequency - number);
            var minimum = Math.min(frequency, difference);
            minimumChangesForNumber.put(number, minimum);
        }
        return minimumChangesForNumber;
    }
}
