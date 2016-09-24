package balancedtree;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author manosahu
 */
public class MedianUpdateNG1 {

    public static void main(String args[]) {
        MedianUpdateNG1 mung = new MedianUpdateNG1();
        List<String> result = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        result = mung.findRunningMedian(scanner);

        /*
        Scanner scanner;

        try {
            scanner = new Scanner(new File("D:\\github\\j-hawk\\j-hawk\\j-hawk-code\\mvn\\hackerrank\\src\\main\\medianupdatetc7.txt"));
            long start = System.currentTimeMillis();
            result = mung.findRunningMedian(scanner);
            long diff = System.currentTimeMillis() - start;
            System.out.println("took " + diff + "ms");
        } catch (FileNotFoundException ex) {

        }
         */
        //   dump("D:\\github\\j-hawk\\j-hawk\\j-hawk-code\\mvn\\hackerrank\\src\\main\\medianupdatetc1mine.txt", result);
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

        BigDecimal median;

        if (avlTree.size() % 2 == 0) {
            Integer mid1 = avlTree.size() / 2;
            Integer mid2 = mid1 - 1;
            median = BigDecimal.valueOf(avlTree.select(mid1).key).add(BigDecimal.valueOf(avlTree.select(mid2).key)).divide(BigDecimal.valueOf(2));
        } else {
            median = BigDecimal.valueOf(avlTree.select(avlTree.size() / 2).key);
        }
        return (median.stripTrailingZeros().toPlainString());
    }

    static class AVLTree<Key extends Comparable<Key>> {

        class Node {

            Key key;
            int ht;
            int size;
            Node left;
            Node right;
        }
        Node root;

        public boolean isEmpty() {
            return root == null;
        }

        public int size() {
            return size(root);
        }

        private int size(Node x) {
            if (x == null) {
                return 0;
            }
            return x.size;
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
            x.size = 1 + size(x.left) + size(x.right);
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
            x.size = 1 + size(x.left) + size(x.right);
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
            } else if (x.left == null) {
                return x.right;
            } else if (x.right == null) {
                return x.left;
            } else {
                Node y = x;
                x = min(y.right);
                x.right = deleteMin(y.right);
                x.left = y.left;
            }
            x.ht = 1 + Math.max(height(x.left), height(x.right));
            x.size = 1 + size(x.left) + size(x.right);
            return balance(x);
        }

        public void insert(Key key) {
            root = insert(root, key);
        }

        private Node insert(Node x, Key key) {
            if (x == null) {
                Node node = new Node();
                node.ht = 0;
                node.size = 1;

                node.key = key;
                return node;
            }
            int cmp = key.compareTo(x.key);
            if (cmp > 0) {
                x.right = insert(x.right, key);
            } else if (cmp < 0) {
                x.left = insert(x.left, key);
            } else {
                Node y = x.left;
                Node node = new Node();
                if (y != null) {
                    node.ht = y.ht + 1;
                    node.size = y.size + 1;
                } else {
                    node.ht = 0;
                    node.size = 1;
                }
                node.key = key;
                x.left = node;
                node.left = y;

            }
            x.ht = 1 + Math.max(height(x.left), height(x.right));
            x.size = 1 + size(x.left) + size(x.right);
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
            x.size = 1 + size(x.left) + size(x.right);
            y.ht = 1 + Math.max(height(y.left), height(y.right));
            y.size = 1 + size(y.left) + size(y.right);
            return y;

        }

        private Node rotateRight(Node x) {

            Node y = x.left;
            x.left = y.right;
            y.right = x;
            x.ht = 1 + Math.max(height(x.left), height(x.right));
            x.size = 1 + size(x.left) + size(x.right);
            y.ht = 1 + Math.max(height(y.left), height(y.right));
            y.size = 1 + size(y.left) + size(y.right);
            return y;

        }

        public Node select(Integer k) {
            return select(root, k);
        }

        private Node select(Node x, Integer k) {
            if (x == null) {
                return null;
            }
            int left = size(x.left);
            if (left > k) {
                return select(x.left, k);
            } else if (left < k) {
                return select(x.right, k - left - 1);
            }
            return x;
        }

    }

}
