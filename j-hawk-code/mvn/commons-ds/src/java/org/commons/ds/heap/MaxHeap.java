package org.commons.ds.heap;

/**
 *
 * @author manosahu
 */
public class MaxHeap extends BinaryHeap {

    public MaxHeap() {
        super();
    }

    public MaxHeap(int initialCapacity) {
        super(initialCapacity);
    }

    public MaxHeap(Comparable keys[]) {
        super(keys);
    }

    @Override
    protected int compareBetween(int i, int j) {
        return this.greaterThan(i, j);

    }

    private int greaterThan(int i, int j) {
        int result;
        if (this.keys[i].compareTo(this.keys[j]) <= 0) {
            result = j;
        } else {
            result = i;
        }
        return result;
    }

    public Comparable max() {
        return this.top();
    }

    public Comparable delMax() {
        return this.delTop();
    }

}
