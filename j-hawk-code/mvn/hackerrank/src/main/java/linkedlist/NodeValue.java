package linkedlist;

/**
 *
 * @author manosahu
 */
public class NodeValue {

    static class Node {

        int data;
        Node next;

        public Node(int data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "Node{" + "data=" + data + '}';
        }

    }

    public static void main(String args[]) {

        NodeValue nv = new NodeValue();
        nv.test();
    }

    public void test() {
        Node node = new Node(1);
        node.next = new Node(3);
        node.next.next = new Node(5);

        node.next.next.next = new Node(6);
        System.out.println(GetNode(null, 0));
    }

    int GetNode(Node head, int n) {
        // This is a "method-only" submission. 
        // You only need to complete this method. 

        return getNode(head, n);

    }

    private static int cnt = -1;

    Integer getNode(Node head, int n) {
        if (head == null) {
            return null;
        }
        Integer result = -1;
        result = getNode(head.next, n);
        cnt++;
        if (cnt == n) {
            result =  head.data;
        }
        return result;
    }
}
