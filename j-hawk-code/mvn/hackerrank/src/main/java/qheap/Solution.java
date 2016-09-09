package qheap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author manosahu
 */
public class Solution {

    public static void main(String[] args)  {

        
        //findRunningMedian();
        //findRunningMedianFromFile();
       Scanner scanner = new Scanner(System.in);
        //test(scanner);
        // Scanner scanner = new Scanner(new File("D:\\github\\j-hawk\\j-hawk\\j-hawk-code\\mvn\\hackerrank\\src\\main\\sample.txt"));
        
        test(scanner);

    }

    public static void testFromFile(){
        
    }
    public static void test(Scanner scanner) {
        
        List<Integer> result = new ArrayList<>(); 
     
        int n = -1;
        MinHeap minHeap = new MinHeap();

        int count = 0;
        while (true) {
            String line = scanner.nextLine();
            if (n == -1) {
                n = Integer.parseInt(line.trim());

            } else {
                count++;
                line = line.trim();
                StringTokenizer strTok = new StringTokenizer(line, " ");
                Integer opr = Integer.parseInt(strTok.nextToken());
                Integer key = 0;
                if (opr == 1 || opr == 2) {
                    key = Integer.parseInt(strTok.nextToken());
                }
                if (opr == 1) {
                    //      minHeap.insert(key);
                    Node node = new Node(key);
                    minHeap.insert(node);
                    //map.put(node,node.index);
                } else if (opr == 2) {

                    Node node = new Node(key);
                    int idx = minHeap.find(node);
                   
                    minHeap.delete(idx);
                    
                    
                } else if (opr == 3) {
                    result.add((Integer)minHeap.min().key);
                }
                if (count == n) {

                    break;
                }
            }

        }
        for(Integer res : result){
            System.out.println(res);
        }
        
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

    static class MinHeap {

        Node keys[];
        int n;
        Map<Node, Integer> map = new HashMap<>();
        public MinHeap() {
            this(1);
        }

        public MinHeap(int initialCapacity) {
            keys = new Node[initialCapacity + 1];
        }
        
        public int find(Node node){
            return map.get(node);
        }

        public boolean isEmpty() {
            return n == 0;
        }

        public int size() {
            return n;
        }

        public Node min() {
            if (this.isEmpty()) {
                return null;
            }
            return keys[1];
        }

        public Node delMin() {
            if (this.isEmpty()) {
                return null;
            }
            Node minNode = this.min();

            this.swap(1, n);
            n--;
            this.sink(1);

            return minNode;
        }
        
        public void delete(int index){
            if(index <1 || index >n){
                return;
            }
            this.swap(index, n);
            n--;
            this.sink(index);
        }

        public void insert(Node node) {
            if (n == keys.length - 1) {
                resize(2 * keys.length);
            }
            n++;
            keys[n] = node;
            node.index = n;
            this.swim(n);
            map.put(node, node.index);
        }

        private void resize(int newCapacity) {
            Node newKeys[] = new Node[newCapacity];
            //todo use system.arraycopy
            for (int i = 1; i <= n; i++) {
                newKeys[i] = keys[i];
            }
            keys = newKeys;
        }

        private int lesserBetween(int i, int j) {
            int result;
            if (keys[i].key <=(keys[j].key) ) {
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
            keys[i].index = j;
            keys[j].index = i;
            Node tmp = keys[i];
            keys[i] = keys[j];
            keys[j] = tmp;
            
            map.put(keys[i], keys[i].index);
            map.put(keys[j], keys[j].index);
            

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
