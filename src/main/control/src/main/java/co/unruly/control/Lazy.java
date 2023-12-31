package co.unruly.control;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * Generic lazy container: does not calculate the value until it's required.
 * Will only calculate the value once.
 * @param <T> the type of object we're lazily instantiating
 */
@SuppressWarnings({"unused", "optional", "OptionalUsedAsFieldOrParameterType"})
public final class Lazy<T> {

    private final Supplier<T> source;
    private Optional<T> value = Optional.empty();

    /**
     * @param source .
     */
    public Lazy(Supplier<T> source) {
        this.source = source;
    }

    /**
     * @return value, calculates it if it hasn't been calculated yet
     */
    public synchronized T get() {
        return value.orElseGet(this::calculateAndStore);
    }

    private T calculateAndStore() {
        T calculatedValue = source.get();
        value = Optional.of(calculatedValue);
        return calculatedValue;
    }
}
