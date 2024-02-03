package playground.concepts.searching.cycles;

import org.jetbrains.annotations.NotNull;
import playground.concepts.searching.Node;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

public class BFSExample {
    public static boolean
    hasCycle(@NotNull HashMap<Integer, Node> valuesToNode) {
        var visited = new HashSet<Node>();
        var nodesToCheck = new Stack<Node>();
        nodesToCheck.add(valuesToNode.get(0));
        var hasCycle = false;
        while(!nodesToCheck.isEmpty() && !hasCycle) {
            var currentNode = nodesToCheck.pop();
            visited.add(currentNode);
            for(var child : currentNode.children) {
                hasCycle = child.parent != currentNode && visited.contains(child);
                if(hasCycle) {
                    break;
                } else {
                    nodesToCheck.add(child);
                }
            }
        }
        return hasCycle;
    }
}
