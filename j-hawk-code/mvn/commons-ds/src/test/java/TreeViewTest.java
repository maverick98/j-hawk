
import java.util.List;

import org.commons.ds.tree.bintreeng.IBinTreeView;
import org.commons.ds.tree.bintreeng.Node;
import org.commons.ds.tree.bintreeng.TopBinTreeViewImpl;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author manosahu
 */
public class TreeViewTest {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    @Test
    public void testTopView() throws Exception {

        IBinTreeView<Integer> topViewer = new TopBinTreeViewImpl<Integer>();

        Node<Integer> one = new Node<>();
        one.setKey(1);

        Node<Integer> two = new Node<>();
        two.setKey(2);

        one.setLeft(two);

        Node<Integer> three = new Node<>();
        three.setKey(3);

        two.setLeft(three);

        Node<Integer> four = new Node<>();
        four.setKey(4);

        two.setRight(four);

        Node<Integer> five = new Node<>();
        five.setKey(5);

        four.setRight(five);

        Node<Integer> nine = new Node<>();
        nine.setKey(9);

        five.setLeft(nine);

        Node<Integer> six = new Node<>();
        six.setKey(6);

        five.setRight(six);

        Node<Integer> seven = new Node<>();
        seven.setKey(7);

        six.setLeft(seven);

        Node<Integer> eight = new Node<>();
        eight.setKey(8);

        six.setRight(eight);

        List<Node<Integer>> topViews = topViewer.showView(one);
        for (Node<Integer> node : topViews) {

                System.out.print(node.getKey()+ ",");
        }

    }

    private static void sop(Object obj) {
        System.out.println(obj.toString());
    }
}
