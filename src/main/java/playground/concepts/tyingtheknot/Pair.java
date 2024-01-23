package playground.concepts.tyingtheknot;

public record Pair<T>(Thunk<T> left, Thunk<T> right) {
}