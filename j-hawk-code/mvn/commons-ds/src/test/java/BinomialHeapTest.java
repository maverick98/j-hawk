/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.commons.ds.graph.Graph;
import org.commons.ds.graph.GraphFactory;
import org.commons.ds.heap.binomial.BinomialHeap;
import org.commons.ds.heap.binomial.BinomialNode;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import junit.framework.Assert.*;
import org.testng.Assert;

/**
 *
 * @author manosahu
 */
public class BinomialHeapTest {

    public BinomialHeapTest() {
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

    // @Test
    public void testLink() throws Exception {
        sop("inside testLink");
        BinomialHeap binHeap = new BinomialHeap();
        BinomialNode<Integer, Integer> node1 = new BinomialNode<>();
        node1.setKey(8);
        node1.setValue(8);

        BinomialNode<Integer, Integer> node2 = new BinomialNode<>();
        node2.setKey(9);
        node2.setValue(9);
        binHeap.link(node2, node1);
        Assert.assertNotNull(node2.getParent(), "node 2 's parent should not be null");
        Assert.assertNull(node1.getParent(), "node 1 's parent should  be null");
        Assert.assertEquals(node1.getDegree(), 1);
        Assert.assertEquals(node2.getDegree(), 0);
        Assert.assertEquals(node1.getChild(), node2);

        sop("finished testLink");

    }

    //@Test
    public void testMerge() throws Exception {
        sop("inside testMerge");
        BinomialHeap binHeap1 = new BinomialHeap();
        BinomialNode<Integer, Integer> node1 = new BinomialNode<>();
        node1.setKey(8);
        node1.setValue(8);
        binHeap1.setHead(node1);

        BinomialHeap binHeap2 = new BinomialHeap();
        BinomialNode<Integer, Integer> node2 = new BinomialNode<>();
        node2.setKey(9);
        node2.setValue(9);
        binHeap2.setHead(node2);

        BinomialHeap mergedHeap = binHeap1.merge(binHeap2);
        Assert.assertNotNull(mergedHeap.getHead(), "new head cant not null");
        Assert.assertNotNull(mergedHeap.getHead().getSibling(), "new head ' next cant not null");
        Assert.assertNull(mergedHeap.getHead().getSibling().getSibling(), "last one shud be null");

        BinomialHeap binHeap11 = new BinomialHeap();
        BinomialNode<Integer, Integer> node11 = new BinomialNode<>();
        node11.setKey(88);
        node11.setValue(88);
        binHeap11.setHead(node11);

        BinomialHeap binHeap22 = new BinomialHeap();
        BinomialNode<Integer, Integer> node22 = new BinomialNode<>();
        node22.setKey(99);
        node22.setValue(99);
        binHeap22.setHead(node22);

        BinomialHeap mergedHeap1 = binHeap11.merge(binHeap22);
        sop(mergedHeap1.toRootsUI());
        Assert.assertEquals(mergedHeap1.toRootsUI(), "88->99->");
        Assert.assertNotNull(mergedHeap1.getHead(), "new head cant not null");
        Assert.assertNotNull(mergedHeap1.getHead().getSibling(), "new head ' next cant not null");
        Assert.assertNull(mergedHeap1.getHead().getSibling().getSibling(), "last one shud be null");

        BinomialHeap binHeap = mergedHeap.merge(mergedHeap1);
        sop(binHeap.toRootsUI());
        Assert.assertEquals(binHeap.toRootsUI(), "8->9->88->99->");

        sop("finished testMerge");
    }

    //@Test
    public void testFindHeapMinimum() throws Exception {
        sop("inside testFindHeapMinimum");
        BinomialHeap binHeap1 = new BinomialHeap();
        BinomialNode<Integer, Integer> node1 = new BinomialNode<>();
        node1.setKey(8);
        node1.setValue(8);
        binHeap1.setHead(node1);

        BinomialHeap binHeap2 = new BinomialHeap();
        BinomialNode<Integer, Integer> node2 = new BinomialNode<>();
        node2.setKey(9);
        node2.setValue(9);
        binHeap2.setHead(node2);

        BinomialHeap mergedHeap = binHeap1.merge(binHeap2);

        BinomialHeap binHeap11 = new BinomialHeap();
        BinomialNode<Integer, Integer> node11 = new BinomialNode<>();
        node11.setKey(88);
        node11.setValue(88);
        binHeap11.setHead(node11);

        BinomialHeap binHeap22 = new BinomialHeap();
        BinomialNode<Integer, Integer> node22 = new BinomialNode<>();
        node22.setKey(99);
        node22.setValue(99);
        binHeap22.setHead(node22);

        BinomialHeap mergedHeap1 = binHeap11.merge(binHeap22);

        BinomialHeap binHeap = mergedHeap.merge(mergedHeap1);

        BinomialNode minNode = binHeap.findHeapMinimum();
        Assert.assertEquals(minNode.getKey(), 8);
        sop("finished testFindHeapMinimum");
    }

    @Test
    public void testUnion() throws Exception {
        BinomialHeap binHeap1 = new BinomialHeap();
        BinomialNode<Integer, Integer> node1 = new BinomialNode<>();
        node1.setKey(15);
        node1.setValue(15);
        binHeap1.setHead(node1);

        BinomialNode<Integer, Integer> node2 = new BinomialNode<>();
        node2.setKey(16);
        node2.setValue(16);
        BinomialHeap h1 = binHeap1.insert(node2);
        System.out.println(h1.toRootsUI());

        BinomialHeap binHeap11 = new BinomialHeap();
        BinomialNode<Integer, Integer> node11 = new BinomialNode<>();
        node11.setKey(31);
        node11.setValue(31);
        binHeap11.setHead(node11);

        BinomialNode<Integer, Integer> node21 = new BinomialNode<>();
        node21.setKey(1);
        node21.setValue(1);
        BinomialHeap h11 = binHeap11.insert(node21);

        BinomialHeap h2 = h1.union(h11);
        System.out.println(h2.toRootsUI());
        Assert.assertEquals(h2.toRootsUI(), "1->");

        sop(node1.getParent().toString());
        Assert.assertEquals(node1.getParent().getKey().intValue(), 1);
        sop(node1.getSibling().toString());
        Assert.assertEquals(node1.getSibling().getKey().intValue(), 31);

    }

    private static void sop(String message) {
        System.out.println(message);
    }

}
