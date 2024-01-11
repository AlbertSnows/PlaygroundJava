package playground.assessment.citadel;

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
            startToEndToDistance.put(startingNode, findDistancesForNode(startingNode, nodeToNeighbors));
        }
        var maxForNode = new HashMap<Integer, DistanceNode>();
        for(var startPairs : startToEndToDistance.entrySet()) {
            DistanceNode maxForCurrentNode = new DistanceNode(0, 0, 0);
            for(var endPairs : startPairs.getValue().entrySet()) {
                var endNode = endPairs.getValue();
                var endNodeIsLarger = endNode.dist > maxForCurrentNode.dist;
                if(endNodeIsLarger) {
                    maxForCurrentNode = endNode;
                }
            }
            maxForNode.put(startPairs.getKey(), maxForCurrentNode);
        }
        var diameter = maxForNode.values().stream()
                .reduce(0, (m, p) -> Math.max(m, p.dist), (p1, p2) -> p1);
        var specialPoints = maxForNode.values().stream()
                .filter(p -> Objects.equals(p.dist, diameter))
                .toList();
        var specialNodes = nodeToNeighbors.keySet().stream()
                .map(integers -> new AbstractMap.SimpleEntry<>(integers, 0))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        for(var point : specialPoints) {
            specialNodes.put(point.start, 1);
            specialNodes.put(point.end, 1);
        }
        System.out.println(specialNodes);
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
    }
}
