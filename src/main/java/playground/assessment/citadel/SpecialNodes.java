package playground.assessment.citadel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class SpecialNodes {
    public static void main(String[] args) {
        var amount = 7;
        var startingNodes = List.of(1, 1, 2, 2, 2, 3, 3, 3, 4, 5, 6, 7);
        var endingNodes =   List.of(2, 3, 1, 4, 6, 1, 7, 5, 2, 3, 2, 3);
        var indexes = IntStream.rangeClosed(0, startingNodes.size()).boxed().toList();
        var nodeToNeighbors = new HashMap<Integer, HashSet<Integer>>();
        for(var index : indexes) {
            var startingNode = startingNodes.get(index);
            var endingNode = endingNodes.get(index);
            if(nodeToNeighbors.containsKey(startingNode)) {
                nodeToNeighbors.get(startingNode).add(endingNode);
            } else {
                nodeToNeighbors.put(startingNode, new HashSet<>(Set.of(endingNode)));
            }
        }

    }
}
