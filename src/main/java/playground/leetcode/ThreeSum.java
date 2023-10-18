//Given an integer array nums, return all the triplets [nums[i], nums[j], nums[k]] such that i != j, i != k, and j != k, and nums[i] + nums[j] + nums[k] == 0.
//
//        Notice that the solution set must not contain duplicate triplets.
//
//
//
//        Example 1:
//
//        Input: nums = [-1,0,1,2,-1,-4]
//        Output: [[-1,-1,2],[-1,0,1]]
//        Explanation:
//        nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0.
//        nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0.
//        nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0.
//        The distinct triplets are [-1,0,1] and [-1,-1,2].
//        Notice that the order of the output and the order of the triplets does not matter.
//
//        Example 2:
//
//        Input: nums = [0,1,1]
//        Output: []
//        Explanation: The only possible triplet does not sum up to 0.
//
//        Example 3:
//
//        Input: nums = [0,0,0]
//        Output: [[0,0,0]]
//        Explanation: The only possible triplet sums up to 0.
//
//
//
//        Constraints:
//
//        3 <= nums.length <= 3000
//        -105 <= nums[i] <= 105

package playground.leetcode;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ThreeSum {
    public static @NotNull @Unmodifiable ArrayList<ArrayList<Integer>> findThreeSum(List<Integer> numbers) {
        var sortedNums = new ArrayList<>(numbers);
        Collections.sort(sortedNums);
        var indexes = IntStream.rangeClosed(0, sortedNums.size() -1)
                .boxed().toList();
        var uniqueFirstPivotIndexes = indexes.stream()
                .filter(index -> {
                    var valueAtCurrentIndex = sortedNums.get(index);
                    var atEnd = index == sortedNums.size() - 1;
                    if(atEnd) {
                        return true;
                    } else {
                        var valueAtNextIndex = sortedNums.get(index + 1);
                        return !valueAtCurrentIndex.equals(valueAtNextIndex);
                    }
                });
        Function<Integer, Function<ArrayList<Integer>, ArrayList<Integer>>> addToList = uniqueIndex -> twoSum -> {
            twoSum.add(uniqueIndex);
            return twoSum;
        };
        var twoSumsForEachFirstPivot = uniqueFirstPivotIndexes
                .map(uniqueIndex -> Pair.of(uniqueIndex, findTwoSum(
                        sortedNums.get(uniqueIndex),
                        sortedNums.subList(uniqueIndex, sortedNums.size()))));
        return twoSumsForEachFirstPivot
                .reduce(new ArrayList<>(), (threeSumsAcc, pair) -> {
                    var uniqueIndex = pair.getLeft();
                    var twoSums = pair.getRight();
                    var updatedSums = twoSums.stream().map(addToList.apply(uniqueIndex))
                            .collect(Collectors.toCollection(ArrayList::new));
                    threeSumsAcc.addAll(updatedSums);
                    return threeSumsAcc;
                }, (l, r) -> { l.addAll(r); return l; });
    }

    @Contract("_, _ -> new")
    private static @NotNull ArrayList<ArrayList<Integer>>
    findTwoSum(Integer target, @NotNull List<Integer> sortedNumbers) {
        // -1 -1 -1 0 0 1 1 1
        var leftIndex = 0;
        var rightIndex = sortedNumbers.size() - 1;
        var matches = new ArrayList<ArrayList<Integer>>();
        while(leftIndex < rightIndex) {
            var leftNum = sortedNumbers.get(leftIndex);
            var rightNum = sortedNumbers.get(rightIndex);
            var sum = leftNum + rightNum;
            var belowTarget = sum < target;
            var aboveTarget = sum > target;
            if(belowTarget) {
                leftIndex++;
            } else if(aboveTarget) {
                rightIndex--;
            } else {
                var set = new ArrayList<Integer>();
                set.add(leftNum);
                set.add(rightNum);
                matches.add(set);
                leftIndex++;
            }
        }

        return matches;
    }
}
