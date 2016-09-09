
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author manosahu
 */
public class Solution {

    public static void main(String[] args) throws FileNotFoundException {
        List<BigDecimal> result;
        Scanner scanner = new Scanner(System.in);
        result = findRunningMedian(scanner);

        //Scanner scanner = new Scanner(new File("D:\\github\\j-hawk\\j-hawk\\j-hawk-code\\mvn\\hackerrank\\src\\main\\input.txt"));
        //result = findRunningMedianFromFile(scanner);

        //dump("D:\\github\\j-hawk\\j-hawk\\j-hawk-code\\mvn\\hackerrank\\src\\main\\output1.txt", result);
        show(result);
    }

    private static void show(List<BigDecimal> result) {
        for (BigDecimal res : result) {
            System.out.println(res);
        }
    }

    private static void dump(String file, List<BigDecimal> result) {
        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            for (BigDecimal res : result) {
                bw.write(res.doubleValue() + "\n");
            }
            bw.close();
        } catch (IOException ex) {

        }
    }

    public static List<BigDecimal> findRunningMedian(Scanner scanner) {

        List<BigDecimal> result = new ArrayList<>();
        int n = -1;
        MinHeap minHeap = new MinHeap();
        MaxHeap maxHeap = new MaxHeap();

        int count = 0;
        while (true) {
            Integer number = Integer.parseInt(scanner.nextLine());
            if (n == -1) {
                n = number;

            } else {
                count++;
                BigDecimal median = findMedian(number, count, minHeap, maxHeap);
                result.add(median);

                if (count == n) {

                   
                    break;
                }
            }

        }

        return result;

    }

    private static List<BigDecimal> findRunningMedianFromFile(Scanner scanner) throws FileNotFoundException {

        List<BigDecimal> result = new ArrayList<>();
        int n = -1;
        MinHeap minHeap = new MinHeap();
        MaxHeap maxHeap = new MaxHeap();

        int count = 0;
        while (true) {
            Integer number = Integer.parseInt(scanner.nextLine());
            if (n == -1) {
                n = number;

            } else {

                count++;
                long start = System.currentTimeMillis();
                BigDecimal median = findMedian(number, count, minHeap, maxHeap);
                long end = System.currentTimeMillis() - start;

                result.add(median);

                if (count == 2409) {
                    maxHeap.max();
                }
                if (count == n) {

                    for (BigDecimal answer : result) {
                        System.out.println(answer.doubleValue());

                    }
                    break;
                }
            }

        }

        return result;

    }

    public static BigDecimal findMedian(Integer key, Integer size, MinHeap minHeap, MaxHeap maxHeap) {

        int maxHalf = (size + 1) / 2;

        if (maxHeap.isEmpty()) {
            maxHeap.insert(key);

        } else {
            Integer max = (Integer) maxHeap.max();
            if (key <= max) {
                if (maxHeap.size() == maxHalf) {
                    Integer tmp = (Integer) maxHeap.delMax();
                    maxHeap.insert(key);
                    minHeap.insert(tmp);
                } else {
                    maxHeap.insert(key);
                }

            } else if (key > max) {
                if (minHeap.size() == maxHalf) {
                    Integer tmp = (Integer) minHeap.delMin();
                    minHeap.insert(key);
                    maxHeap.insert(tmp);
                } else {
                    minHeap.insert(key);
                }
            }
        }

        Integer max = (Integer) maxHeap.max();
        Integer min = (Integer) minHeap.min();
        if (max != null && min != null) {
            if (max > min) {
                // exchange them
                max = (Integer) maxHeap.delMax();
                min = (Integer) minHeap.delMin();
                minHeap.insert(max);
                maxHeap.insert(min);
            }
        }

        BigDecimal median;
        if (maxHeap.size() == minHeap.size()) {
            Integer maxRoot = (Integer) maxHeap.max();
            Integer minRoot = (Integer) minHeap.min();
            if (minRoot == null) {
                minRoot = 0;
            }
            BigDecimal sum = new BigDecimal(maxRoot + minRoot);
            median = sum.divide(BigDecimal.valueOf(2), 1, RoundingMode.UNNECESSARY);
        } else if (maxHeap.size() > minHeap.size()) {
            median = new BigDecimal((Integer) maxHeap.max());
        } else {
            median = new BigDecimal((Integer) minHeap.min());
        }

        return median;
    }

    public static BigDecimal findMedian1(Integer key, Integer size, MinHeap minHeap, MaxHeap maxHeap) {

        int maxHalf = (size + 1) / 2;
        if (maxHeap.size() < maxHalf) {
            maxHeap.insert(key);
        } else {
            Integer max = (Integer) maxHeap.max();
            if (key < max) {

                Integer tmp = (Integer) maxHeap.delMax();
                maxHeap.insert(key);
                minHeap.insert(tmp);

            } else {
                minHeap.insert(key);
            }
        }

        BigDecimal median;
        if (size % 2 == 1) {
            median = new BigDecimal((Integer) maxHeap.max());
        } else {
            Integer maxRoot = (Integer) maxHeap.max();
            Integer minRoot = (Integer) minHeap.min();
            if (minRoot == null) {
                minRoot = 0;
            }
            BigDecimal sum = new BigDecimal(maxRoot + minRoot);
            median = sum.divide(BigDecimal.valueOf(2), 1, RoundingMode.UNNECESSARY);

        }
        return median;
    }

    static abstract class BinaryHeap {

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
            return n;
        }

        public boolean isEmpty() {
            return n == 0;
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

        protected boolean greaterThanLowerBound(int i) {
            return i > 1;
        }

        protected boolean lessThanEqualUpperBound(int i) {
            return i <= this.size();
        }

        protected abstract int compareBetween(int i, int j);

        protected int compareBetween(int i, int j, int k) {

            return this.compareBetween(i, this.compareBetween(j, k));
        }

        protected void swap(int i, int j) {
            Comparable tmpKey = this.keys[i];
            this.keys[i] = this.keys[j];
            this.keys[j] = tmpKey;
        }

        public void swim(int i) {

            while (true) {

                int parent = parent(i);
                if (parent < 1) {
                    break;
                }
                int least = this.compareBetween(i, parent);
                if (i == least) {
                    this.swap(i, parent);
                    i = parent;;

                } else {
                    break;
                }
            }

        }

        public void sink(int i) {

            while (true) {
                int left = this.left(i);

                if (left > n) {
                    break;
                }
                int right = this.right(i);
                int least = -1;
                if (right <= n) {
                    least = this.compareBetween(i, left, right);
                } else {
                    least = this.compareBetween(i, left);
                }
                if (i != least) {
                    this.swap(i, least);
                    i = least;
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
            for (int i = 1; i <= n; i++) {
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

    static class MaxHeap extends BinaryHeap {

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
            if (this.keys[i].compareTo(this.keys[j]) > 0) {
                result = i;
            } else {
                result = j;
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

    static class MinHeap extends BinaryHeap {

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

}
