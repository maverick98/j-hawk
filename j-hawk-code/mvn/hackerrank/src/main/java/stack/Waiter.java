package stack;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author manosahu
 */
public class Waiter {

    public static void main(String args[]) {
        Waiter waiter = new Waiter();
     /*   Long q = new Long(168);
        List<Long> primes = waiter.calculatePrimes(q);
        for(Long prime : primes){
            System.out.println(prime);
        }
       */
       Scanner scanner = new Scanner(System.in);
/*

        Scanner scanner = null;
        try {
            scanner = new Scanner(new java.io.File("D:\\github\\j-hawk\\j-hawk\\j-hawk-code\\mvn\\hackerrank\\src\\main\\waitertc2.txt"));
        } catch (java.io.FileNotFoundException ex) {
            java.util.logging.Logger.getLogger(Waiter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
*/
       

        List<Long> result = waiter.test(scanner);
    waiter.show(result);
  //      dump("D:\\github\\j-hawk\\j-hawk\\j-hawk-code\\mvn\\hackerrank\\src\\main\\waitertc2mine.txt",result);
        
    }

    public void show(List<Long> result) {
        for (Long res : result) {
            System.out.println(res);
        }
    }

    private static void dump(String file, List<Long> result) {
        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            for (Long res : result) {
                bw.write(res + "\n");
            }
            bw.close();
        } catch (IOException ex) {

        }
    }

    public List<Long> calculatePrimes(Long q) {
        List<Long> primes = new ArrayList<>();
        primes.add(new Long(2));
       
        while (true) {
            if (q.equals(new Long(primes.size()))) {
                break;
            }
             Long lastPrime = primes.get(primes.size()-1);
             for (Long i = lastPrime+1; i < 2*lastPrime; i++) {
                 if(this.isPrime(i)){
                     primes.add(i);
                     break;
                 }
             }

        }
      

        return primes;
    }

    public boolean isPrime(Long number) {
        boolean result = true;
        double d = Double.parseDouble(number.toString());
        for (Long i = new Long(2); i <= Math.sqrt(d); i++) {
            if (number % i == 0) {
                result = false;
                break;
            }
        }
        return result;
    }

    public List<Long> test(Scanner scanner) {

        Long n = null;
        Long q = null;

        Stack<Long> stack = new Stack<>();

        while (true) {
            String line = scanner.nextLine();
            line = line.trim();
            if (n == null) {
                StringTokenizer strTok = new StringTokenizer(line, " ");
                n = Long.parseLong(strTok.nextToken());
                q = Long.parseLong(strTok.nextToken());

            } else {
                StringTokenizer strTok = new StringTokenizer(line, " ");
                while (strTok.hasMoreTokens()) {
                    stack.push(Long.parseLong(strTok.nextToken()));
                }

                break;

            }

        }
        return this.calculate(stack, q);

    }

    public List<Long> calculate(Stack<Long> stack, Long q) {
        List<Long> result = new ArrayList<>();
        List<Long> primes = this.calculatePrimes(q);

        MinHeap<Node> minHeap = new MinHeap<>();
        Comparator<Node> cmp = new Comparator<Node>() {
            @Override
            public int compare(Node thisOne, Node thatOne) {

                return thisOne.prime.compareTo(thatOne.prime);

            }
        };
        minHeap.cmp = cmp;
        Stack<Long> remaining = new Stack<>();

        for (Long prime : primes) {

            Node primeNode = new Node();
            primeNode.prime = prime;

           
                while (!stack.isEmpty()) {
                    Long popped = stack.pop();
                    if (popped % prime == 0) {
                        primeNode.push(popped);
                    } else {
                        remaining.push(popped);
                    }
                }
                stack = remaining;
                remaining = new Stack<>();
                minHeap.insert(primeNode);
           
        }
        if (!stack.isEmpty()) {
            Node maxNode = new Node();
            maxNode.prime = Long.MAX_VALUE;
            Stack<Long> leftOver = new Stack<>();
            while (!stack.isEmpty()) {

                Long popped = stack.pop();
                leftOver.push(popped);

            }
            while(!leftOver.isEmpty()){
                maxNode.push(leftOver.pop());
            }
            minHeap.insert(maxNode);
        }
        
        while(!minHeap.isEmpty()){
            Node node= minHeap.delMin();
            Stack<Long> stack1 = node.myStack;
            while(!stack1.isEmpty()){
                result.add(stack1.pop());
            }
        }

        return result;
    }

    static class Node {

        Long prime;
        Stack<Long> myStack = new Stack<>();

        public void push(Long l) {
            myStack.push(l);
        }

        @Override
        public String toString() {
            return "Node{" + "prime=" + prime + '}';
        }

    }

    static class Stack<T> {

        private final List<T> data = new ArrayList<T>();

        private int size = 0;

        public boolean push(T obj) {
            if (obj == null) {
                return false;
            }
            boolean status;
            status = data.add(obj);
            size++;
            return status;
        }

        public <T> T pop() {

            T topObj = null;
            if (size > 0) {
                topObj = (T) data.get(size - 1);
                //data.remove(topObj);
                data.remove(size - 1);
                size--;
            }
            return topObj;
        }

        public <T> T peek() {

            T topObj = null;
            if (size > 0) {
                topObj = (T) data.get(size - 1);
            }
            return topObj;
        }

        public boolean isEmpty() {
            return this.size == 0;
        }

        @Override
        public String toString() {
            return this.isEmpty() ? "" : data.toString();
        }
    }

    static class Queue<T> {

        private LinkedList<T> list = new LinkedList<>();

        public void add(T item) {
            list.addLast(item);
        }

        public T remove() {
            return list.poll();
        }

        public boolean hasItems() {
            return !list.isEmpty();
        }

        public T peek() {
            return list.get(0);
        }

        public int size() {
            return list.size();
        }

        public void addItems(Queue<? extends T> q) {
            while (q.hasItems()) {
                list.addLast(q.remove());
            }
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
