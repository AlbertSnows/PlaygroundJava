package playground.assessment.bluecrew;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Actions {
    public enum COMMAND { ADD, IN, OUT, REMAINING }
    public static final Map<String, COMMAND> COMMANDS_MAP = Map.of(
            "add", COMMAND.ADD,
            "in", COMMAND.IN,
            "out", COMMAND.OUT,
            "remaining", COMMAND.REMAINING);
    public static Action parseInput(@NotNull List<String> input) {
        var command = COMMANDS_MAP.get(input.get(0));
        return Map.of(
                COMMAND.ADD, new Action(command, Map.of("name", input.get(1))),
                COMMAND.IN, new Action(command, Map.of(
                        "name", input.get(1),
                        "timestamp", Instant.parse(input.get(2)).getEpochSecond())),
                COMMAND.OUT, new Action(command, Map.of(
                        "name", input.get(1),
                        "timestamp", Instant.parse(input.get(2)).getEpochSecond())),
                COMMAND.REMAINING, new Action(command, Map.of("name", input.get(1)))).get(command);

    }
    public static List<Action> parseInputs(@NotNull List<List<String>> input) {
        return input.stream().map(Actions::parseInput).collect(Collectors.toList());
    }

    @Contract(pure = true)
    public static @NotNull Function<Map<String, Object>, TimeKeeper>
    addUser(TimeKeeper tk)
    { return (Map<String, Object> data) -> { return tk;}; }
    @Contract(pure = true)
    public static @NotNull Function<Map<String, Object>, TimeKeeper>
    clockIn(TimeKeeper tk)
    { return (Map<String, Object> data) -> { return tk;}; }
    @Contract(pure = true)
    public static @NotNull Function<Map<String, Object>, TimeKeeper>
    clockOut(TimeKeeper tk)
    { return (Map<String, Object> data) -> { return tk;}; }
    @Contract(pure = true)
    public static @NotNull Function<Map<String, Object>, TimeKeeper>
    printRemaining(TimeKeeper tk)
    { return (Map<String, Object> data) -> { return tk;}; }
    public static TimeKeeper
    applyUpdateCommands(@NotNull TimeKeeper tk, @NotNull Action command) {
        return Map.
                <COMMAND, Function<TimeKeeper, Function<Map<String, Object>, TimeKeeper>>>
                of(COMMAND.ADD, Actions::addUser,
                COMMAND.IN, Actions::clockIn,
                COMMAND.OUT, Actions::clockOut,
                COMMAND.REMAINING, Actions::printRemaining)
                .get(command.state())
                .apply(tk).apply(command.data());

    }
}