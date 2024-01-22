package playground.concepts;

public class DLNode<T> {
    private T nodeValue;
    private Thunk<DLNode<T>> previous;
    private Thunk<DLNode<T>> next;
    public DLNode(T value, Thunk<DLNode<T>> previous, Thunk<DLNode<T>> next) {
        super();
        this.nodeValue = value;
        this.previous = previous;
        this.next = next;
    }
    public T getNodeValue() {
        return nodeValue;
    }
    public Thunk<DLNode<T>> getPrevious() {
        return previous;
    }
    public Thunk<DLNode<T>> getNext() {
        return next;
    }
}
