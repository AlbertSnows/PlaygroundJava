package playground.leetcode;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ReorderList {

    @Contract(value = "_ -> param1", pure = true)
    public static @NotNull List<Integer>
    reorderList(@NotNull LinkedList<Integer> listToReorder) {
        var linkedListAsArray = listToReorder.stream().toList();
        var lastIndex = linkedListAsArray.size();
        var halfSize = lastIndex / 2;
        var firstHalf = linkedListAsArray.subList(0, halfSize);
        var reversedSecondHalf = new ArrayList<>(linkedListAsArray.subList(halfSize, lastIndex));
        Collections.reverse(reversedSecondHalf);
        var index = 0;
        var updatedList = new ArrayList<Integer>();
        while(index < halfSize) {
            var leftValue = firstHalf.get(index);
            var rightValue = reversedSecondHalf.get(index);
            updatedList.add(leftValue);
            updatedList.add(rightValue);
            index++;
        }
        var isOdd = reversedSecondHalf.size() % 2 != 0;
        if(isOdd) {
            updatedList.add(reversedSecondHalf.get(index));
        }
        return updatedList;
    }
}
