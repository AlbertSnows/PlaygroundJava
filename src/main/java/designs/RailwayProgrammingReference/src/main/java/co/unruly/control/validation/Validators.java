package co.unruly.control.validation;


import co.unruly.control.Optionals;
import co.unruly.control.ThrowingLambdas.ThrowingFunction;
import co.unruly.control.result.Result;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static co.unruly.control.result.Result.failure;
import static co.unruly.control.result.Result.success;
import static co.unruly.control.result.Transformers.onFailure;
import static co.unruly.control.result.Transformers.recover;

public interface Validators {

    @Contract(pure = true)
    @SafeVarargs
    static <T, E> @NotNull Validator<T, E> compose(Validator<T, E>... validators) {
        return t -> Arrays.stream(validators).flatMap(v -> v.validate(t));
    }

    static <T, E> @NotNull Validator<T, E> rejectIf(@NotNull Predicate<T> test, E error) {
        return acceptIf(test.negate(), error);
    }

    static <T, E> @NotNull Validator<T, E> rejectIf(@NotNull Predicate<T> test, Function<T, E> errorGenerator) {
        return acceptIf(test.negate(), errorGenerator);
    }

    @Contract(pure = true)
    static <T, E> @NotNull Validator<T, E> acceptIf(Predicate<T> test, E error) {
        return acceptIf(test, t -> error);
    }

    @Contract(pure = true)
    static <T, E> @NotNull Validator<T, E> acceptIf(Predicate<T> test, Function<T, E> errorGenerator) {
        return t -> test.test(t) ? Stream.empty() : Stream.of(errorGenerator.apply(t));
    }

    @Contract(pure = true)
    static <T, E> @NotNull Validator<T, E> firstOf(Validator<T, E> validator) {
        return t -> Optionals.stream(validator.validate(t).findFirst());
    }

    @Contract(pure = true)
    static <T, E> @NotNull Validator<T, E> onlyIf(Predicate<T> test, Validator<T, E> validator) {
        return t -> test.test(t) ? validator.validate(t) : Stream.empty();
    }

    @Contract(pure = true)
    static <T, E, E1> @NotNull Validator<T, E1> mappingErrors(Validator<T, E> validator, BiFunction<T, E, E1> errorMapper) {
        return t -> validator.validate(t).map(e -> errorMapper.apply(t, e));
    }

    @Contract(pure = true)
    static <T, T1, E> @NotNull Validator<T, E> on(Function<T, T1> accessor, Validator<T1, E> innerValidator) {
        return t -> innerValidator.validate(accessor.apply(t));
    }

    @Contract(pure = true)
    static <T, T1, E, X extends Exception> @NotNull Validator<T, E> tryOn(ThrowingFunction<T, T1, X> accessor, Function<Exception, E> onException, Validator<T1, E> innerValidator) {
        return t -> {
            try {
                return innerValidator.validate(accessor.apply(t));
            } catch (Exception e) {
                return Stream.of(onException.apply(e));
            }
        };
    }

    @Contract(pure = true)
    static <T, T1, E> @NotNull Validator<T, E> onEach(Function<T, Iterable<T1>> iterator, Validator<T1, E> innerValidator) {
        return t -> StreamSupport.stream(iterator.apply(t).spliterator(), false).flatMap(innerValidator::validate);
    }

    @Contract(pure = true)
    static <T, E> @NotNull Validator<T, E> tryTo(Validator<T, E> validatorWhichThrowsRuntimeExceptions, Function<RuntimeException, E> errorMapper) {
        return t -> {
            try {
                return validatorWhichThrowsRuntimeExceptions.validate(t);
            } catch (RuntimeException ex) {
                return Stream.of(errorMapper.apply(ex));
            }
        };
    }

    /**
     * @param filterCondition predicate function to filter the failure validation
     * @param <T> success
     * @param <E> errors(?)
     * @return a function that expects a result, and does some filtering
     * based on the failure outcome
     */
    @Contract(pure = true)
    static <T, E> @NotNull Function<Result<T, FailedValidation<T, E>>, Result<T, FailedValidation<T, E>>>
    ignoreWhen(Predicate<E> filterCondition) {
        Function<FailedValidation<T, E>, FailedValidation<T, E>> filteredFailedValidation = fv -> {
            var t = fv.errors().stream()
                    .filter(filterCondition.negate())
                    .collect(Collectors.toList());
            return new FailedValidation<>(fv.value(), t);
        };
        Function<FailedValidation<T, E>, Result<T, FailedValidation<T, E>>> isEmptyResult =
                fv -> fv.errors().isEmpty() ? success(fv.value()) : failure(fv);
        return result -> result.then(onFailure(filteredFailedValidation)).then(recover(isEmptyResult));
    }

}
