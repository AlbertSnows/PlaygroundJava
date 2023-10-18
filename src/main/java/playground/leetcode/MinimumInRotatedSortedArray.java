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
        while(leftIndex < rightIndex) {
            var leftValue = rotatedSortedList.get(leftIndex);
            var rightValue = rotatedSortedList.get(rightIndex);
            var onLeftSide = leftValue < rightValue;
            var onRightSide = rightValue < leftValue;
            var diff = (rightIndex - leftIndex) / 2;
            if(onLeftSide) {
                rightIndex = rightIndex - diff;
            } else if(onRightSide) {
                leftIndex = leftIndex + diff;
            } else {
                break;
            }
         }
        return rotatedSortedList.get(leftIndex);
    }
}
