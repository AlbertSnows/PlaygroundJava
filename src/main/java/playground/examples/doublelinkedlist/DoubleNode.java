package playground.examples.doublelinkedlist;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

public class DoubleNode<C> {
    C content;
    DoubleNode<C> prev;
    DoubleNode<C> next;
    DoubleNode(C contents) {
        this.content = contents;
    }

    public static <C> DoubleNode<C> from(@NotNull List<C> list) {
        var length = list.size();
        var nodeList = list.stream()
                .map(DoubleNode::new).toList();
        IntConsumer addNeighbors = i -> nodeList.get(i).between()
                        .apply(nodeList.get(i-1))
                        .apply(nodeList.get(i+1));
        IntStream.rangeClosed(1, length - 2).forEach(addNeighbors);
        nodeList.get(length - 1).aheadOf(nodeList.get(length - 2));
        nodeList.get(0).priorTo(nodeList.get(1));
        return nodeList.get(0);
    }
    public DoubleNode<C> content(C data) {
        this.content = data;
        return this;
    }
    public DoubleNode<C> aheadOf(DoubleNode<C> prior) {
        this.prev = prior;
        return this;
    }

    public DoubleNode<C> priorTo(DoubleNode<C> leader) {
        this.next = leader;
        return this;
    }

    public Function<DoubleNode<C>, Function<DoubleNode<C>, DoubleNode<C>>> between() {
        return prior -> leader -> this.priorTo(leader).aheadOf(prior);
    }
}
