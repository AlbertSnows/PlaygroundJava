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