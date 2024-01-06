package playground.examples.cardgame;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class Advancers {

    @Contract(pure = true)
    private static @NotNull Player updateScore(@NotNull Player playerWithTurn) {
        var playerDeck = playerWithTurn.playerDeck();
        var card = playerDeck.remove(0);
        var newScore = playerWithTurn.score() + card;
        return new Player(newScore, playerWithTurn.playerDeck());
    }

    public static Boolean shouldAdvance(@NotNull GameState state) {
        return !(state.leftPlayer().playerDeck().isEmpty()
                && state.rightPlayer().playerDeck().isEmpty());
    }
    public static GameState advance(@NotNull GameState readState) {
        Function<GameState, GameState> updateLeft = (GameState state) ->
                new GameState("right", updateScore(state.leftPlayer()), state.rightPlayer());
        Function<GameState, GameState> updateRight = (GameState state) ->
                new GameState("left", state.leftPlayer(), updateScore(state.leftPlayer()));

        return Map.of(
                "left", updateLeft,
                "right", updateRight
        ).get(readState.state()).apply(readState);
    }
}
