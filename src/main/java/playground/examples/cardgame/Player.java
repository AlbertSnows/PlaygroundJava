package playground.examples.cardgame;

import java.util.List;

public record Player(Integer score, List<Integer> playerDeck) {
}
