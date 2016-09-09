
import java.util.List;
import org.commons.ds.tree.bintreeng.DownBinTreeViewImpl;

import org.commons.ds.tree.bintreeng.IBinTreeView;
import org.commons.ds.tree.bintreeng.LeftSideBinTreeViewImpl;
import org.commons.ds.tree.bintreeng.Node;
import org.commons.ds.tree.bintreeng.RightSideBinTreeViewImpl;
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

        Node<Integer> three = new Node<>();
        three.setKey(3);

        Node<Integer> five = new Node<>();
        five.setKey(5);

        three.setLeft(five);

        Node<Integer> two = new Node<>();
        two.setKey(2);

        three.setRight(two);

        Node<Integer> one = new Node<>();
        one.setKey(1);

        five.setLeft(one);

        Node<Integer> nine = new Node<>();
        nine.setKey(9);

        one.setRight(nine);

        Node<Integer> four = new Node<>();
        four.setKey(4);

        five.setRight(four);

        Node<Integer> six = new Node<>();
        six.setKey(6);

        two.setLeft(six);

        Node<Integer> seven = new Node<>();
        seven.setKey(7);

        two.setRight(seven);

        Node<Integer> eight = new Node<>();
        eight.setKey(8);

        seven.setLeft(eight);

        IBinTreeView<Integer> topViewer = new TopBinTreeViewImpl<Integer>();
        List<Node<Integer>> topViews = topViewer.showView(three);
        for (Node<Integer> node : topViews) {

            sopn(node.getKey() + ",");
        }
        sop("\n\n\n");

        IBinTreeView<Integer> downViewer = new DownBinTreeViewImpl<Integer>();
        List<Node<Integer>> downViews = downViewer.showView(one);
        for (Node<Integer> node : downViews) {

            sopn(node.getKey() + ",");
        }

         sop("\n\n\n");
        IBinTreeView<Integer> leftViewer = new LeftSideBinTreeViewImpl<Integer>();
        List<Node<Integer>> leftViews = leftViewer.showView(one);
        for (Node<Integer> node : leftViews) {

            sopn(node.getKey() + ",");
        }
        sop("\n\n\n");

        IBinTreeView<Integer> rightViewer = new RightSideBinTreeViewImpl<Integer>();
        List<Node<Integer>> rightViews = rightViewer.showView(one);
        for (Node<Integer> node : rightViews) {

            sopn(node.getKey() + ",");
        }
        
          sop("\n\n\n");
    }

    private static void sop(Object obj) {
        System.out.println(obj.toString());
    }

    private static void sopn(Object obj) {
        System.out.print(obj.toString());
    }
}
