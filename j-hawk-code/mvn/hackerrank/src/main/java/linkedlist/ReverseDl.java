package linkedlist;

import static linkedlist.Solution.Reverse0;

/**
 *
 * @author manosahu
 */
public class ReverseDl {

    static class Node {

        int data;
        Node next;
        Node prev;
    }

    public static void main(String args[]) {

        ReverseDl rdl = new ReverseDl();

    }

    Node Reverse(Node head) {

        return reverse(head);
    }
    private static Node newHead;

    private Node reverse(Node head) {
        if (head == null || head.next == null) {
            newHead = head;
            return head;
        }
        Node x = reverse(head.next);
        if (x != null) {
            x.next = head;
            head.next = null;
            head.prev = x;
        }
        return head;

    }
}
