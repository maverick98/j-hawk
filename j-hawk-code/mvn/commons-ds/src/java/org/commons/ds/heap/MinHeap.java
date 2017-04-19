package org.commons.ds.heap;

/**
 *
 * @author manosahu
 */
public class MinHeap extends BinaryHeap {

    public MinHeap() {
        super();
    }

    public MinHeap(int initialCapacity) {
        super(initialCapacity);
    }

    public MinHeap(Comparable keys[]) {
        super(keys);
    }

    @Override
    protected int compareBetween(int i, int j) {
        return this.lesserBetween(i, j);

    }

    private int lesserBetween(int i, int j) {
        int result;
        if (this.keys[i].compareTo(this.keys[j]) <= 0) {
            result = i;
        } else {
            result = j;
        }
        return result;
    }

    public Comparable min() {
        return this.top();
    }

    public Comparable delMin() {
        return this.delTop();
    }

}
