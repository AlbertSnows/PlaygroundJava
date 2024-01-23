package playground.concepts.searching;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.IntStream;

public class DFSExample {
    public static boolean hasCycle(@NotNull HashMap<Integer, Node> valuesToNode) {
        var visited = new HashSet<Node>();
        var nodesToCheck = new ArrayDeque<Node>();
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
    public static void main(String[] main) {

    }

    // 0 -> 1 2 3
    // 1 -> 0
    // 2 -> 0 3 4
    // 3 -> 0 2
    // 4 -> 2
}
