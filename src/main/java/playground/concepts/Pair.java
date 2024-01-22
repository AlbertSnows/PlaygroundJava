package playground.concepts;

public record Pair<T>(Thunk<T> left, Thunk<T> right) {
}