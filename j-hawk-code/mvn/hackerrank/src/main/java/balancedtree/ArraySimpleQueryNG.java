package balancedtree;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author manosahu
 */
public class ArraySimpleQueryNG {

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        ArraySimpleQueryNG asqng = new ArraySimpleQueryNG();
        Scanner scanner = new Scanner(System.in);

        //    Scanner scanner;
        AVLTree avlTree = null;
        try {
            //  scanner = new Scanner(new File("D:\\github\\j-hawk\\j-hawk\\j-hawk-code\\mvn\\hackerrank\\src\\main\\arraysimpletc1.txt"));
            scanner = new Scanner(new File("D:\\github\\j-hawk\\j-hawk\\j-hawk-code\\mvn\\hackerrank\\src\\main\\arrayquery.txt"));

            avlTree = asqng.compute(scanner);

        } catch (FileNotFoundException ex) {

        }

        // Integer input[] = test(scanner);
        System.out.println(Math.abs(avlTree.get(1).linkNode.data - avlTree.get(NN).linkNode.data));
        asqng.print(avlTree.get(1).linkNode);
    }

    private static int NN;

    public AVLTree compute(Scanner scanner) {

        int N = -1;
        int M = -1;

        int count = 0;
        LinkNode headNode = null;
        LinkNode tailNode = null;
        AVLTree avlTree = new AVLTree();
        while (true) {
            String line = scanner.nextLine();
            StringTokenizer strTok = new StringTokenizer(line, " ");

            if (N == -1) {
                N = Integer.parseInt(strTok.nextToken());
                M = Integer.parseInt(strTok.nextToken());
                NN = N;

            } else if (count == 0) {
                for (int i = 0; i < N; i++) {
                    LinkNode node = new LinkNode(Integer.parseInt(strTok.nextToken()));
                    if (headNode == null) {
                        headNode = node;
                        tailNode = headNode;
                        avlTree.insert(1, headNode);
                    } else {
                        tailNode.right = node;
                        node.left = tailNode;
                        tailNode = tailNode.right;
                    }
                }
                avlTree.insert(N, tailNode);

                count++;
            } else {

                int opr = Integer.parseInt(strTok.nextToken());
                int start = Integer.parseInt(strTok.nextToken());
                int end = Integer.parseInt(strTok.nextToken());
                if (opr == 1) {
                    leftRotate(avlTree, avlTree.get(1).linkNode, avlTree.get(N).linkNode, start, end);
                } else if (opr == 2) {
                    rightRotate(avlTree, avlTree.get(1).linkNode, avlTree.get(N).linkNode, start, end, N);
                }
                count++;
                if (count > M) {
                    break;
                }

            }

        }

        return avlTree;

    }

    public void leftRotate(AVLTree avlTree, LinkNode headNode, LinkNode tailNode, Integer start, Integer end) {
        LinkNode s1 = findLinkNode(avlTree, start);

        LinkNode e1 = findLinkNode(avlTree, end);

        if (end - start < start - 1) {

            int i = start;
            avlTree.delete(start);
            LinkNode e2 = headNode;
            int cnt = 1;
            while (i < end) {

                i++;
                cnt++;
                e2 = e2.right;
                avlTree.delete(i);
            }
            avlTree.delete(end);

            LinkNode e2Right = e2.right;
            LinkNode e1Right = e1.right;
            e1.right = e2Right;
            if (e2Right != null) {
                e2Right.left = e1;
            }
            e2.right = e1Right;

            LinkNode s1Left = s1.left;
            s1.left = null;
            s1Left.right = headNode;
            headNode.left = s1Left;
            avlTree.insert(1, s1);
            avlTree.insert(end + 1, e1Right);

         //   print(s1);

        } else {
            int i = start;
            avlTree.delete(start);

            int cnt = 1;
            while (i < end) {

                i++;
                cnt++;

                avlTree.delete(i);
            }
            avlTree.delete(end);

            LinkNode s1Left = s1.left;
            s1.left = null;
            LinkNode e1Right = e1.right;
            e1.right = headNode;
            headNode.left = e1;
            if (s1Left != null) {
                s1Left.right = e1Right;
            }
            if (e1Right != null) {
                e1Right.left = s1Left;
            }
         //   print(s1);
            avlTree.insert(1, s1);
            avlTree.insert(cnt, e1);
            avlTree.insert(end, s1Left);

        }

    }

    public void rightRotate(AVLTree avlTree, LinkNode headNode, LinkNode tailNode, Integer start, Integer end, int N) {
        LinkNode s1 = findLinkNode(avlTree, start);

        LinkNode e1 = findLinkNode(avlTree, end);

        if (end - start < N - end) {

            int i = end;
            avlTree.delete(end);
            LinkNode s2 = tailNode;
            int cnt = 1;
            while (i > start) {
                i--;
                cnt++;
                s2 = s2.left;
                avlTree.delete(i);
            }
            avlTree.delete(start);
            LinkNode s2Left = s2.left;
            LinkNode s1Left = s1.left;
            s2.left = s1Left;
            if (s1Left != null) {
                s1Left.right = s2;
            }
            s1.left = s2Left;
            if (s2Left != null) {
                s2Left.right = s1;
            }

            LinkNode e1Right = e1.right;
            e1.right = null;
            tailNode.right = e1Right;
            e1Right.left = tailNode;

            avlTree.insert(N, e1);
            avlTree.insert(start, s2);
            avlTree.insert(end, tailNode);
            avlTree.insert(N - cnt + 1, s1);

         //   print(s2);
        } else {
            int i = end;
            avlTree.delete(end);

            int cnt = 1;
            while (i > start) {
                i--;
                cnt++;
                avlTree.delete(i);
            }
            avlTree.delete(start);
            LinkNode s1Left = s1.left;

            LinkNode e1Right = e1.right;
            if (s1Left != null) {
                s1Left.right = e1Right;
            }
            if (e1Right != null) {
                e1Right.left = s1Left;
            }

            e1.right = null;
            tailNode.right = s1;
            s1.left = tailNode;
            avlTree.insert(N, e1);
            avlTree.insert(end - start + 1, s1);
            avlTree.insert(start, e1Right);

           // print(headNode);
        }

    }

    private void print(LinkNode headNode) {
        while (headNode != null) {
            System.out.print(headNode.data + " ");
            headNode = headNode.right;
        }
        System.out.println();
    }

    private LinkNode findLinkNode(AVLTree avlTree, Integer start) {
        AVLTree.Node start1 = this.findNearestNode(avlTree, start);
        LinkNode startNode = start1.linkNode;
        if (start > start1.pos) {
            int i = start1.pos;
            while (i < start) {
                i++;
                startNode = startNode.right;
            }
        } else {
            int i = start1.pos;
            while (i > start) {
                i--;
                startNode = startNode.left;
            }
        }
        return startNode;
    }

    public AVLTree.Node findNearestNode(AVLTree avlTree, Integer key) {
        List<AVLTree.Node> result = avlTree.findRange(key);
        if (result != null) {
            if (result.size() == 1) {
                return result.get(0);
            } else if (result.size() == 2) {
                AVLTree.Node first = result.get(0);
                AVLTree.Node second = result.get(1);
                if (Math.abs(key - first.pos) <= Math.abs(key - second.pos)) {
                    return first;
                } else {
                    return second;
                }

            }
        }
        return null;
    }

    static class LinkNode {

        LinkNode left;
        LinkNode right;
        Integer data;

        public LinkNode(Integer data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "LinkNode{" + "left=" + left + ", right=" + right + ", data=" + data + '}';
        }

    }

    static class AVLTree {

        public class Node {

            Integer pos;
            int ht;
            int size;
            Node left;
            Node right;
            LinkNode linkNode;
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

        public boolean contains(Integer key) {
            return get(key) != null;
        }

        public List<Node> findRange(Integer key) {

            return this.findRange(root, null, null, key);
        }

        public List<Node> findRange(Node x, Node left, Node right, Integer key) {
            if (x == null) {
                List<Node> result = new ArrayList<>();
                if (left != null) {
                    result.add(left);
                }
                if (right != null) {
                    result.add(right);
                }
                return result;

            }
            int cmp = key.compareTo(x.pos);
            if (cmp > 0) {
                return findRange(x.right, x, right, key);
            } else if (cmp < 0) {
                return findRange(x.left, left, x, key);

            } else {
                List<Node> result = new ArrayList<>();
                result.add(x);
                return result;
            }
        }

        public Node get(Integer key) {
            if (key == null) {
                return null;
            }
            Node x = get(root, key);
            return x;
        }

        private Node get(Node x, Integer key) {
            if (x == null) {
                return null;
            }
            int cmp = key.compareTo(x.pos);
            if (cmp > 0) {
                return get(x.right, key);
            } else if (cmp < 0) {
                return get(x.left, key);
            } else {
                return x;
            }

        }

        public void insert(Integer key, LinkNode linkNode) {
            root = insert(root, key, linkNode);
        }

        private Node insert(Node x, Integer key, LinkNode linkNode) {
            if (x == null) {
                Node node = new Node();
                node.ht = 0;
                node.size = 1;
                node.linkNode = linkNode;
                node.pos = key;
                return node;
            }
            int cmp = key.compareTo(x.pos);
            if (cmp > 0) {
                x.right = insert(x.right, key, linkNode);
            } else if (cmp < 0) {
                x.left = insert(x.left, key, linkNode);
            } else {
                x.linkNode = linkNode;

            }
            x.ht = 1 + Math.max(height(x.left), height(x.right));
            x.size = 1 + size(x.left) + size(x.right);
            return balance(x);
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

        public void delete(Integer val) {
            if (this.get(val) != null) {
                root = delete(root, val);
            }
        }

        private Node delete(Node x, Integer key) {

            int cmp = key.compareTo(x.pos);
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
