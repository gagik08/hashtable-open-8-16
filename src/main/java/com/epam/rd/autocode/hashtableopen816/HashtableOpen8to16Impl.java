package com.epam.rd.autocode.hashtableopen816;

public class HashtableOpen8to16Impl implements HashtableOpen8to16 {
    private static final int INITIAL_CAPACITY = 8;
    private static final int MAX_CAPACITY = 16;
    private static final int MIN_CAPACITY = 2;
    private int size;
    private static final HashNode dummy = new HashNode(-1, -1);
    private HashNode[] buckets;
    private int actualCapacity;

    public HashtableOpen8to16Impl() {
        this.size = 0;
        this.buckets = new HashNode[INITIAL_CAPACITY];
        this.actualCapacity = INITIAL_CAPACITY;
    }

    private int hashcode(int key, int capacity) {
        return Math.abs(key % capacity);
    }

    @Override
    public void insert(int key, Object value) {
        if (containsKey(key)) {
            int hashIndex = findKeyIndex(key);
//            buckets[hashIndex].setKey(key);
            buckets[hashIndex].setValue(value);
        } else {
            increaseCapacity();
            int hashIndex = hashcode(key, actualCapacity);
            while (buckets[hashIndex] != null && buckets[hashIndex].getKey() != key && !buckets[hashIndex].getValue().equals(-1)) {
                hashIndex++;
                hashIndex %= actualCapacity;
            }
            buckets[hashIndex] = new HashNode(key, value);
            this.size++;
        }
    }

    private void increaseCapacity() {
        if (size + 1 > MAX_CAPACITY) {
            throw new IllegalStateException();
        }
        if (size == actualCapacity && actualCapacity < MAX_CAPACITY) {
            actualCapacity = actualCapacity * 2;
            buckets = redefineHashtable(buckets, actualCapacity);
        }
    }

    public HashNode[] redefineHashtable(HashNode[] initialHashtable, int capacity) {
        HashNode[] redefinedHashtable = new HashNode[capacity];
        int newIndex;
        for (HashNode node : initialHashtable) {
            if (node == null || node.getValue() == dummy.getValue()) continue;
            newIndex = hashcode(node.getKey(), capacity);
            while (redefinedHashtable[newIndex] != null && redefinedHashtable[newIndex].getKey() != node.getKey()) {
                newIndex++;
                newIndex %= actualCapacity;
            }
            redefinedHashtable[newIndex] = node;
        }
        return redefinedHashtable;
    }

    @Override
    public void remove(int key) {
        if (containsKey(key)) {
            int hashIndex = hashcode(key, actualCapacity);
            while (!buckets[hashIndex].getValue().equals(-1) || buckets[hashIndex].getKey() != key) {
                if (buckets[hashIndex].getKey() == key) {
                    buckets[hashIndex].setKey(0);
                    buckets[hashIndex].setValue(dummy.getValue());
                    size--;
                    decreaseCapacity();
                    break;
                }
                hashIndex++;
                hashIndex %= actualCapacity;
            }
        }
    }


    private void decreaseCapacity() {
        if (size == actualCapacity / 4 && actualCapacity > MIN_CAPACITY) {
            actualCapacity = actualCapacity / 2;
            buckets = redefineHashtable(buckets, actualCapacity);
        }
    }

    @Override
    public Object search(int key) {
        if (containsKey(key)) {
            int idx = findKeyIndex(key);
            return buckets[idx].getValue();
        } else {
            return null;
        }
    }

    private int findKeyIndex(int key) {
        int hashIndex = hashcode(key, actualCapacity);
        int counter = 0;
        while (buckets[hashIndex] != null) {
            if (counter > actualCapacity) {
                return -1;
            }
            if (buckets[hashIndex].getKey() == key) {
                return hashIndex;
            }
            hashIndex++;
            hashIndex %= actualCapacity;
            counter++;
        }
        return 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int[] keys() {
        int[] keys = new int[actualCapacity];
        for (int i = 0; i < keys.length; i++) {
            if (buckets[i] == null) {
                keys[i] = 0;
            } else if (buckets[i].getValue() != dummy.getValue()) {
                keys[i] = buckets[i].getKey();
            } else {
                keys[i] = 0;
            }
        }
        return keys;
    }

    public boolean containsKey(int key) {
        for (HashNode node : buckets) {
            if (node != null && node.getKey() == key && node.getValue().equals(key)) {
                return true;
            }
        }
        return false;
    }
}