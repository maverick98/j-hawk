package org.commons.ds.heap;

/**
 *
 * @author manosahu
 * @param <Key>
 */
public abstract class BinaryHeap {

    protected Comparable[] keys;
    private int n;

    public BinaryHeap() {
        this(1);
    }

    public BinaryHeap(int initialCapacity) {
        this.keys = new Comparable[initialCapacity + 1];
        this.n = 0;

    }

    public BinaryHeap(Comparable keys[]) {

        this.n = keys.length;
        this.keys = new Comparable[n + 1];

        for (int i = 0; i < n; i++) {
            this.keys[i + 1] = keys[i];
        }
        int mid = this.parent(n);
        for (int i = mid; i >= 1; i--) {
            this.sink(i);
        }
    }

    public Comparable[] sort() {

        int count = this.size();
        for (int i = 0; i < count; i++) {
            this.delTop();
        }
        return this.keys;
    }

    public int size() {
        return this.n;
    }

    public boolean isEmpty() {
        return this.size() == 0;
    }

    public int parent(int i) {
        return i / 2;
    }

    public int left(int i) {
        return 2 * i;
    }

    public int right(int i) {
        return this.left(i) + 1;
    }

    private boolean greaterThanLowerBound(int i) {
        return i > 1;
    }

    private boolean lessThanEqualUpperBound(int i) {
        return i <= this.size();
    }

    protected abstract int compareBetween(int i, int j);

   

   

    private int compareBetween(int i, int j, int k) {

        return this.compareBetween(i, this.compareBetween(j, k));
    }

    private void swap(int i, int j) {
        Comparable tmpKey = this.keys[i];
        this.keys[i] = this.keys[j];
        this.keys[j] = tmpKey;
    }

    public void swim(int i) {

        while (this.greaterThanLowerBound(i)) {
            int parent = this.parent(i);
            int least = this.compareBetween(i, parent);
            if (i == least) {
                this.swap(i, parent);
                i = parent;
            }else{
                break;
            }
        }

    }

    public void sink(int i) {
        int right = this.right(i);
        while (this.lessThanEqualUpperBound(right)) {
            int left = this.left(i);
            right = this.right(i);
            int least = this.compareBetween(i, left, right);
            if (i != least) {
                this.swap(i, least);
                i = least;
                right = this.right(i);
            } else {
                break;
            }
        }
    }

    public Comparable top() {
        if (this.isEmpty()) {
            return null;
        }
        return this.keys[1];
    }

    public Comparable delTop() {
        if (this.isEmpty()) {
            return null;
        }
        Comparable minKey = this.keys[1];
        this.swap(1, n);
        n = n - 1;
        this.sink(1);
        return minKey;
    }

    public void insert(Comparable key) {
        if (n == keys.length - 1) {
            this.resize(2 * keys.length);
        }
        this.keys[n + 1] = key;
        n = n + 1;
        this.swim(n);
    }

    private void resize(int newCapacity) {
        Comparable tmp[] = new Comparable[newCapacity];
        for (int i = 1; i <= this.size(); i++) {
            tmp[i] = this.keys[i];
        }
        this.keys = tmp;
    }

    public Comparable[] getKeys() {
        return keys;
    }

    public void setKeys(Comparable[] keys) {
        this.keys = keys;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

}
