package org.commons.ds.heap;

import java.util.Comparator;

/**
 *
 * @author manosahu
 */
public class BinaryHeap<Key> {

    Key[] keys;
    int heapSize;
    private Comparator<Key> comparator;

    public Key[] getKeys() {
        return keys;
    }

    public void setKeys(Key[] keys) {
        this.keys = keys;
    }

    public int getHeapSize() {
        return heapSize;
    }

    public void setHeapSize(int heapSize) {
        this.heapSize = heapSize;
    }

    public Comparator<Key> getComparator() {
        return comparator;
    }

    public void setComparator(Comparator<Key> comparator) {
        this.comparator = comparator;
    }

    public BinaryHeap(Key[] keys) {
        this.keys = keys;
        this.heapSize = this.keys.length;
    }

    public void heapify(int i) {

        int largest = i;
        int left = this.left(i);
        if (left < this.getHeapSize() && this.greater(left, i)) {
            largest = left;
        }
        int right = this.right(i);
        if (right < this.getHeapSize() && this.greater(right, largest)) {
            largest = right;
        }
        if (largest != i) {
            this.exchange(i, largest);
            this.heapify(largest);
        }

    }

    public void buildHeap() {
        int i = (this.getKeys().length - 1) / 2;

        for (; i >= 0; i--) {
            this.heapify(i);
        }
    }

    public void sort() {
        this.buildHeap();
        int i = this.getKeys().length - 1;
        for (; i >= 1; i--) {
            this.exchange(0, i);
            this.decreaseHeapSize();
            this.heapify(0);

        }
    }

    public void show() {
        for (int i = 0; i < this.getKeys().length; i++) {
            System.out.print(this.getKeys()[i] + "   ,");
        }
    }

    private boolean greater(int i, int j) {
        if (comparator == null) {
            return ((Comparable<Key>) keys[i]).compareTo(keys[j]) > 0;
        } else {
            return comparator.compare(keys[i], keys[j]) > 0;
        }
    }

    public void decreaseHeapSize() {
        if (this.getHeapSize() >= 1) {
            this.setHeapSize(this.getHeapSize() - 1);
        } else {
            throw new IllegalStateException("can not decrease beyond 0");
        }
    }

    public void increaseHeapSize() {

        this.setHeapSize(this.getHeapSize() + 1);

    }

    public int left(int i) {
        return i * 2 + 1;
    }

    public int right(int i) {
        return this.left(i) + 1;
    }

    private void exchange(int i, int j) {
        Key swap = keys[i];
        keys[i] = keys[j];
        keys[j] = swap;
    }

    public static void main(String args[]) {
        Integer a[] = new Integer[]{4, 1, 3, 2, 16, 9, 10, 14, 8, 7};
        BinaryHeap<Integer> heap = new BinaryHeap(a);
        heap.sort();
        heap.show();

    }
}
