package playground.assessment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MaximumSum {
    public static void MaximumSum() {
        int[] A = new int[]
//                {30, 909, 3190, 99, 3990, 9009};
//                {50, 222, 49, 52, 25};
//                {130, 191, 200, 10};
                {405, 45, 300, 300};
        var asStrings = new ArrayList<String>();
        for(var number : A) {
            asStrings.add(Integer.toString(number));
        }
        var FLMap = new HashMap<String, ArrayList<Integer>>();
        for(var numString : asStrings) {
            var asArray = numString.toCharArray();
            var first = asArray[0];
            var last = asArray[asArray.length -1];
            var key = Character.toString(first) + Character.toString(last);
            var keyExists = FLMap.containsKey(key);
            if(keyExists) {
                var list = FLMap.get(key);
                list.add(Integer.parseInt(numString));
            } else {
                var list = new ArrayList<Integer>();
                list.add(Integer.parseInt(numString));
                FLMap.put(key, list);
            }
        }

        var largestForFLNumber = new HashMap<String, Integer>();
        for(var pair : FLMap.entrySet()) {
            var list = pair.getValue();
            var size = list.size();
            var tooSmall = size < 2;
            if(tooSmall) {
                // set sum to -1
                largestForFLNumber.put(pair.getKey(), -1);
            } else {
                Collections.sort(list);
                var largest = list.get(size - 1);
                var secondLargest = list.get(size - 2);
                var largestSumForDigitType = largest + secondLargest;
                largestForFLNumber.put(pair.getKey(), largestSumForDigitType);
            }
        }

        var possibleSums = new ArrayList<Integer>();
        for(var pair : largestForFLNumber.entrySet()) {
            possibleSums.add(pair.getValue());
        }



        // quickly find first and last digit
        // associate that with the number
        // find largest sum
        // iterate, get first and last
        // {45: [405, 45, 4005]}
        // sort it, add last two


        var answer = Collections.max(possibleSums);
        System.out.println("answer: " + answer);
    }
}
