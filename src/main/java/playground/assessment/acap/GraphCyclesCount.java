package playground.assessment.acap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;

public class GraphCyclesCount {
    public void placeholder() {
        int g_nodes = 0;
        List<Integer> gFrom = Arrays.asList(1, 2, 1, 3, 1, 2);
        List<Integer> gTo = Arrays.asList(2, 3, 3, 5, 4, 6);
        IntStream range = IntStream.range(0, gFrom.size() -1 );
        HashMap<Integer, HashSet<Integer>> nodeRelationships = new HashMap<>();
        for(int index : range.toArray()) {
            int gFromVal = gFrom.get(index);
            int gToVal = gTo.get(index);
            boolean fromExists = nodeRelationships.containsKey(gFromVal);
            boolean toExists = nodeRelationships.containsKey(gToVal);
            if(fromExists) {
                HashSet<Integer> list = nodeRelationships.get(gFromVal);
                list.add(gToVal);
                nodeRelationships.put(gFromVal, list);
            } else {
                nodeRelationships.put(gFromVal, new HashSet<>(gToVal));
            }
            if(toExists) {
                HashSet<Integer> list = nodeRelationships.get(gToVal);
                list.add(gFromVal);
                nodeRelationships.put(gToVal, list);
            } else {
                nodeRelationships.put(gToVal, new HashSet<>(gFromVal));
            }
        }

    }
}
