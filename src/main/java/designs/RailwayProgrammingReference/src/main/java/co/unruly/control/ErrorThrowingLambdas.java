package co.unruly.control;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * A collection of functional interfaces which throw, and convenience functions to wrap them
 * so any thrown Throwables are converted to RuntimeExceptions so they can be used where
 * non-throwing functional interfaces are required
 * <p>
 * Catching errors in the general case is not recommended, but there are specific errors
 * which are contextually reasonable to catch. Therefore, this wider capability exists
 * separately and should be used judiciously.
 */
@SuppressWarnings("unused")
public interface ErrorThrowingLambdas {

    /**
     * A Function which may throw a checked exception
     */
    @FunctionalInterface
    interface ThrowingFunction<I, O, X extends Throwable> {
        /**
         * @param input input variable
         * @return output of type O
         * @throws X the exception type
         */
        O apply(I input) throws X;

        default <T> ThrowingFunction<I, T, X> andThen(Function<O, T> nextFunction) {
            return x -> nextFunction.apply(apply(x));
        }

        /**
         * @param nextFunction next function to
         * @param <T> input type
         * @return a function that can throw a runtime exception
         * when provided with an input X, it will pass X into nextFunction, and then
         * call the throwing function on the result
         */
        default <T> ThrowingFunction<T, O, X>
        compose(Function<T, I> nextFunction) {
            return x -> apply(nextFunction.apply(x));
        }

        /**
         * Converts the provided function into a regular Function, where any thrown exceptions are
         * wrapped in a RuntimeException.
         * @param f function that throws
         * @param <I> input type
         * @param <O> output type
         * @param <X> exception type
         * @return a function that expects an input X. X will be applied to f, and the result is
         * wrapped in a try-catch block
         */
        @Contract(pure = true)
        static <I, O, X extends Throwable> @NotNull Function<I, O>
        throwingRuntime(ThrowingFunction<I, O, X> f) {
            return x -> {
                try {
                    return f.apply(x);
                } catch (Throwable ex) {
                    throw new RuntimeException(ex);
                }
            };
        }
    }

    /**
     * A Consumer which may throw a checked exception
     */
    @FunctionalInterface
    interface ThrowingConsumer<T, X extends Throwable> {
        void accept(T item) throws X;

        /**
         * Converts the provided consumer into a regular Consumer, where any thrown exceptions are
         * wrapped in a RuntimeException.
         * @param p consumer that throws
         * @param <T> input type
         * @param <X> exception type
         * @return a function that expects an input X. X is passed to p, and the
         * result is wrapped in a try-catch block
         */
        @Contract(pure = true)
        static <T, X extends Throwable> @NotNull Consumer<T>
        throwingRuntime(ThrowingConsumer<T, X> p) {
            return x -> {
                try {
                    p.accept(x);
                } catch (Throwable ex) {
                    throw new RuntimeException(ex);
                }
            };
        }
    }

    /**
     * A BiFunction which may throw a checked exception
     */
    @FunctionalInterface
    interface ThrowingBiFunction<A, B, R, X extends Throwable> {
        R apply(A first, B second) throws X;

        /**
         * Converts the provided bifunction into a regular BiFunction, where any thrown exceptions
         * are wrapped in a RuntimeException
         */
        @Contract(pure = true)
        static <A, B, R, X extends Throwable> @NotNull BiFunction<A, B, R>
        throwingRuntime(ThrowingBiFunction<A, B, R, X> f) {
            return (a, b) -> {
                try {
                    return f.apply(a, b);
                } catch (Throwable ex) {
                    throw new RuntimeException(ex);
                }
            };
        }
    }

    /**
     * A Predicate which may throw a checked exception
     */
    @FunctionalInterface
    interface ThrowingPredicate<T, X extends Throwable> {
        boolean test(T item) throws X;


        /**
         * Converts the provided predicate into a regular Predicate, where any thrown exceptions
         * are wrapped in a RuntimeException
         * @param p predicate function that throws
         * @param <T> input type
         * @param <X> exception type
         * @return function that takes an input, tests it against p, and throws a contained try/catch
         * exception depending on the result
         */
        @Contract(pure = true)
        static <T, X extends Throwable> @NotNull Predicate<T>
        throwingRuntime(ThrowingPredicate<T, X> p) {
            return x -> {
                try {
                    return p.test(x);
                } catch (Throwable ex) {
                    throw new RuntimeException(ex);
                }
            };
        }
    }
}
