package playground.core;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.sql.Array;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static final class ParsedInput{
        public final long time;
        public final String type;
        public final String key;
        public final String value;
        public ParsedInput(long time, String type, String key, String value) {
            this.time = time;
            this.type = type;
            this.key = key;
            this.value = value;

        }
    }
    public static void main(String[] args) {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        System.out.println("Hello and welcome!");
        // Press Shift+F10 or click the green arrow button in the gutter to run the code.
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
        HashSet<Integer> visited = new HashSet<>();
        for(Map.Entry<Integer, HashSet<Integer>> pair : nodeRelationships.entrySet()) {
            boolean info = dfs(nodeRelationships, visited, -1);
        }

    }

    private static boolean dfs(HashMap<Integer, HashSet<Integer>> pair,
                               HashSet<Integer> visited,
                               Integer parent) {
        for(Integer neighbor : pair.values()) {
            boolean haveVisited = visited.contains(neighbor);
            boolean notParent = !Objects.equals(neighbor, parent);
            if(!haveVisited) {
                dfs(neighbor, pair.Key());
            } else if(notParent) {
                //
            }
        }

    }

    /*
     * Complete the 'getNumberOfDroppedPackets' function below.
     *
     * The function is expected to return a LONG_INTEGER.
     * The function accepts following parameters:
     *  1. 2D_INTEGER_ARRAY requests
     *  2. INTEGER max_packets
     *  3. INTEGER rate
     */

    public static long getNumberOfDroppedPackets() {
        var max_packets = 7;
        var rate = 3;
        var requests = List.of(
                List.of(2, 8),
                List.of(1, 10),
                List.of(3, 4)
//                ,
//                List.of(73, 19339),
//                List.of(45, 59342)
        );
        // r = 3
        // mp = 7
        //
        Comparator<List<Integer>> sorter = Comparator.comparing(ll -> ll.get(0));
        var mutableList = new ArrayList<>(requests);
        mutableList.sort(sorter);
        var currentPackets = 0;
        var lastTime = 0;
        var packetsDropped = 0;
        for(var packetsAtTime : mutableList) {
            var currentTime = packetsAtTime.get(0);
            var packets = packetsAtTime.get(1);
            var timeSinceLastDelivery = currentTime - lastTime;
            var packetsSent = timeSinceLastDelivery * rate;
            currentPackets = currentPackets - packetsSent;
            currentPackets = currentPackets + packets;
            var amountOverMax = currentPackets - max_packets;
            if(amountOverMax > 0) {
                packetsDropped = packetsDropped + amountOverMax;
                currentPackets = currentPackets - amountOverMax;
            }
            lastTime = currentTime;
        }

        return packetsDropped;
    }
}