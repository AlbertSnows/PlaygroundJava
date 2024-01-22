package playground.concepts;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.LinkedList;

public class TyingTheKnot {
    @Contract("_, _, _ -> new")
    public static <T> @NotNull Pair<DLNode<T>>
    go(Thunk<DLNode<T>> prev, @NotNull LinkedList<T> linkedList, Thunk<DLNode<T>> next) {
        if (linkedList.isEmpty()) {
            return new Pair<>(next, prev);
        } else {
            T nextValue = linkedList.pop();
            Thunk<DLNode<T>> thisNode = new Thunk<>();
            Pair<DLNode<T>> pair = go(thisNode, linkedList, next);
            thisNode.setValue(new DLNode<>(nextValue, prev, pair.first()));
            return new Pair<>(thisNode, pair.second());
        }
    }
    public static <T> @NotNull Thunk<DLNode<T>>
    mkDList(@NotNull LinkedList<T> linkedList) {
        if (linkedList.isEmpty()) {
            throw new RuntimeException("must have at least one element!");
        }
        Thunk<DLNode<T>> first = new Thunk<>();
        Thunk<DLNode<T>> last = new Thunk<>();
        Pair<DLNode<T>> pair = go(last,linkedList,first);
        // Is this the proper way of representing the switch?
        first.setValue(pair.getFst().getValue());
        last.setValue(pair.getSnd().getValue());
        return first;
    }
    public static void main(String[] args) {
        LinkedList<Integer> integerLinkedList = new LinkedList<>(Arrays.asList(1, 2, 3));
        Thunk<DLNode<Integer>> result = mkDList(integerLinkedList);

        while(result.getValue().getNext() != null) {
            System.out.println(result.getValue().getNodeValue());
            result = result.getValue().getNext();

        }
    }
}
