package playground.assessment.citadel;

import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SpecialNodes {
    public static void main(String[] args) {
//        var amount = 7;
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
        var startToEndToDistance = new HashMap<Integer, HashMap<Integer, Integer>>();
        for(var startingNode : nodeToNeighbors.keySet()) {
            startToEndToDistance = searchDeeper(startingNode, nodeToNeighbors, startToEndToDistance);
        }
        var maxForNode = new HashMap<Integer, AbstractMap.SimpleEntry<Integer, Integer>>();
        for(var startPairs : startToEndToDistance.entrySet()) {
            AbstractMap.SimpleEntry<Integer, Integer> maxForCurrentNode = new AbstractMap.SimpleEntry<>(0, 0);
            for(var endPairs : startPairs.getValue().entrySet()) {
                maxForCurrentNode = maxForCurrentNode.getKey() > endPairs.getValue() ?
                        maxForCurrentNode :
                        new AbstractMap.SimpleEntry<>(endPairs.getValue(), endPairs.getKey());
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

    private static HashMap<Integer, HashMap<Integer, Integer>>
    searchDeeper(Integer startingNode,
                 HashMap<Integer, HashSet<Integer>> nodeToNeighbors,
                 HashMap<Integer, HashMap<Integer, Integer>> startToEndToDistance,
                 Integer depth) {
        startToEndToDistance.put(startingNode, new HashMap<>(Map.of(startingNode, 0)));
        var nodeNeighbors = nodeToNeighbors.get(startingNode);
        HashMap<Integer, HashMap<Integer, Integer>> finalStartToEndToDistance = startToEndToDistance;
        var neighborsToCheck = nodeNeighbors.stream()
                .filter(neighbor -> !finalStartToEndToDistance.containsKey(neighbor))
                .toList();
        for(var neighbor : neighborsToCheck) {
            startToEndToDistance.get(startingNode).put(neighbor, depth + 1);
            startToEndToDistance = searchDeeper(neighbor, nodeToNeighbors, startToEndToDistance, depth + 1);
        }
        return  startToEndToDistance;
    }
}
