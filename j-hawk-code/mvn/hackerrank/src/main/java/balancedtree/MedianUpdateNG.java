package balancedtree;

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
import java.util.StringTokenizer;
import java.util.logging.Level;

/**
 *
 * @author manosahu
 */
public class MedianUpdateNG {

    public static void main(String args[]) {
        MedianUpdateNG mung = new MedianUpdateNG();
        List<String> result = new ArrayList<>();
       // Scanner scanner = new Scanner(System.in);
      //  result = mung.findRunningMedian(scanner);

    
        Scanner scanner;

        try {
            scanner = new Scanner(new File("D:\\github\\j-hawk\\j-hawk\\j-hawk-code\\mvn\\hackerrank\\src\\main\\medianupdatetc7.txt"));
            long start = System.currentTimeMillis();
            result = mung.findRunningMedian(scanner);
            long diff = System.currentTimeMillis()-start;
            System.out.println("took "+diff + "ms");
        } catch (FileNotFoundException ex) {
           
        }

     //   dump("D:\\github\\j-hawk\\j-hawk\\j-hawk-code\\mvn\\hackerrank\\src\\main\\medianupdatetc1mine.txt", result);

       // show(result);

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

    private List<String> findRunningMedian(Scanner scanner) {

        List<String> result = new ArrayList<>();
        int n = -1;
        AVLTree<Long> avlTree = new AVLTree<>();

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
                    String median = processAddition(key, avlTree);

                    result.add(median);
                } else if ("r".equals(opr)) {
                    String median = processRemoval(key, avlTree);

                    result.add(median);
                }
                
                if (count == n) {

                    break;
                }
            }

        }

        return result;

    }

    public String processAddition(Long key, AVLTree<Long> avlTree) {
        avlTree.insert(key);
        return computeMedian(avlTree);
    }

    public String processRemoval(Long key, AVLTree<Long> avlTree) {
        if (avlTree.isEmpty() || !avlTree.contains(key)) {
            return "Wrong!";
        }

        avlTree.delete(key);
        if (avlTree.isEmpty()) {
            return "Wrong!";
        }
        return computeMedian(avlTree);
    }

    private String computeMedian(AVLTree<Long> avlTree) {

        BigDecimal median = BigDecimal.ZERO;
         long start = System.currentTimeMillis();
        Integer size = avlTree.size();
         long diff = System.currentTimeMillis()-start;
       //  System.out.println("size took "+diff +"ms");
      
       List<Integer> medianIndices = new ArrayList<>();
        if (size % 2 != 0) {
            medianIndices.add(size / 2);
        } else {
            Integer second = (size + 1) / 2;
            Integer first = second - 1;
            medianIndices.add(first);
            medianIndices.add(second);
        }
      
         long start1 = System.currentTimeMillis();
        List<Long> keys = avlTree.traverse();
        long diff1 = System.currentTimeMillis()-start1;
      //   System.out.println("traverse took "+diff1 +"ms");

        if (size % 2 == 0) {
            BigDecimal sum = BigDecimal.valueOf(0);
            Integer second = (size + 1) / 2;
            Integer first = second - 1;
            sum = BigDecimal.valueOf(keys.get(first)).add(BigDecimal.valueOf(keys.get(second)));
            median = sum.divide(BigDecimal.valueOf(2), 1, RoundingMode.UNNECESSARY).stripTrailingZeros();
        } else {
            median = BigDecimal.valueOf(keys.get(size / 2));
        }
     
        return median.stripTrailingZeros().toPlainString();
    }

    static class AVLTree<Key extends Comparable<Key>> {

        class Node {

            Key key;
            int ht;
            int childSize;
            int frequency;
            Node left;
            Node right;
        }
        Node root;

        public boolean isEmpty() {
            return root == null;
        }
        
        public int size(){
            return size(root);
        }
        private int size(Node x){
            if(x == null){
                return 0;
            }
            return x.childSize + x.frequency;
        }

        public int height() {
            return height(root);
        }

        public boolean contains(Key key) {
            return get(key) != null;
        }

        public Node get(Key key) {
            if (key == null) {
                return null;
            }
            Node x = get(root, key);
            return x;
        }

        private Node get(Node x, Key key) {
            if (x == null) {
                return null;
            }
            int cmp = key.compareTo(x.key);
            if (cmp > 0) {
                return get(x.right, key);
            } else if (cmp < 0) {
                return get(x.left, key);
            } else {
                return x;
            }

        }

        private int height(Node x) {
            if (x == null) {
                return -1;
            }
            return x.ht;
        }

        public Node min() {
            return min(root);
        }

        private Node min(Node x) {
            if (x.left == null) {
                return x;
            }
            return min(x.left);
        }

        public void deleteMin() {
            root = deleteMin(root);
        }

        private Node deleteMin(Node x) {
            if (x.left == null) {
                return x.right;
            }
            x.left = deleteMin(x.left);
            x.ht = 1 + Math.max(height(x.left), height(x.right));
            x.childSize = size(x.left) +size(x.right);
            return balance(x);
        }

        public void deleteMax() {
            root = deleteMax(root);
        }

        private Node deleteMax(Node x) {
            if (x.right == null) {
                return x.left;
            }
            x.right = deleteMax(x.right);
            x.ht = 1 + Math.max(height(x.left), height(x.right));
            x.childSize = size(x.left) +size(x.right);
            return balance(x);
        }

        public Node max() {
            return max(root);
        }

        private Node max(Node x) {
            if (x.right == null) {
                return x;
            }
            return min(x.right);
        }

        public void delete(Key val) {
            root = delete(root, val);
        }

        private Node delete(Node x, Key key) {
            int cmp = key.compareTo(x.key);
            if (cmp > 0) {
                x.right = delete(x.right, key);
            } else if (cmp < 0) {
                x.left = delete(x.left, key);
            } else 
            {
                if (x.frequency > 1) {
                    x.frequency = x.frequency - 1;
                } else if (x.frequency == 1) {
                    if (x.left == null) {
                        return x.right;
                    } else if (x.right == null) {
                        return x.left;
                    }else{
                        Node y = x;
                        x = min(y.right);
                        x.right = deleteMin(y.right);
                        x.left = y.left;
                    }
                }
            }
            x.ht = 1 + Math.max(height(x.left), height(x.right));
            x.childSize = size(x.left) +size(x.right);
            return balance(x);
        }

        public void insert(Key key) {
            root = insert(root, key);
        }

        private Node insert(Node x, Key key) {
            if (x == null) {
                Node node = new Node();
                node.ht = 0;
                node.childSize = 0;
                node.frequency = 1;
                node.key = key;
                return node;
            }
            int cmp = key.compareTo(x.key);
            if (cmp > 0) {
                x.right = insert(x.right, key);
            } else if (cmp < 0) {
                x.left = insert(x.left, key);
            } else {
                x.frequency = x.frequency + 1;
                return x;
            }
            x.ht = 1 + Math.max(height(x.left), height(x.right));
            x.childSize = size(x.left) +size(x.right);
            return balance(x);
        }

        private Node balance(Node x) {

            if (balanceFactor(x) < -1) {
                if (balanceFactor(x.right) > 0) {
                    x.right = rotateRight(x.right);
                }
                x = rotateLeft(x);
            } else if (balanceFactor(x) > 1) {
                if (balanceFactor(x.left) < 0) {
                    x.left = rotateLeft(x.left);
                }
                x = rotateRight(x);
            }

            return x;
        }

        private int balanceFactor(Node x) {
            return height(x.left) - height(x.right);
        }

        private Node rotateLeft(Node x) {

            Node y = x.right;
            x.right = y.left;
            y.left = x;
            x.ht = 1 + Math.max(height(x.left), height(x.right));
            x.childSize = size(x.left) +size(x.right);
            y.ht = 1 + Math.max(height(y.left), height(y.right));
            y.childSize = size(y.left) +size(y.right);
            return y;

        }

        private Node rotateRight(Node x) {

            Node y = x.left;
            x.left = y.right;
            y.right = x;
            x.ht = 1 + Math.max(height(x.left), height(x.right));
            x.childSize = size(x.left) +size(x.right);
            y.ht = 1 + Math.max(height(y.left), height(y.right));
            y.childSize = size(y.left) +size(y.right);
            return y;

        }

        public List<Key> traverse() {
            List<Key> keys = new ArrayList<>();
            this.traverse(root, keys, 0);
            return keys;
        }

        private void traverse(Node x, List<Key> keys, int i) {
            if (x == null) {
                return;
            }
            traverse(x.left, keys, i);
            Integer frequency = x.frequency;
            for (Integer j = 0; j < frequency; j++) {
                keys.add(x.key);
            }
            traverse(x.right, keys, i + frequency);
        }
        
        
      
        
       
        
        

    }

}
