package org.commons.ds.queue;

import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 *
 * @author manosahu
 */
public class PriorityQueue<Key> {

    private Key[] keys;

    private int size;
    private Comparator<Key> comparator;

    public PriorityQueue() {

    }

    public PriorityQueue(int max) {

    }

    public PriorityQueue(Key[] a) {

    }

    public Key[] getKeys() {
        return keys;
    }

    public void setKeys(Key[] keys) {
        this.keys = keys;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Comparator<Key> getComparator() {
        return comparator;
    }

    public void setComparator(Comparator<Key> comparator) {
        this.comparator = comparator;
    }

    public void insert(Key v) {

    }

    private void exchange(int i, int j) {
        Key swap = keys[i];
        keys[i] = keys[j];
        keys[j] = swap;
    }

    public Key max() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Priority queue underflow");
        }
        return this.getKeys()[1];
    }

    public Key delMax() {
        return null;
    }

    public boolean isEmpty() {
        return this.getSize() == 0;
    }

    public int size() {
        return this.getSize();
    }

}
