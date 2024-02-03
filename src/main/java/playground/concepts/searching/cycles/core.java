package playground.concepts.searching.cycles;

import org.jetbrains.annotations.NotNull;
import playground.concepts.searching.Node;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;

public class core {
    public static @NotNull HashMap<Integer, Node>
    makeGraph(@NotNull List<Node> nodes, Map<Integer, List<Integer>> parentToChildren) {
        HashMap<Integer, Node> vToChildren = new HashMap<>();
        for(Node node : nodes) {
            vToChildren.put(node.value, node);
        }
        for(var pair : parentToChildren.entrySet()) {
            var parent = vToChildren.get(pair.getKey());
            var children = pair.getValue();
            children.forEach(neighbor_value ->
                    parent.connect(vToChildren.get(neighbor_value).parent(parent)));
        }
        return vToChildren;
    }

    public static boolean testCycleGraph(Function<HashMap<Integer, Node>, Boolean> searcher) {
        List<Node> nodes = IntStream.rangeClosed(0, 4).boxed().map(Node::new).toList();
        var parentToChildrenV1 = Map.of(
                0, List.of(1, 2, 3),
                1, List.of(0),
                2, List.of(0, 3, 4),
                3, List.of(0, 2),
                4, List.of(2));
        var valueToNode = makeGraph(nodes, parentToChildrenV1);
        var hasCycle = searcher.apply(valueToNode);
        return hasCycle;
    }

    public static boolean testTreeGraph(@NotNull Function<HashMap<Integer, Node>, Boolean> searcher) {
        List<Node> nodes = IntStream.rangeClosed(0, 7).boxed().map(Node::new).toList();
        var parentToChildrenV1 = Map.of(
                0, List.of(1),
                1, List.of(2, 3),
                2, List.of(4, 6),
                3, List.of(5, 7));
        var valueToNode = makeGraph(nodes, parentToChildrenV1);
        var hasCycle = searcher.apply(valueToNode);
        return hasCycle;
    }

    public static void main(String[] args) {
//        Function<Function<HashMap<Integer, Node>, Boolean>, Boolean> x = core::testCycleGraph;
        List<Function<Function<HashMap<Integer, Node>, Boolean>, Boolean>> output =
                List.of(core::testCycleGraph, core::testTreeGraph);
        output
                .stream().map(testcase -> testcase.apply(DFSExample::hasCycle))
                .forEach(supplier -> System.out.println("dfs, has cycle?" + supplier));
        output
                .stream().map(testcase -> testcase.apply(BFSExample::hasCycle))
                .forEach(supplier -> System.out.println("dfs, has cycle?" + supplier));

    }
}
