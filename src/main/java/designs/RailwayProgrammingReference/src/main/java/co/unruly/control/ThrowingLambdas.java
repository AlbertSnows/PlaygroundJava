package co.unruly.control;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * A collection of functional interfaces which throw, and convenience functions to wrap them
 * so any thrown exceptions are converted to RuntimeExceptions so they can be used where
 * non-throwing functional interfaces are required
 */
@SuppressWarnings("ALL")
public interface ThrowingLambdas {

    /**
     * A Function which may throw a checked exception
     */
    @SuppressWarnings("unused")
    @FunctionalInterface
    interface ThrowingFunction<I, O, X extends Exception> {
        /**
         * @param input
         * @return
         * @throws X
         */
        O apply(I input) throws X;

        /**
         * @param nextFunction
         * @param <T>
         * @return
         */
        default <T> ThrowingFunction<I, T, X> andThen(Function<O, T> nextFunction) {
            return x -> nextFunction.apply(apply(x));
        }

        /**
         * @param nextFunction
         * @param <T>
         * @return
         */
        default <T> ThrowingFunction<T, O, X> compose(Function<T, I> nextFunction) {
            return x -> apply(nextFunction.apply(x));
        }

        /**
         * Converts the provided function into a regular Function, where any thrown exceptions are
         * wrapped in a RuntimeException.
         * @param f
         * @param <I>
         * @param <O>
         * @param <X>
         * @return
         */
        @Contract(pure = true)
        static <I, O, X extends Exception> @NotNull Function<I, O>
        throwingRuntime(ThrowingFunction<I, O, X> f) {
            return x -> {
                try {
                    return f.apply(x);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            };
        }
    }

    /**
     * A Consumer which may throw a checked exception
     */
    @SuppressWarnings("unused")
    @FunctionalInterface
    interface ThrowingConsumer<T, X extends Exception> {
        /**
         * @param item
         * @throws X
         */
        void accept(T item) throws X;

        /**
         * Converts the provided consumer into a regular Consumer, where any thrown exceptions are
         * wrapped in a RuntimeException.
         * @param p
         * @param <T>
         * @param <X>
         * @return
         */
        @Contract(pure = true)
        static <T, X extends Exception> @NotNull Consumer<T>
        throwingRuntime(ThrowingConsumer<T, X> p) {
            return x -> {
                try {
                    p.accept(x);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            };
        }
    }

    /**
     * A BiFunction which may throw a checked exception
     */
    @SuppressWarnings("unused")
    @FunctionalInterface
    interface ThrowingBiFunction<A, B, R, X extends Exception> {
        R apply(A first, B second) throws X;

        /**
         * Converts the provided bifunction into a regular BiFunction, where any thrown exceptions
         * are wrapped in a RuntimeException
         * @param f
         * @param <A>
         * @param <B>
         * @param <R>
         * @param <X>
         * @return
         */
        @Contract(pure = true)
        static <A, B, R, X extends Exception> @NotNull BiFunction<A, B, R>
        throwingRuntime(ThrowingBiFunction<A, B, R, X> f) {
            return (a, b) -> {
                try {
                    return f.apply(a, b);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            };
        }
    }

    /**
     * A Predicate which may throw a checked exception
     * @param <T>
     * @param <X>
     */
    @FunctionalInterface
    interface ThrowingPredicate<T, X extends Exception> {
        /**
         * @param item
         * @return
         * @throws X
         */
        boolean test(T item) throws X;

        /**
         * Converts the provided predicate into a regular Predicate, where any thrown exceptions
         * are wrapped in a RuntimeException
         * @param p
         * @param <T>
         * @param <X>
         * @return
         */
        @Contract(pure = true)
        static <T, X extends Exception> @NotNull Predicate<T>
        throwingRuntime(ThrowingPredicate<T, X> p) {
            return x -> {
                try {
                    return p.test(x);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            };
        }
    }

    /**
     * @param consumer
     * @param <T>
     * @param <X>
     * @return
     */
    @Contract(pure = true)
    static <T, X extends Exception> @NotNull Predicate<T>
    throwsWhen(ThrowingConsumer<T, X> consumer) {
        return t -> {
            try {
                consumer.accept(t);
                return false;
            } catch (Exception ex) {
                return true;
            }
        };
    }

    /**
     * @param consumer
     * @param <T>
     * @param <X>
     * @return
     */
    @Contract(pure = true)
    static <T, X extends Exception> @NotNull Predicate<T>
    doesntThrow(ThrowingConsumer<T, X> consumer) {
        return t -> {
            try {
                consumer.accept(t);
                return true;
            } catch (Exception ex) {
                return false;
            }
        };
    }
}
