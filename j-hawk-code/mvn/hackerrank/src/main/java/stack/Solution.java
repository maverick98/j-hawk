package stack;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author manosahu
 */
public class Solution {
    
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
                data.remove(topObj);
                size--;
            }
            return topObj;
        }
        
        public <T> T top() {
            
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
    
    static class MaxHeap {
        
        Node keys[];
        int n;
        Map<Node, Integer> map = new HashMap<>();
        
        public MaxHeap() {
            this(1);
        }
        
        public MaxHeap(int initialCapacity) {
            keys = new Node[initialCapacity + 1];
        }
        
        public int find(Node node) {
            return map.get(node);
        }
        
        public boolean isEmpty() {
            return n == 0;
        }
        
        public int size() {
            return n;
        }
        
        public Node max() {
            if (this.isEmpty()) {
                return null;
            }
            return keys[1];
        }
        
        public Node delMax() {
            if (this.isEmpty()) {
                return null;
            }
            Node minNode = this.max();
            
            this.swap(1, n);
            n--;
            this.sink(1);
            
            return minNode;
        }
        
        public void delete(int index) {
            if (index < 1 || index > n) {
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
        
        private int greaterBetween(int i, int j) {
            int result;
            if (keys[i].key <= (keys[j].key)) {
                result = j;
            } else {
                result = i;
            }
            return result;
        }
        
        private int greaterBetween(int i, int j, int k) {
            
            return this.greaterBetween(i, greaterBetween(j, k));
            
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
                    least = this.greaterBetween(i, left, right);
                } else {
                    least = this.greaterBetween(i, left);
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
                int least = this.greaterBetween(i, parent);
                if (i == least) {
                    this.swap(i, parent);
                    i = parent;;
                    
                } else {
                    break;
                }
            }
        }
        
    }
    
    public static void main(String args[]) {
        /*
        Scanner scanner = new Scanner(System.in);
        List<Integer> result = test(scanner);
        show(result);
         */
        Scanner scanner = null;
        try {
            scanner = new Scanner(new java.io.File("D:\\github\\j-hawk\\j-hawk\\j-hawk-code\\mvn\\hackerrank\\src\\main\\sample.txt"));
        } catch (Exception ex) {
            
        }
        List<Integer> result = test(scanner);
        dump("D:\\github\\j-hawk\\j-hawk\\j-hawk-code\\mvn\\hackerrank\\src\\main\\output.txt", result);
        
    }
    
    private static void dump(String file, List<Integer> result) {
        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            for (Integer res : result) {
                bw.write(res+"\n");
            }
            bw.close();
        } catch (IOException ex) {
            
        }
    }
    
    private static void show(List<Integer> result) {
        for (Integer res : result) {
            System.out.println(res);
        }
    }
    
    public static List<Integer> test(Scanner scanner) {
        
        List<Integer> result = new ArrayList<>();
        
        int n = -1;
        Stack<Integer> stack = new Stack<>();
        //MaxHeap maxHeap = new MaxHeap();

        PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(new Comparator<Integer>() {
            public int compare(Integer c1, Integer c2) {
                return -1 * Integer.compare(c1, c2);
            }
        });
        
        int count = 0;
        int curMax = -1;
        int count3=0;
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
                if (opr == 1) {
                    key = Integer.parseInt(strTok.nextToken());
                }
                if (opr == 1) {
                    stack.push(key);
                    
                    maxHeap.add(key);
                } else if (opr == 2) {
                    
                    int popped = stack.pop();
                    Node node = new Node(popped);
                    maxHeap.remove(popped);
                    
                } else if (opr == 3) {
                    count3++;
                    if (!maxHeap.isEmpty()) {
                        curMax = maxHeap.peek();
                        result.add(curMax);
                    }
                }
                if(count3 == 87){
                    System.out.println(maxHeap.peek());
                }
                if (count == n) {
                    
                    break;
                }
            }
            
        }
        return result;
        
    }
    
}
