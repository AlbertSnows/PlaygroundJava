package playground.examples.cardgame;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static playground.examples.cardgame.Advancers.advance;
import static playground.examples.cardgame.Advancers.shouldAdvance;

public class CardGame {
    @Contract(pure = true)
    private static @NotNull List<Integer> generateNewDeck() {
        //todo: bonus: switch to a 10 point system
        var deck = new java.util.ArrayList<>(IntStream.rangeClosed(0, 52).boxed().toList());
        Collections.shuffle(deck);
        return deck;
    }
    public static @NotNull GameState init() {
        var deck = generateNewDeck();
        var playerOne = new Player(0, deck.subList(0, 26));
        var playerTwo = new Player(0, deck.subList(26, 52));
        return new GameState("left", playerOne, playerTwo);
    }
    public static GameState playUntilFinished(GameState state) {
        while(shouldAdvance(state)) {
            state = advance(state);
        }
        return state;
    }
}
