package tree;

import java.util.LinkedList;

/**
 *
 * @author manosahu
 */
public class LevelOrder {

    static class Node {

        int data;
        Node left;
        Node right;

        @Override
        public String toString() {
            return "Node{" + "data=" + data + '}';
        }

        public Node(int data) {
            this.data = data;
        }
    }

    public static void main(String args[]) {
        new LevelOrder().test();
    }

    public void test() {
        Node root = new Node(3);
        root.left = new Node(5);
        root.right = new Node(2);

        root.left.left = new Node(1);
        root.left.right = new Node(4);

        root.right.left = new Node(6);
        level(root);

    }

    public void level(Node root) {
        Queue<Node> abc = new Queue<>();
        abc.add(root);
        abc.add(null);
        while (abc.hasItems()) {
            Node node = abc.remove();
            if (node != null) {
                System.out.print(node.data);

                if (node.left != null) {
                    abc.add(node.left);
                }
                if (node.right != null) {
                    abc.add(node.right);
                }
                if (shouldPrintHypen(abc)) {
                    System.out.print(" ");
                }else{
                    break;
                }
            } else {
                abc.add(null);
            }
        }
    }

    public boolean shouldPrintHypen(Queue<Node> abc) {
        if (abc.hasItems()) {
            if (abc.size() == 1) {
                return false;
            }
            return true;
        }
        return false;
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

}
