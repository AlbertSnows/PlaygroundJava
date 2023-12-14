package co.unruly.control.result;

import co.unruly.control.HigherOrderFunctions;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

import static co.unruly.control.result.Transformers.onSuccess;

/**
 * Some syntax-fu in order to get nice, readable up-casting operations on Results.
 * <p>
 * Usage isn't totally obvious from the implementation: to upcast a success to an Animal, for example, you need:
 * <code>
 *     using(TypeOf.{@literal (<Animal>)}forSuccesses())
 * </code>
 * <p>
 * That'll give you a {@code (Function<Result<Bear, String>, Function<Animal, String>>)} (inferring
 * the types Animal and String from context), which you can then use for mapping a Stream or use in
 * a Result then-operation chain.
 */
@SuppressWarnings("unused")
public interface TypeOf {

    /**
     * Generalises the success type for a Result to an appropriate superclass.
     * @param dummy class for overload differentiation
     * @param <T> success parent type
     * @param <F> failure type
     * @param <S> success child type
     * @return function that upcasts S to T
     */
    @Contract(pure = true)
    static <T, F, S extends T> @NotNull Function<Result<S, F>, Result<T, F>>
    using(ForSuccesses<T> dummy) {
        return result -> result.then(onSuccess(HigherOrderFunctions::upcast));
    }

    /**
     * Generalises the failure type for a Result to an appropriate superclass.
     * @param dummy class for overload differentiation
     * @param <S> success type
     * @param <T> failure parent type
     * @param <F> failure child type
     * @return result with failure upcasted
     */
    @Contract(pure = true)
    static <S, T, F extends T> @NotNull Function<Result<S, F>, Result<S, T>>
    using(ForFailures<T> dummy) {
        return result -> result.then(Transformers.onFailure(HigherOrderFunctions::upcast));
    }

    // we don't use the return value - all this does is provide type context
    @Contract(pure = true)
    static <T> @Nullable ForSuccesses<T> forSuccesses() {
        return null;
    }

    // we don't use the return value - all this does is provide type context
    @Contract(pure = true)
    static <T> @Nullable ForFailures<T> forFailures() {
        return null;
    }

    // this class only exists, so we can differentiate the overloads of using()
    // we don't even instantiate it
    class ForSuccesses<T> { }

    // this class only exists, so we can differentiate the overloads of using()
    // we don't even instantiate it
    class ForFailures<T> { }
}
