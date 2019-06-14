package mateacademy.homework.homework_10;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class Storage<T, K> {

    private static final int DEFAULTCAPACITY = 10;
    private int currentCapacity;
    private int size;
    private int basket;
    private double loadFactor;
    Node<K, T> currentNode;
    public Node<K, T>[] values;

    public Storage() {
        currentCapacity = DEFAULTCAPACITY;
        size = 0;
    }

    private int getPutIndex(T key) {
        return key.hashCode() % currentCapacity;
    }

    private boolean isCollision(Node<K, T> node, T key) {
        return (size() > 0 && node != null && node.entry.getKey() != key);
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
        basket = getPutIndex(key);
        loadFactor = currentCapacity * 0.75;
        if (size < loadFactor) {
            currentNode = new Node(null, object, null);
            if (isCollision(values[basket], key)) {
                collisionList(values, basket, currentNode);
            } else {
                if (values[basket] != null
                        && (values[basket].entry.getKey().equals(currentNode.entry.getKey()))) {
                    values[basket].entry.setValue(currentNode.entry.getValue());
                } else {
                    values[basket] = currentNode;
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
        basket = getPutIndex(key);
        if (values[basket].entry.getKey().equals(key)
        ) {
            return values[basket].entry.getValue();
        }
        Node<K, T> temp = values[basket];
        do {
            temp = temp.next;
        } while (temp.entry.getKey().uquals(key));
        return temp.entry.getValue();
    }

    public int size() {
        return size;
    }

    private void growArray(Entry<T, K> entry) {
        Node<K, T>[] growArray = new Node[currentCapacity * 3 / 2];
        currentCapacity = growArray.length;
        basket = getPutIndex(entry.getKey());
        currentNode = new Node(null, entry, null);
        growArray[basket] = currentNode;
        size = 1;
        for (int i = 0; i < values.length; i++) {
            if (values[i] != null) {
                basket = getPutIndex(values[i].entry.getKey());
                currentNode = new Node(null, values[i].entry, null);
                if (isCollision(growArray[basket], values[i].entry.getKey())) {
                    collisionList(growArray, basket, currentNode);
                }
                growArray[basket] = currentNode;
                size++;
            }
        }
        values = Arrays.copyOf(growArray, growArray.length);
        for (int i = 0; i < growArray.length; i++) {
            growArray[i] = null;
        }
    }
}
