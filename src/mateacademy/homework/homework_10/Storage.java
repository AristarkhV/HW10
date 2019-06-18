package mateacademy.homework.homework_10;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class Storage<T, K> {

    private static final int DEFAULTCAPACITY = 10;
    private int currentCapacity;
    private int size;
    private int basketNumber;
    private double loadFactor;
    private Node<K, T> currentNode;
    private Node<K, T> basketNode;
    public Node<K, T>[] values;

    public Storage() {
        currentCapacity = DEFAULTCAPACITY;
        size = 0;
    }

    private int getPutIndex(T key) {
        return key.hashCode() % currentCapacity;
    }

    private boolean isCollision(Node<K, T> basketNode, T currentNodeKey) {
        return (size() > 0
                && basketNode != null
                && basketNode.entry.getKey().equals(currentNodeKey));
    }

    private void collisionList(Node<K, T>[] node, int basket, Node<K, T> currentNode) {
        if (node[basket].next == null) {
            node[basket].next = currentNode;
        } else {
            Node<K, T> temp = node[basket];
            do {
                temp = temp.next;
            } while (temp.next != null);
            temp.next = currentNode;
        }
    }

    public void put(T key, K value) {
        if (size() == 0) {
            values = new Node[currentCapacity];
        }
        Entry object = new Entry(key, value);
        basketNumber = getPutIndex(key);
        loadFactor = currentCapacity * 0.75;
        basketNode = values[basketNumber];
        if (size < loadFactor) {
            currentNode = new Node(null, object, null);
            if (isCollision(basketNode, key)) {
                collisionList(values, basketNumber, currentNode);
            } else {
                if (basketNode != null
                        && (basketNode.entry.getKey().equals(currentNode.entry.getKey()))) {
                    basketNode.entry.setValue(currentNode.entry.getValue());
                } else {
                    values[basketNumber] = currentNode;
                    size++;
                }
            }
        } else {
            growArray(object);
            put(key, value);
        }
    }

    public K get(T key) {
        if (values[getPutIndex(key)] == null) {
            throw new NoSuchElementException();
        }
        basketNumber = getPutIndex(key);
        basketNode = values[basketNumber];
        if (basketNode.entry.getKey().equals(key)
        ) {
            return basketNode.entry.getValue();
        }
        Node<K, T> temp = basketNode;
        do {
            temp = temp.next;
        } while (temp.entry.getKey().equals(key));
        return temp.entry.getValue();
    }

    public int size() {
        return size;
    }

    private void growArray(Entry<T, K> entry) {
        Node<K, T>[] growArray = new Node[currentCapacity * 2];
        currentCapacity = growArray.length;
        basketNumber = getPutIndex(entry.getKey());
        currentNode = new Node(null, entry, null);
        growArray[basketNumber] = currentNode;
        basketNode = growArray[basketNumber];
        size = 1;
        for (int i = 0; i < values.length; i++) {
            if (values[i] != null) {
                T valuesKey = values[i].entry.getKey();
                basketNumber = getPutIndex(valuesKey);
                currentNode = new Node(null, values[i].entry, null);
                if (isCollision(basketNode, valuesKey)) {
                    collisionList(growArray, basketNumber, currentNode);
                }
                growArray[basketNumber] = currentNode;
                size++;
            }
        }
        values = Arrays.copyOf(growArray, growArray.length);
        for (int i = 0; i < growArray.length; i++) {
            growArray[i] = null;
        }
    }
}
