package playground.leetcode;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;

public class LongestSubStringWithoutRepeatingChars {
    @Contract(pure = true)
    public static @NotNull Integer
    findLongestSubStringWithoutRepeatingCharsTest(@NotNull String letters) {
        // abcabcbb
        var lettersArray = letters.toCharArray();
        var leftIndex = 0;
        var rightIndex = 0;
        var size = letters.length();
        var currentSet = new HashSet<>();
        var max = 0;
        while(rightIndex < size) {
            var nextLetter = lettersArray[rightIndex];
            var oldestLetter = lettersArray[leftIndex];
            var isInSet = currentSet.contains(nextLetter);
            var isOldLetter = nextLetter == oldestLetter;
            if(isInSet) {
                max = Math.max(max, rightIndex - leftIndex);
                leftIndex++;
                if (isOldLetter) {
                    rightIndex++;
                } else {
                    currentSet.remove(oldestLetter);
                }
            } else {
                currentSet.add(nextLetter);
                rightIndex++;
            }
        }
        return max;
    }
}
