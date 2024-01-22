package playground.concepts;

public class DLNode<T> {
    private Thunk<DLNode<T>> previous;
    private T content;
    private Thunk<DLNode<T>> next;
    public DLNode(Thunk<DLNode<T>> previous, T value, Thunk<DLNode<T>> next) {
        super();
        this.content = value;
        this.previous = previous;
        this.next = next;
    }
    public T getContent() {
        return content;
    }
    public Thunk<DLNode<T>> getPrevious() {
        return previous;
    }
    public Thunk<DLNode<T>> getNext() {
        return next;
    }
}
