package playground.concepts.searching;

import java.util.ArrayList;
import java.util.List;

public class Node {
    int value;
    Node parent;
    List<Node> children = new ArrayList<>();
    Node(int value) {
        this.value = value;
    }
    public Node connect(Node neighbor) {
        this.children.add(neighbor);
        return this;
    }
    public Node parent(Node parent) {
        this.parent = parent;
        return this;
    }
}
