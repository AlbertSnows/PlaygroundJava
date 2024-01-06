package designs.utility.stream;

import org.jetbrains.annotations.NotNull;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CharacterStream {

    public static Stream<Character> of(char @NotNull [] arr) {
        return IntStream.range(0, arr.length).mapToObj(i -> arr[i]);
    }
}
