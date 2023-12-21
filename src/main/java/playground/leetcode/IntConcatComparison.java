package playground.leetcode;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class IntConcatComparison {
    public static void intconcat() {
        int[] a = {1, 2, 3};
        int[] b = {1, 2, 3};
        var k = 32;
        var range = IntStream.rangeClosed(0, a.length - 1);
        var concatNums = range.mapToObj(i -> {
            var leftValue = a[i];
            var rightValue = b[(a.length - 1) - i];
            return "" + leftValue + rightValue;
        }).map(Integer::parseInt);
        var higherConcatNums = concatNums.filter(v -> v < k);
        var answer = higherConcatNums.count();
        System.out.println(answer);

    }

    public static void otherIntConcat() {
        int[] a = {1000000, 1000000, 1000000, 1000000};
        var range = IntStream.rangeClosed(0, a.length - 1);
        var list = new ArrayList<Long>();
        for (int j : a) {
            for (int k : a) {
                var outerInnerConcat = Long.parseLong("" + j + k);
                list.add(outerInnerConcat);
            }
        }
        System.out.println();
    }
}
