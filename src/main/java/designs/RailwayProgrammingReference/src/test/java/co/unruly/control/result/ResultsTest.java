package co.unruly.control.result;

import co.unruly.control.ConsumableFunction;
import co.unruly.control.Lists;
import co.unruly.control.pair.Comprehensions;
import co.unruly.control.pair.Pair;
import org.hamcrest.core.Is;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import static co.unruly.control.matchers.ResultMatchers.isFailureOf;
import static co.unruly.control.matchers.ResultMatchers.isSuccessOf;
import static co.unruly.control.pair.Comprehensions.ifAllSucceeded;
import static co.unruly.control.pair.Maps.entry;
import static co.unruly.control.pair.Maps.mapOf;
import static co.unruly.control.result.Combiners.combineWith;
import static co.unruly.control.result.Introducers.fromMap;
import static co.unruly.control.result.Introducers.tryTo;
import static co.unruly.control.result.Resolvers.*;
import static co.unruly.control.result.Result.failure;
import static co.unruly.control.result.Result.success;
import static co.unruly.control.result.Transformers.*;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class ResultsTest {

    @Test
    public void canCreateSuccess() {
        Result<Integer, String> shouldBeSuccess = success(5);
        Boolean isSuccess = shouldBeSuccess.either(success -> true, failure -> false);
        assertTrue(isSuccess);
    }

    @Test
    public void canCreateFailure() {
        Result<Integer, String> shouldFail = failure("oh poop");
        assertFalse(shouldFail.either(success -> true, failure -> false));
    }

    @Test
    public void canReduceResultToValue() {
        Result<Integer, String> success = success(5);
        Result<Integer, String> failure = failure("i blew up");
        String successString = success.either(
                succ -> String.format("I got %d out of this Result", succ),
                err -> err);
        String failString = failure.either(
                succ -> String.format("I got %d out of this Result", succ),
                err -> err);
        assertEquals(successString, "I got 5 out of this Result");
        assertEquals(failString, "i blew up");
    }

    @Mock
    private Consumer<Integer> successCallback;
    @Mock
    private Consumer<String> failureCallback;
    
    @Test
    public void canDoSideEffectsOnCorrectSideForSuccess() {
        Result<Integer, String> success = success(5);

        success
            .then(onSuccessDo(successCallback))
            .then(onFailureDo(failureCallback));

        verify(successCallback).accept(5);
        verifyNoInteractions(failureCallback);
    }

    @Test
    public void canDoSideEffectsOnCorrectSideForFailure() {

        Result<Integer, String> failure = failure("oops");
        ConsumableFunction<Result<Integer, String>> failureDo = onFailureDo(failureCallback);
        failure
            .then(onSuccessDo(successCallback))
            .then(failureDo);

        verify(failureCallback).accept("oops");
        verifyNoInteractions(successCallback);
    }

    @Test
    public void flatMapsSuccessesIntoAppropriateValues() {
        final Function<Integer, Result<Integer, String>> halve = num ->
            num % 2 == 0 ? success(num / 2) : failure("Cannot halve an odd number into an integer");

        final Result<Integer, String> six = success(6);
        final Result<Integer, String> five = success(5);
        final Result<Integer, String> failure = failure("Cannot parse number");


        assertEquals(six.then(attempt(halve)), isSuccessOf(3));
        assertEquals(five.then(attempt(halve)), isFailureOf("Cannot halve an odd number into an integer"));
        assertEquals(failure.then(attempt(halve)), isFailureOf("Cannot parse number"));
    }

    @Test
    public void canMapSuccesses() {
        final Result<Integer, String> six = success(6);
        final Result<Integer, String> failure = failure("Cannot parse number");

        final Result<Integer, String> twelve = six.then(onSuccess(x -> x * 2));
        final Result<Integer, String> stillFailure = failure.then(onSuccess(x -> x * 2));

        assertEquals(twelve, isSuccessOf(12));
        assertEquals(stillFailure, is(failure));
    }

    @Test
    public void canMapFailures() {
        final Result<Integer, String> six = success(6);
        final Result<Integer, String> failure = failure("Cannot parse number");

        final Result<Integer, String> stillSix = six.then(onFailure(String::toLowerCase));
        final Result<Integer, String> lowerCaseFailure = failure.then(onFailure(String::toLowerCase));

        assertEquals(stillSix, Is.is(success(6)));
        assertEquals(lowerCaseFailure, Is.is(failure("cannot parse number")));
    }

    @Test
    public void canMapOverExceptionThrowingMethods() {
        Result<String, String> six = success("6");
        Result<String, String> notANumber = success("NaN");

        Result<Long, String> parsedSix = six.then(onSuccessTry(Long::parseLong, Exception::toString));
        Result<Long, String> parsedNaN = notANumber.then(onSuccessTry(Long::parseLong, Exception::toString));

        assertEquals(parsedSix, Is.is(success(6L)));
        assertEquals(parsedNaN, Is.is(failure("java.lang.NumberFormatException: For input string: \"NaN\"")));
    }

    @Test
    public void canStreamSuccesses() {
        Stream<Result<Integer, String>> results = Stream.of(success(6), success(5), failure("darnit"));

        Stream<Integer> resultStream = results.flatMap(successes());
        List<Integer> successes = resultStream.collect(toList());

        assertEquals(successes, hasItems(6, 5));
    }

    @Test
    public void canMergeOperationsOnTwoResults() {
        Result<Integer, String> evenSix = success(6);
        Result<Integer, String> evenTwo = success(2);

        Result<Integer, String> oddFive = Result.failure("Five is odd");
        Result<Integer, String> oddSeven = Result.failure("Seven is odd");

        assertEquals(evenSix.then(combineWith(evenTwo)).using((x, y) -> x * y), isSuccessOf(12));
        assertEquals(evenSix.then(combineWith(oddSeven)).using((x, y) -> x * y), isFailureOf("Seven is odd"));
        assertEquals(oddFive.then(combineWith(evenTwo)).using((x, y) -> x * y), isFailureOf("Five is odd"));
        assertEquals(oddFive.then(combineWith(oddSeven)).using((x, y) -> x * y), isFailureOf("Five is odd"));
    }

    @Test
    public void canStreamFailures() {
        Stream<Result<Integer, String>> results = Stream.of(success(6), success(5), failure("darnit"));

        List<String> failures = results.flatMap(failures()).collect(toList());

        assertEquals(failures, hasItems("darnit"));
    }

    @Test
    public void canExtractValuesFromMap() {
        Map<String, Integer> frenchNumberNames = mapOf(entry("un", 1), entry("deux", 2), entry("trois", 3));

        Function<String, Result<Integer, String>> extractor = fromMap(frenchNumberNames, word -> String.format("%s is not a french number", word));

        assertEquals(extractor.apply("deux"), isSuccessOf(2));
        assertEquals(extractor.apply("quattro"), isFailureOf("quattro is not a french number"));
    }

    @Test
    public void canConvertListOfResultsIntoResultOfList() {
        List<Result<Integer, String>> results = asList(success(1), success(42), success(69));
        Result<List<Integer>, List<String>> unwrapped = Lists.successesOrFailures(results);

        assertEquals(unwrapped, isSuccessOf(asList(1, 42, 69)));
    }

    @Test
    public void canConvertListOfResultsIntoFailureOfListOfReasons() {
        List<Result<Integer, String>> results = asList(success(1), failure("cheese"), success(69), failure("hotdog"));
        Result<List<Integer>, List<String>> unwrapped = Lists.successesOrFailures(results);

        assertEquals(unwrapped, isFailureOf(asList("cheese", "hotdog")));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void exampleParseAndHalveNumbers() {
        Stream<String> inputs = Stream.of("6", "5", "NaN");
        Consumer<String> failureCallback = mock(Consumer.class);

        List<Long> halvedNumbers = inputs
            .map(tryTo(Long::parseLong, Exception::toString))
            .map(attempt(x -> x % 2 == 0 ? success(x / 2) : failure(x + " is odd")))
            .peek(onFailureDo(failureCallback))
            .flatMap(successes())
            .collect(toList());

        assertEquals(halvedNumbers, hasItems(3L));

        verify(failureCallback).accept("java.lang.NumberFormatException: For input string: \"NaN\"");
        verify(failureCallback).accept("5 is odd");
        verifyNoMoreInteractions(failureCallback);
    }

    @Test
    public void exampleSplitResults() {
        Stream<String> inputs = Stream.of("6", "5", "NaN");

        Pair<List<Long>, List<String>> halvedNumbers = inputs
                .map(tryTo(Long::parseLong, Exception::toString))
                .map(attempt(x -> x % 2 == 0 ? success(x / 2) : failure(x + " is odd")))
                .collect(split());

        assertEquals(halvedNumbers.left, hasItems(3L));
        assertEquals(halvedNumbers.right, hasItems("java.lang.NumberFormatException: For input string: \"NaN\"", "5 is odd"));
    }

    @Test
    public void shouldAggregateResults_BothSuccessful() {
        Result<String, ?> actualResult = Comprehensions
            .allOf(
                success("Yay!"),
                success(123)
            )
            .then(ifAllSucceeded((x, y) -> x + " = " + y));

        assertEquals(actualResult, isSuccessOf("Yay! = 123"));
    }

    @Test
    public void shouldAggregateResults_OneFailure(){
        Result<String, String> result = Comprehensions.allOf(
            success("Yes!"),
            failure("No!")
        ).then(ifAllSucceeded((x, y) -> x + " = " + y));

        assertEquals(result, isFailureOf("No!"));
    }

    @Test
    public void shouldAggregateResults_AllThreeSuccessful() {
        Result<String, ?> song = Comprehensions.allOf(
            success("bibbidy"),
            success("bobbidy"),
            success("boo")
        ).then(ifAllSucceeded((x, y, z) -> x + " " + y + " " + z));

        assertEquals(song, isSuccessOf("bibbidy bobbidy boo"));
    }

    @Test
    public void shouldAggregateResults_AllThreeFailed() {
        Result<?, String> uhoh = Comprehensions.allOf(
            failure("no"),
            failure("noo"),
            failure("nooo")
        ).then(ifAllSucceeded((x, y, z) -> x + " " + y + " " + z));

        assertEquals(uhoh, isFailureOf("no"));
    }

    @Test
    public void shouldAggregateResults_AllFourPassed() {
        Result<String, ?> result = Comprehensions.allOf(
            success("Yes"),
            success("YesYes"),
            success("YesYesYes"),
            success("YesYesYesYes")
        ).then(ifAllSucceeded((a, b, c, d) -> a + b + c + d));

        assertEquals(result, isSuccessOf("YesYesYesYesYesYesYesYesYesYes"));
    }

    @Test
    public void shouldAggregateResults_AllFourFailures() {
        Result<?, String> result = Comprehensions.allOf(
            failure("NoNo", String.class),
            failure("NoNoNoNo"),
            failure("NoNoNoNo"),
            failure("NoNo") // There's no limit
        ).then(ifAllSucceeded((a, b, c, d) -> a + b + c + d));

        assertEquals(result, isFailureOf("NoNo"));
    }
}
