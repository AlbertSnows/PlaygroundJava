
//    private static boolean dfs(HashMap<Integer, HashSet<Integer>> pair,
//                               HashSet<Integer> visited,
//                               Integer parent) {
//        for(Integer neighbor : pair.values()) {
//            boolean haveVisited = visited.contains(neighbor);
//            boolean notParent = !Objects.equals(neighbor, parent);
//            if(!haveVisited) {
//                dfs(neighbor, pair.Key());
//            } else if(notParent) {
//            }
//        }
//        return false;
//    }

/*
 * Complete the 'getNumberOfDroppedPackets' function below.
 *
 * The function is expected to return a LONG_INTEGER.
 * The function accepts following parameters:
 *  1. 2D_INTEGER_ARRAY requests
 *  2. INTEGER max_packets
 *  3. INTEGER rate
 */

//    public static long getNumberOfDroppedPackets() {
//        var max_packets = 7;
//        var rate = 3;
//        var requests = List.of(
//                List.of(2, 8),
//                List.of(1, 10),
//                List.of(3, 4)
////                ,
////                List.of(73, 19339),
////                List.of(45, 59342)
//        );
//        // r = 3
//        // mp = 7
//        //
//        Comparator<List<Integer>> sorter = Comparator.comparing(ll -> ll.get(0));
//        var mutableList = new ArrayList<>(requests);
//        mutableList.sort(sorter);
//        var currentPackets = 0;
//        var lastTime = 0;
//        var packetsDropped = 0;
//        for(var packetsAtTime : mutableList) {
//            var currentTime = packetsAtTime.get(0);
//            var packets = packetsAtTime.get(1);
//            var timeSinceLastDelivery = currentTime - lastTime;
//            var packetsSent = timeSinceLastDelivery * rate;
//            currentPackets = currentPackets - packetsSent;
//            currentPackets = currentPackets + packets;
//            var amountOverMax = currentPackets - max_packets;
//            if(amountOverMax > 0) {
//                packetsDropped = packetsDropped + amountOverMax;
//                currentPackets = currentPackets - amountOverMax;
//            }
//            lastTime = currentTime;
//        }
//
//        return packetsDropped;
//    }

// insert, upsert, delete
// epoch_millis | type | key | value
// ex: 1563454984001 | INSERT | test | 123
// keep track of most recent time
// read input
// add to hash table
// printo to stdout
// string args[]
//        var scanner = new Scanner(System.in);
//        var inputs = new ArrayList<String>();
//        while(scanner.hasNextLine()) {
//            var line = scanner.nextLine();
//            inputs.add(line);
//        }
//        var parsedInputs = new ArrayList<ParsedInput>();
//        for(var input : inputs) {
//            var splitInput = input.split("\\|");
//            var isDelete = Objects.equals(splitInput[1], "DELETE");
//            var parsedInput = new ParsedInput(
//                    Long.parseLong(splitInput[0]),
//                    splitInput[1],
//                    splitInput[2],
//                    isDelete ? null : splitInput[3]);
//            parsedInputs.add(parsedInput);
//        }
//
//        var hashTable = new HashMap<String, String>();
//        long mostRecentTime = 0;
//        for(var parsedInput : parsedInputs) {
//            var time = parsedInput.time;
//            var type = parsedInput.type;
//            var key = parsedInput.key;
//            var value = parsedInput.value;
//            var keyExists = hashTable.containsKey(key);
//            switch (type){
//                case "INSERT":
//                    if(!keyExists) {
//                        hashTable.put(key, value);
//                    }
//                    break;
//                case "DELETE":
//                    if(keyExists) {
//                        hashTable.remove(key);
//                    }
//                    break;
//                case "UPSERT":
//                    hashTable.put(key, value);
//                    break;
//                default:
//                    break;
//            }
//            mostRecentTime = time;
//        }
//        // High Watermark: Mon Dec 01 19:06:43 UTC 51513
//        // High Watermark: 2019-07-18T13:03:04.003Z
//        var formattedTime = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSz");
//        try {
//            var asEPOCH = formattedTime.parse(Long.toString(mostRecentTime));
//            System.out.println("High Watermark: " + asEPOCH);
//            System.out.println("Table State: ");
//            for(var entry : hashTable.entrySet()) {
//                System.out.println("\t" + entry.getKey() + ": " + entry.getValue());
//            }
//        } catch(Exception e) {
//            System.out.println(e.getCause());
//        }
//                new Date(TimeUnit.SECONDS.toMillis(mostRecentTime));

//        getNumberOfDroppedPackets();