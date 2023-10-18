package playground.leetcode;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class ValidParenthesis {
    @Contract(pure = true)
    public static @NotNull Boolean
    validateParenthesis(@NotNull String parenthesisList) {
        var parenthesisChars = parenthesisList.toCharArray();
        var openingParenthesis = Set.of('(', '{', '[');
        var closingRelationships = Map.of(
                ')', '(',
                '}', '{',
                ']', '[');
        var parenthesisStack = new Stack<>();
        var validSoFar = true;
        for(var parenthesis : parenthesisChars) {
            var isOpener = openingParenthesis.contains(parenthesis);
            if(isOpener) {
                parenthesisStack.add(parenthesis);
            } else {
                var expectedOpener = closingRelationships.get(parenthesis);
                var actualOpener = parenthesisStack.peek();
                var matchesExpected = expectedOpener == actualOpener;
                if(matchesExpected) {
                    parenthesisStack.pop();
                } else {
                    validSoFar = false;
                    break;
                }
            }
        }
        return validSoFar;
    }
}
