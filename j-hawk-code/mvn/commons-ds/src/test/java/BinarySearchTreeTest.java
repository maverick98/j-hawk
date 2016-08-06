/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.List;
import org.commons.ds.graph.Graph;
import org.commons.ds.graph.GraphFactory;
import org.commons.ds.graph.service.GraphServiceImpl;
import org.commons.ds.graph.service.IGraphService;
import org.commons.ds.tree.bst.BinaryNode;
import org.commons.ds.tree.bst.BinarySearchTree;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author manosahu
 */
public class BinarySearchTreeTest {

    IGraphService graphService = new GraphServiceImpl();

    public BinarySearchTreeTest() {
    }

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

    private Graph createGraphInternal(String file) {
        Graph<String> graph = GraphFactory.createGrpah(file);
        return graph;
    }

    /**
     *     6
     *
     * 3      688
     *
     *   355      68811
     *
     * @throws Exception
     */
    @Test
    public void testInorder() throws Exception {
        sop("inside testInorder");
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        BinaryNode<Integer> root = new BinaryNode<>();
        root.setKey(6);

        BinaryNode<Integer> left1 = new BinaryNode<>();
        left1.setKey(3);
        root.setLeft(left1);

        BinaryNode<Integer> right1 = new BinaryNode<>();
        right1.setKey(688);
        root.setRight(right1);

        BinaryNode<Integer> left11 = new BinaryNode<>();
        left11.setKey(355);
        right1.setLeft(left11);

        BinaryNode<Integer> right11 = new BinaryNode<>();
        right11.setKey(68811);
        right1.setRight(right11);

        bst.setRootNode(root);
        List<Integer> inorderList = new ArrayList<>();
        bst.inorder(root, inorderList);
        Assert.assertEquals(inorderList.get(0), new Integer(3));
        sop("---------");
        List<Integer> inorderList1 = new ArrayList<>();
        bst.inorderIterative(root, inorderList1);
        Assert.assertEquals(inorderList.size(),inorderList1.size());
        for(int i=0;i<inorderList.size();i++){
            Assert.assertEquals(inorderList.get(i),inorderList1.get(i));
        }
        sop("---------");
        List<Integer> preorderList = new ArrayList<>();
        bst.preorder(root, preorderList);
        sop("---------");
        List<Integer> postorderList = new ArrayList<>();
        bst.postorder(root, postorderList);
        Assert.assertEquals(postorderList.get(0), new Integer(3));

        sop("finished testInorder");

    }

    private static void sop(String message) {
        System.out.println(message);
    }

}
