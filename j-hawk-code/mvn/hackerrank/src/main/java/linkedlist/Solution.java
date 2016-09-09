package linkedlist;

/**
 *
 * @author manosahu
 */
public class Solution {

    static class Node {

        int data;
        Node next;

        public Node(int data) {
            this.data = data;
        }

        public Node() {

        }
    }

    static Node MergeLists(Node headA, Node headB) {
        // This is a "method-only" submission. 
        // You only need to complete this method 
        Node result = new Node();
        Node curNode = result;
        

        while (true) {

            if (headA == null  && headB == null) {
                break;
            }
            if (headA == null) {
                curNode.next = headB;
                break;
            }
            if (headB == null) {
                curNode.next = headA;
                break;
            } else {

                if (headA.data <= headB.data) {
                    curNode.next = headA;
                    headA = headA.next;

                } else {
                    curNode.next = headB;
                    headB = headB.next;
                }
                curNode = curNode.next;

            }

        }
        result = result.next;
        return result;

    }

    private static Node newHead;

    static Node Reverse0(Node head) {
        if (head == null || head.next == null) {
            newHead = head;
            return head;
        }
        Node x = Reverse0(head.next);
        if (x != null) {
            x.next = head;
            head.next = null;
        }
        return head;
    }

    static Node Reverse(Node head) {

        Reverse0(head);
        return newHead;

    }

    Node InsertNth(Node head, int data, int position) {
        // This is a "method-only" submission. 
        // You only need to complete this method. 
        Node node = new Node();
        node.data = data;
        node.next = null;
        if (head == null) {
            return node;
        }
        if (position == 0) {
            node.next = head;
            return node;
        }
        Node result = head;
        for (int i = 0; i <= position - 2; i++) {
            head = head.next;
        }
        Node tmp = head.next;
        node.next = tmp;
        head.next = node;

        return result;
    }

    public static void main(String args[]) {
        Solution solution = new Solution();

        Node headA;
        headA = new Node(12);
        headA.next = new Node(34);
        headA.next.next = new Node(45);
        headA.next.next.next = new Node(56);
        headA.next.next.next.next = new Node(67);

        Node headB;
        headB = new Node(13);
        headB.next = new Node(35);
        headB.next.next = new Node(46);
        headB.next.next.next = new Node(59);
        headB.next.next.next.next = new Node(678);
        headB = null;
        Node result = MergeLists(headA, headB);
        show(result);

    }

    private static void show(Node head) {
        while (head != null) {
            System.out.println(head.data);
            head = head.next;
        }
    }
}
