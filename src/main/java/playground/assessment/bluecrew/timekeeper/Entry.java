package playground.assessment.bluecrew.timekeeper;

import java.util.HashMap;
import java.util.List;

import static playground.assessment.bluecrew.timekeeper.Actions.parseInputs;

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
                List.of("in", "jim", "2022-12-31T08:45:30+02:00"),
                List.of("add", "john"),
                List.of("out", "jim", "2023-09-15T08:45:30+02:00"),
                List.of("in", "jim", "2024-01-06T14:30:00Z"),
                List.of("remaining", "jim"),
                List.of("out", "jim", "2024-07-15T10:30:45.123Z"));
        var commandList = parseInputs(input);
        var initialTimeState = new TimeKeeper(new HashMap<>());
        TimeKeeper finalTimeState = commandList.stream()
                .reduce(initialTimeState, Actions::applyUpdateCommands, (oldTK, newTK) -> {
                    newTK.users().putAll(oldTK.users());
                    return newTK;
                });
        System.out.println(finalTimeState);
    }
}
