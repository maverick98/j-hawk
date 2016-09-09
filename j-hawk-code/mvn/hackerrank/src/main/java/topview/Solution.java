package topview;



/**
 *
 * @author manosahu
 */
public class Solution {

    public void top_view(Node root) {
        IBinTreeView topViewer = new TopBinTreeViewImpl();
        java.util.List<Node> topViews = topViewer.showView(root);
        for (Node node : topViews) {

            System.out.print(node.data + " ");
        }
    }

    private static void sop(Object obj) {
        System.out.println(obj.toString());
    }

    private static void sopn(Object obj) {
        System.out.print(obj.toString());
    }

    public static void main(String args[]) {
        Node three = new Node();
        three.data = 3;

        Node five = new Node();
        five.data = 5;

        three.left = five;

        Node two = new Node();
        two.data = 2;

        three.right = two;

        Node one = new Node();
        one.data = 1;

        five.left = one;

        Node nine = new Node();
        nine.data = 9;

        one.right = nine;

        Node four = new Node();
        four.data = 4;

        five.right = four;

        Node six = new Node();
        six.data = 6;

        two.left = six;

        Node seven = new Node();
        seven.data = 7;

        two.right = seven;

        Node eight = new Node();
        eight.data = 8;

        seven.left = eight;

        new Solution().top_view(three);

        sop("\n\n\n");
    }
}
