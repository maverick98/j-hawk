package heap;

import java.util.Comparator;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author manosahu
 */
public class JesseCookies {

    public static void main(String args[]) {
       // Scanner scanner = new Scanner(System.in);

     
        Scanner scanner = null;
        try {
            scanner = new Scanner(new java.io.File("D:\\github\\j-hawk\\j-hawk\\j-hawk-code\\mvn\\hackerrank\\src\\main\\jesse.txt"));
        } catch (java.io.FileNotFoundException ex) {
            java.util.logging.Logger.getLogger(JesseCookies.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
 
         
        JesseCookies ste = new JesseCookies();

        Long result = ste.test(scanner);
        System.out.println(result);

    }

    public Long test(Scanner scanner) {

        Long result = Long.MIN_VALUE;
        Long n = null;
        Long k = null;

        MinHeap<Long> minHeap = new MinHeap<>();
        Comparator<Long> cmp = new Comparator<Long>() {
            @Override
            public int compare(Long thisOne, Long thatOne) {

                return thisOne.compareTo(thatOne);

            }
        };
        minHeap.cmp = cmp;

        while (true) {
            String line = scanner.nextLine();
            line = line.trim();
            if (n == null) {
                StringTokenizer strTok = new StringTokenizer(line, " ");
                n = Long.parseLong(strTok.nextToken());
                k = Long.parseLong(strTok.nextToken());

            } else {
                StringTokenizer strTok = new StringTokenizer(line, " ");
                while (strTok.hasMoreTokens()) {
                    minHeap.insert(Long.parseLong(strTok.nextToken()));
                }

                break;

            }

        }
        result = calculate(minHeap, k);
        return result;

    }

    public Long calculate(MinHeap<Long> minHeap, Long k) {
        Long result = new Long(0);

        while (true) {

            if (minHeap.min() >= k) {
                break;
            }
            Long min1 = minHeap.delMin();
            Long min2 = minHeap.delMin();
            if(min2 != null){
                minHeap.insert(this.combineCookie(min1, min2));
                result++;
            }else{
                if(min1 < k){
                    result = new Long(-1);
                    break;
                }
            }
            

        }

        return result;

    }

    private Long combineCookie(Long a, Long b) {
        return a + 2 * b;
    }
     static class Node {
        
        int index;
        int key;
        
        @Override
        public String toString() {
            return "Node{" + "index=" + index + ", key=" + key + '}';
        }
        
        public Node(int key) {
            this.key = key;
        }
        
        @Override
        public int hashCode() {
            int hash = 5;
            hash = 59 * hash + this.key;
            return hash;
        }
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Node other = (Node) obj;
            if (this.key != other.key) {
                return false;
            }
            return true;
        }
        
    }
    
   
    static class MinHeap<T> {

        T keys[];
        int n;
        Comparator cmp;

        public MinHeap() {
            this(1);
        }

        public MinHeap(int initialCapacity) {
            keys = (T[]) new Object[initialCapacity + 1];
        }

        public boolean isEmpty() {
            return n == 0;
        }

        public int size() {
            return n;
        }

        public T min() {
            if (this.isEmpty()) {
                return null;
            }
            return keys[1];
        }

        public T delMin() {
            if (this.isEmpty()) {
                return null;
            }
            T minNode = this.min();

            this.swap(1, n);
            n--;
            this.sink(1);

            return minNode;
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

        private int lesserBetween(int i, int j) {

            int tmp = cmp.compare(keys[i], keys[j]);
            int result;
            if (tmp <= 0) {
                result = i;
            } else {
                result = j;
            }

            return result;
        }

        private int lesserBetween(int i, int j, int k) {

            return this.lesserBetween(i, lesserBetween(j, k));

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
                    least = this.lesserBetween(i, left, right);
                } else {
                    least = this.lesserBetween(i, left);
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
                int least = this.lesserBetween(i, parent);
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
