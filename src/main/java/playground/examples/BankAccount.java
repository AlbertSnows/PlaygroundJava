package playground.examples;

import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class BankAccount {
    public static void main(String[] args) {
        var inputList = List.of(
                Pair.of("create", List.of("david")),
                Pair.of("balance", List.of("1")),
                Pair.of("add", List.of("1", "100")),
                Pair.of("remove", List.of("1", "90")),
                Pair.of("create", List.of("david")),
                Pair.of("balance", List.of("1")));
        var accounts = new HashMap<Integer, BasicAccount>();
        final int[] max = {0};
        Consumer<Pair<String, List<String>>> routeRequest = input  -> {
            var command = input.getLeft();
            var parameters = input.getRight();
            switch (command) {
                case "create":
                    var newAccount = new BasicAccount(max[0], parameters.get(0), 0);
                    accounts.put(max[0], newAccount);
                    max[0]++;
                    break;
                case "balance":
                    var id = Integer.parseInt(parameters.get(0));
                    var exists = accounts.containsKey(id);
                    var balance = exists ? accounts.get(id).balance : "No account found";
                    System.out.println(balance);
                    break;
                case "add":
                    var accountIdForAdd = Integer.parseInt(parameters.get(0));
                    var amountForAdd = parameters.get(1);
                    var addAccount = accounts.get(accountIdForAdd);
                    var addExists = accounts.containsKey(accountIdForAdd);
                    if(addExists) {
                        addAccount.balance += Integer.parseInt(amountForAdd);
                        accounts.put(accountIdForAdd, addAccount);
                    }
                    break;
                case "remove":
                    var accountIdForRemove = Integer.parseInt(parameters.get(0));
                    var amountForRemove = parameters.get(1);
                    var removeAccount = accounts.get(accountIdForRemove);
                    var removeExists = accounts.containsKey(accountIdForRemove);
                    if(removeExists) {
                        removeAccount.balance -= Integer.parseInt(amountForRemove);
                        accounts.put(accountIdForRemove, removeAccount);
                    }
                    break;
                default:
                    System.out.println("Input " + command +" not recognized.");
                    break;
            }
        };

        inputList.forEach(routeRequest);
        accounts.entrySet().forEach(System.out::println);
//        inputList.forEach(System.out::println);


    }
}

class BasicAccount {
    Integer id;
    String name;
    Integer balance;
    BasicAccount(Integer id, String name, Integer balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }
}

//    public static final class ParsedInput{
//        public final long time;
//        public final String type;
//        public final String key;
//        public final String value;
//        public ParsedInput(long time, String type, String key, String value) {
//            this.time = time;
//            this.type = type;
//            this.key = key;
//            this.value = value;
//
//        }
//    }