package com.epam.rd.autocode.hashtableopen816;

public class HashNode {
    int key;
    Object value;

    public HashNode(int key, Object value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
