package co.unruly.control.pair;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

@SuppressWarnings("unused")
public record Triple<A, B, C>(A first, B second, C third) {

    @FunctionalInterface
    public interface TriFunction<A, B, C, R> {
        /**
         * @param a .
         * @param b .
         * @param c .
         * @return R
         */
        R apply(A a, B b, C c);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    public static <A, B, C> @NotNull Triple<A, B, C> of(A first, B second, C third) {
        return new Triple<>(first, second, third);
    }

    public <T> T then(@NotNull Function<Triple<A, B, C>, T> function) {
        return function.apply(this);
    }

    public <T> T then(@NotNull TriFunction<A, B, C, T> function) {
        return function.apply(first, second, third);
    }

    @Override
    public String toString() {
        return "Triple{" +
                "first=" + first +
                ", second=" + second +
                ", third=" + third +
                '}';
    }
}
