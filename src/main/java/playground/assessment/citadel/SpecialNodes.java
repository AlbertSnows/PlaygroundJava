package playground.assessment.citadel;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class DistanceNode {
    public Integer start;
    public Integer end;
    public Integer dist;
    DistanceNode(Integer start, Integer end, Integer dist) {
        this.start = start;
        this.end = end;
        this.dist = dist;
    }
}

public class SpecialNodes {
    public static void main(String[] args) {
//        var amount = 7;
        var startingNodes = List.of(1, 1, 2, 2, 2, 3, 3, 3, 4, 5, 6, 7);
        var endingNodes =   List.of(2, 3, 1, 4, 6, 1, 7, 5, 2, 3, 2, 3);
        var indexes = IntStream.rangeClosed(0, startingNodes.size() - 1).boxed().toList();
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
        var startToEndToDistance = new HashMap<Integer, HashMap<Integer, DistanceNode>>();
        for(var startingNode : nodeToNeighbors.keySet()) {
            startToEndToDistance
                    .put(startingNode, findDistancesForNode(startingNode, nodeToNeighbors));

        }
        var maxForNode = new HashMap<Integer, DistanceNode>();
        for(var startPairs : startToEndToDistance.entrySet()) {
            DistanceNode maxForCurrentNode = new DistanceNode(0, 0, 0);
            for(var endPairs : startPairs.getValue().entrySet()) {
//                maxForCurrentNode = maxForCurrentNode.dist > endPairs.getValue().dist ?
//                        maxForCurrentNode :
//                        new DistanceNode(startPairs.getKey(), endPairs.getValue(), endPairs.getKey());
            }
            maxForNode.put(startPairs.getKey(), maxForCurrentNode);
        }
        int diameter = 0;
        for(var startToMaxAndEndPair : maxForNode.entrySet()) {
            diameter = Math.max(diameter, startToMaxAndEndPair.getKey());
        }
        Integer finalDiameter = diameter;
        var diameterPairs = maxForNode.entrySet().stream()
                .filter(pair -> pair.getValue().getKey() >= finalDiameter)
                .map(pair -> new AbstractMap.SimpleEntry<>(pair.getKey(), pair.getValue().getKey()))
                .toList();
        var size = nodeToNeighbors.keySet().size();
        var specialNodes = new ArrayList<>(Collections.nCopies(size, 0));
        for(var pair : diameterPairs) {
            specialNodes.set(specialNodes.get(pair.getKey()), 1);
            specialNodes.set(specialNodes.get(pair.getValue()), 1);
        }
        System.out.println(specialNodes);
//        return specialNodes;
    }

    private static @NotNull HashMap<Integer, DistanceNode>
    findDistancesForNode(Integer startingNode, @NotNull HashMap<Integer, HashSet<Integer>> nodeToNeighbors) {
        var startingDistanceNode = new DistanceNode(startingNode, startingNode, 0);
        var distanceMap = new HashMap<>(Map.of(startingNode, startingDistanceNode));
        var nodesToCheck = new ArrayDeque<DistanceNode>();
        nodesToCheck.add(startingDistanceNode);
        while(!nodesToCheck.isEmpty()) {
            var currentNode = nodesToCheck.pop();
            var nodeNeighbors = nodeToNeighbors.get(currentNode.end);
            var neighborsToAdd = nodeNeighbors.stream()
                    .filter(node -> !distanceMap.containsKey(node))
                    .toList();
            for(var neighborToAdd : neighborsToAdd) {
                var nextNode = new DistanceNode(startingDistanceNode.start, neighborToAdd, currentNode.dist + 1);
                distanceMap.put(neighborToAdd, nextNode);
                nodesToCheck.add(nextNode);
            }
        }
        return distanceMap;


//        HashMap<Integer, HashMap<Integer, Integer>> finalStartToEndToDistance = startToEndToDistance;
//        var neighborsToCheck = nodeNeighbors.stream()
//                .filter(neighbor -> !finalStartToEndToDistance.containsKey(neighbor))
//                .toList();

        //        while(haveNodes) {
//            var currentNode = nodes.pop();
//            var foundPath = startToEndToDistance.get(startingNode).containsKey(currentNode);
//            if(!foundPath) {
////                startToEndToDistance.get(startingNode)
////                        .put(currentNode, );
//            }
//        }
//        return new HashMap<>();


//        for(var neighbor : neighborsToCheck) {
//            startToEndToDistance.get(startingNode).put(neighbor, depth + 1);
//            startToEndToDistance.put()
//                    findDistancesForNode(neighbor, nodeToNeighbors, startToEndToDistance, depth + 1);
//        }
//        return  startToEndToDistance;
    }
}
