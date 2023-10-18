package playground.leetcode;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MinimumInRotatedSortedArray {
    @Contract(pure = true)
    public static Integer
    findMinimumInRotatedSortedArrayTest(@NotNull List<Integer> rotatedSortedList) {
        var leftIndex = 0;
        var rightIndex = rotatedSortedList.size() - 1;
        var found = false;
        while(leftIndex < rightIndex && !found) {
            var leftValue = rotatedSortedList.get(leftIndex);
            var rightValue = rotatedSortedList.get(rightIndex);
            var onLeftSide = leftValue < rightValue;
            var onRightSide = rightValue < leftValue;
            var diff = (rightIndex - leftIndex) / 2;
            if(onLeftSide) {
                rightIndex = (rightIndex - diff) - 1;
            } else if(onRightSide) {
                leftIndex = (leftIndex + diff) + 1;
            } else {
                found = true;
            }
         }
        return rotatedSortedList.get(leftIndex);
    }
}
