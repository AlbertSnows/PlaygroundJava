package playground.concepts;

public record Pair<T>(Thunk<T> first, Thunk<T> second) {
}