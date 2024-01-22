package playground.concepts;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.LinkedList;

public class TyingTheKnot {
    @Contract("_, _, _ -> new")
    public static <T> @NotNull Pair<DLNode<T>>
    tieRecursiveList(Thunk<DLNode<T>> lastNodeHolder, @NotNull LinkedList<T> listOfContent, Thunk<DLNode<T>> firstNodeHolder) {
        T firstValue = listOfContent.pop();
        Thunk<DLNode<T>> firstNode = new Thunk<>();
        // build it backwards?
        Pair<DLNode<T>> secondNodeWithLastNode = buildNodeLayer(firstNode, listOfContent, firstNodeHolder);
        firstNode.hold(new DLNode<>(lastNodeHolder, firstValue, secondNodeWithLastNode.left()));
        return new Pair<>(firstNode, secondNodeWithLastNode.right());
    }
    @Contract("_, _, _ -> new")
    public static <T> @NotNull Pair<DLNode<T>>
    buildNodeLayer(Thunk<DLNode<T>> previousNode, @NotNull LinkedList<T> linkedList, Thunk<DLNode<T>> nextNode) {
        if (linkedList.isEmpty()) {
            return new Pair<>(nextNode, previousNode);
        } else {
            T nextValue = linkedList.pop();
            Thunk<DLNode<T>> thisNode = new Thunk<>();
            Pair<DLNode<T>> pair = buildNodeLayer(thisNode, linkedList, nextNode);
            // p.f = next node or this node
            // p.s = prev node or p.s
            // next with previous, or, this with previous
            // empty -> next becomes next, this becomes previous
            //
            thisNode.hold(new DLNode<>(previousNode, nextValue, pair.left()));
            return new Pair<>(thisNode, pair.right());
        }
    }
    public static <T> @NotNull Thunk<DLNode<T>>
    mkDList(@NotNull LinkedList<T> linkedList) {
        if (linkedList.isEmpty()) {
            throw new RuntimeException("must have at least one element!");
        }
        Thunk<DLNode<T>> first = new Thunk<>();
        Thunk<DLNode<T>> last = new Thunk<>();
        Pair<DLNode<T>> firstNodeWithLastNode = tieRecursiveList(last,linkedList,first);
        // Is this the proper way of representing the switch?
        first.hold(firstNodeWithLastNode.left().get());
        last.hold(firstNodeWithLastNode.right().get());
        return first;
    }
    public static void main(String[] args) {
        LinkedList<Integer> integerLinkedList = new LinkedList<>(Arrays.asList(1, 2, 3, 4));
        Thunk<DLNode<Integer>> result = mkDList(integerLinkedList);

        while(result.get().getNext() != null) {
            System.out.println(result.get().getContent());
            result = result.get().getNext();

        }
    }
}
