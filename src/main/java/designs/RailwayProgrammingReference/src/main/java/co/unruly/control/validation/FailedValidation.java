package co.unruly.control.validation;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record FailedValidation<T, E>(T value, List<E> errors) implements ForwardingList<E> {

    @Contract(pure = true)
    @Override
    public @NotNull String toString() {
        return "FailedValidation{" +
                "value=" + value +
                ", errors=" + errors +
                '}';
    }

    @Override
    public List<E> delegate() {
        return errors;
    }
}
