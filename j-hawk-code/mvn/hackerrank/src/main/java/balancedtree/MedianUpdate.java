package balancedtree;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author manosahu
 */
public class MedianUpdate {

    public static void main(String args[]) {

        List<String> result = new ArrayList<>();
        //    Scanner scanner = new Scanner(System.in);
        //  result = findRunningMedianFromFile(scanner);

        Scanner scanner;

        try {
            scanner = new Scanner(new File("D:\\github\\j-hawk\\j-hawk\\j-hawk-code\\mvn\\hackerrank\\src\\main\\medianupdatetc1.txt"));
            result = findRunningMedianFromFile(scanner);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MedianUpdate.class.getName()).log(Level.SEVERE, null, ex);
        }

        dump("D:\\github\\j-hawk\\j-hawk\\j-hawk-code\\mvn\\hackerrank\\src\\main\\medianupdatetc1mine.txt", result);
        show(result);

    }

    private static void dump(String file, List<String> result) {
        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            for (String res : result) {
                bw.write(res + "\n");
            }
            bw.close();
        } catch (IOException ex) {

        }
    }

    private static void show(List<String> result) {
        for (String res : result) {
            System.out.println(res);
        }
    }

    private static List<String> findRunningMedianFromFile(Scanner scanner) {

        List<String> result = new ArrayList<>();
        int n = -1;
        BinaryHeap minHeap = new BinaryHeap();
        Comparator<Long> minCmp = new Comparator<Long>() {
            @Override
            public int compare(Long t, Long t1) {
                return t.compareTo(t1);
            }
        };
        minHeap.cmp = minCmp;
        BinaryHeap maxHeap = new BinaryHeap();
        Comparator<Long> maxCmp = new Comparator<Long>() {
            @Override
            public int compare(Long t, Long t1) {
                return -1 * (t.compareTo(t1));
            }
        };
        maxHeap.cmp = maxCmp;

        int count = 0;
        while (true) {

            if (n == -1) {
                n = Integer.parseInt(scanner.nextLine());;

            } else {

                count++;
                String input = scanner.nextLine();
                StringTokenizer strTok = new StringTokenizer(input, " ");
                String opr = strTok.nextToken();
                Long key = Long.parseLong(strTok.nextToken());

                if ("a".equals(opr)) {
                    BigDecimal median = processAddition(key, count, minHeap, maxHeap);

                    result.add(median.stripTrailingZeros() + "");
                } else if ("r".equals(opr)) {
                    String median = processRemoval(key, count, minHeap, maxHeap);

                    result.add(median);
                }

                if (count == 5) {
                    System.out.println("hello");
                }
                if (count == n) {

                    break;
                }
            }

        }

        return result;

    }

    public static String processRemoval(Long key, Integer size, BinaryHeap<Long> minHeap, BinaryHeap<Long> maxHeap) {
        String result = "";

        if (maxHeap.isEmpty() && minHeap.isEmpty()) {
            return "Wrong!";
        }

        boolean deleted = minHeap.delete(key);
        if (!deleted) {
            deleted = maxHeap.delete(key);
        }
        if (!deleted) {
            return "Wrong!";
        }

        Long max = maxHeap.peek();
        Long min = minHeap.peek();
        if (max != null && min != null) {
            if (max > min) {
                // exchange them
                max = maxHeap.poll();
                min = minHeap.poll();
                minHeap.insert(max);
                maxHeap.insert(min);
            }
        }
        if (maxHeap.isEmpty() && minHeap.isEmpty()) {
            return "Wrong!";
        }

        BigDecimal median;
        if (maxHeap.size() == minHeap.size()) {
            Long maxRoot = maxHeap.peek();
            Long minRoot = minHeap.peek();
            if (minRoot == null) {
                minRoot = new Long(0);
            }
            BigDecimal sum = new BigDecimal(maxRoot + minRoot);
            median = sum.divide(BigDecimal.valueOf(2), 1, RoundingMode.UNNECESSARY);
        } else if (maxHeap.size() > minHeap.size()) {
            median = new BigDecimal(maxHeap.peek());
        } else {
            median = new BigDecimal(minHeap.peek());
        }

        result = median.stripTrailingZeros().toString();

        return result;
    }

    public static BigDecimal processAddition(Long key, Integer size, BinaryHeap<Long> minHeap, BinaryHeap<Long> maxHeap) {

        int maxHalf = (size + 1) / 2;

        if (maxHeap.isEmpty()) {
            maxHeap.insert(key);

        } else {
            Long max = maxHeap.peek();
            if (key <= max) {
                if (maxHeap.size() == maxHalf) {
                    Long tmp = maxHeap.poll();
                    maxHeap.insert(key);
                    minHeap.insert(tmp);
                } else {
                    maxHeap.insert(key);
                }

            } else if (key > max) {
                if (minHeap.size() == maxHalf) {
                    Long tmp = minHeap.poll();
                    minHeap.insert(key);
                    maxHeap.insert(tmp);
                } else {
                    minHeap.insert(key);
                }
            }
        }

        Long max = maxHeap.peek();
        Long min = minHeap.peek();
        if (max != null && min != null) {
            if (max > min) {
                // exchange them
                max = maxHeap.poll();
                min = minHeap.poll();
                minHeap.insert(max);
                maxHeap.insert(min);
            }
        }

        BigDecimal median;
        if (maxHeap.size() == minHeap.size()) {
            Long maxRoot = maxHeap.peek();
            Long minRoot = minHeap.peek();
            if (minRoot == null) {
                minRoot = new Long(0);
            }
            BigDecimal sum = new BigDecimal(maxRoot + minRoot);
            median = sum.divide(BigDecimal.valueOf(2), 1, RoundingMode.UNNECESSARY);
        } else if (maxHeap.size() > minHeap.size()) {
            median = new BigDecimal(maxHeap.peek());
        } else {
            median = new BigDecimal(minHeap.peek());
        }

        return median;
    }

    static class BinaryHeap<T> {

        T keys[];
        int n;

        Comparator cmp;

        public BinaryHeap() {
            this(1);
        }

        public BinaryHeap(int initialCapacity) {
            keys = (T[]) new Object[initialCapacity + 1];
        }

        public boolean isEmpty() {
            return n == 0;
        }

        public int size() {
            return n;
        }

        public T peek() {
            if (this.isEmpty()) {
                return null;
            }
            return keys[1];
        }

        public T poll() {
            if (this.isEmpty()) {
                return null;
            }
            T minInteger = this.peek();

            this.swap(1, n);
            n--;
            this.sink(1);

            return minInteger;
        }

        public boolean delete(T node) {
            return delete(find(node));
        }

        public int find(T node) {
            int result = -1;
            for (int i = 1; i <= this.size(); i++) {
                if (keys[i].equals(node)) {
                    result = i;
                    break;
                }
            }
            return result;

        }

        public boolean delete(int index) {
            if (index < 1 || index > n) {
                return false;
            }
            this.swap(index, n);
            n--;
            this.sink(index);
            return true;
        }

        public void insert(T node) {
            if (n == keys.length - 1) {
                resize(2 * keys.length);
            }
            n++;
            keys[n] = node;

            this.swim(n);

        }

        private void resize(int newCapacity) {
            T newKeys[] = (T[]) new Object[newCapacity];
            //todo use system.arraycopy
            for (int i = 1; i <= n; i++) {
                newKeys[i] = keys[i];
            }
            keys = newKeys;
        }

        private int compareBetween(int i, int j) {
            int tmp = cmp.compare(keys[i], keys[j]);
            int result;
            if (tmp <= 0) {
                result = i;
            } else {
                result = j;
            }

            return result;
        }

        private int compareBetween(int i, int j, int k) {

            return this.compareBetween(i, compareBetween(j, k));

        }

        private void swap(int i, int j) {

            T tmp = keys[i];
            keys[i] = keys[j];
            keys[j] = tmp;

        }

        private int parent(int i) {
            return i / 2;
        }

        private int left(int i) {
            return 2 * i;
        }

        private int right(int i) {
            return left(i) + 1;
        }

        private void sink(int i) {

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

        private void swim(int i) {

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

    }

}
