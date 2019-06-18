package mateacademy.homework.homework_10;

public class Main {

    public static void main(String[] args) {
        Storage<Integer, String> storage = new Storage<>();
        storage.put(1, "first");
        storage.put(2, "second");
        storage.put(3, "third");
        storage.put(4, "fourth");
        storage.put(5, "blabla");
        storage.put(6, "set");
        storage.put(7, "777");
        storage.put(8, "777");
        storage.put(9, "777");
        storage.put(10, "777");

        System.out.println(storage.get(1));
        System.out.println(storage.get(2));
        System.out.println(storage.get(3));
        System.out.println(storage.get(4));
        System.out.println(storage.get(5));
        System.out.println(storage.get(6));
        System.out.println(storage.get(7));
        System.out.println(storage.get(8));
        System.out.println(storage.get(9));
        System.out.println(storage.get(10));

        for (int i = 0; i < 1000; i++){
            storage.put(i, "value:"+i);
            System.out.println(storage.get(i));
        }
//        storage.put(128, "777");
//        System.out.println(storage.get(128));
    }
}
