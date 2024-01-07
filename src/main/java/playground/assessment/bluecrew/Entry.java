package playground.assessment.bluecrew;

import java.util.List;
import java.util.function.BiFunction;

import static playground.assessment.bluecrew.Actions.applyUpdateCommands;
import static playground.assessment.bluecrew.Actions.parseInputs;

/**
 * Timekeeping
 * Create a time keeping data structure
 * Add employees.
 * employees have a check in/out clock mechanism
 * Should be able to query a specific user to see how many hours
 * they have left in their quota for the past week
 */
public class Entry {

    public static void main(String[] args) {
        var input = List.of(
                List.of("add", "jim"),
                List.of("in", "jim", "timestamp"),
                List.of("add", "john"),
                List.of("out", "jim", "timestamp"),
                List.of("remaining", "jim"));
        var commandList = parseInputs(input);
        var initialTimeState = new TimeKeeper();
        TimeKeeper finalTimeState = commandList.stream()
                .reduce(initialTimeState, Actions::applyUpdateCommands, (oldTK, newTK) -> newTK);
        System.out.println(finalTimeState);
    }
}
