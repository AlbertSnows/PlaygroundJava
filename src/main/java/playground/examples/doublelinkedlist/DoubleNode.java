package playground.examples.doublelinkedlist;

import java.util.function.Function;

public class DoubleNode<C> {
    final C content;
    private DoubleNode<C> prev;
    private DoubleNode<C> next;
    DoubleNode(C contents) {
        this.content = contents;
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
        return prior -> leader -> this.priorTo(prior).aheadOf(leader);
    }
}
