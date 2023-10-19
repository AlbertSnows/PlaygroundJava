package playground.core;

import java.util.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        System.out.println("Hello and welcome!");
        // Press Shift+F10 or click the green arrow button in the gutter to run the code.
        int[] A = new int[]{1, 1, 3, 4, 4, 4};
        int[] B = new int[]{1, 2, 2, 2, 5, 5, 5, 8};

        System.out.println("input: " + Arrays.toString(B));

        var frequencies = new HashMap<Integer, Integer>();
        for(var number : B) {
            var isInMap = frequencies.get(number) != null;
            if(isInMap) {
                var occurrences = frequencies.get(number);
                frequencies.put(number, occurrences + 1);
            } else {
                frequencies.put(number, 1);
            }
        }

        var minimumChangesForNumber = new HashMap<Integer,Integer>();
        for(var pair : frequencies.entrySet()) {
            var number = pair.getKey();
            var frequency = pair.getValue();
            var difference = Math.abs(frequency - number);
            var minimum = Math.min(frequency, difference);
            minimumChangesForNumber.put(number, minimum);
        }

        var minimumsPerNumber = new ArrayList<Integer>();
        for(var pair : minimumChangesForNumber.entrySet()) {
            minimumsPerNumber.add(pair.getValue());
        }

        var sum = minimumsPerNumber.stream().reduce(0, Integer::sum);

        // first solve: 8:58
        // final test (for now): 9:01
        // I think it's done!

        // 1 > 2, 3 > 1, 4 > 3
        // 1 > 1, 3 > 2, 4 > 1

        // 3
        // what is the minimum number of moves after which every value X
        // in the array occurs exactly X times?
//        var answer = -1;
        System.out.println("answer: " + sum);
    }
}