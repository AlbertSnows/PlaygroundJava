//Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.
//
//        An input string is valid if:
//
//        Open brackets must be closed by the same type of brackets.
//        Open brackets must be closed in the correct order.
//        Every close bracket has a corresponding open bracket of the same type.
//
//
//
//        Example 1:
//
//        Input: s = "()"
//        Output: true
//
//        Example 2:
//
//        Input: s = "()[]{}"
//        Output: true
//
//        Example 3:
//
//        Input: s = "(]"
//        Output: false
//
//
//
//        Constraints:
//
//        1 <= s.length <= 104
//        s consists of parentheses only '()[]{}'.

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
