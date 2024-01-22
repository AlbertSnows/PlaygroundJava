package playground.concepts;

/**
 * a thunk is a subroutine used to inject a calculation into another subroutine.
 * Thunks are primarily used to delay a calculation until its result is needed,
 * or to insert operations at the beginning or end of the other subroutine.
 * ...soooooooooo, lazy eval?
 * this is just a wrapper though!
 * ohhh, but the wrapper can pretend to be something...?
 * @param <T>
 */
public class Thunk<T> {
    private T value;
    public Thunk() {
        value = null;
    }
    public T getValue() {
        if (value==null) {
            throw new RuntimeException("should never happen!");
        }
        return value;
    }
    public void setValue(T value) {
        this.value = value;
    }
}