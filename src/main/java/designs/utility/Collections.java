package designs.utility;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class Collections {

    @Contract(pure = true)
    public static @NotNull IntStream range(Integer start, Integer end) {
        return IntStream.rangeClosed(start, end);
    }
}
