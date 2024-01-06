package playground.examples.cardgame;

// this is a two player card game
// the game starts with a deck of cards
// the cards are dealt out to both players
// on each turn:
// both players turn over their top-most card
// the player with the higher valued card takes the cards and puts them in their scoring pile (scoring 1 point per card)
// this continues until the players have no cards left
// the player with the highest score wins

public class Core {
    public static void main(String[] args) {
        var initialState = CardGame.init();
        var finalState  = CardGame.playUntilFinished(initialState);
        System.out.println("game finished");
        System.out.println(finalState);
    }
}
