package playground.examples.doublelinkedlist;

import java.util.List;

public class Core {
    public static void main(String[] args) {
        var example = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        var nodes = DoubleNode.from(example).next;
        while(nodes.next != null) {
            System.out.println("before: " + nodes.prev.content);
            System.out.println("here: " + nodes.content);
            System.out.println("next: " + nodes.next.content);
            nodes = nodes.next;
        }
    }
}
