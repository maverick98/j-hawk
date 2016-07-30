/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import junit.framework.Assert;
import org.commons.ds.graph.Graph;
import org.commons.ds.graph.GraphFactory;
import org.commons.ds.graph.Node;
import org.commons.ds.graph.service.GraphServiceImpl;
import org.commons.ds.graph.service.IGraphService;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author manosahu
 */
public class GraphTest {

    IGraphService graphService = new GraphServiceImpl();

    public GraphTest() {
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

    //@Test
    public void createGraph() throws Exception {
        sop("inside createGraph");
        Graph graph = this.createGraphInternal("graph.txt");
        Assert.assertNotNull(graph);
        sop("finished createGraph");

    }

    //@Test
    public void testVisitDFS() throws Exception {
        sop("inside testVisitDFS");
        Graph graph = this.createGraphInternal("graph.txt");
        Assert.assertNotNull(graph);
        graphService.dfsVisit(graph);
        GraphFactory.reset();
        sop("finished testVisitDFS");

    }

    //@Test
    public void testEulerianPath() throws Exception {
         sop("inside testEulerianPath");
        Graph graph = this.createGraphInternal("eulieriangraph.txt");
        Assert.assertNotNull(graph);
        System.out.println("Graph is eulierian " + graphService.hasEulerianPath(graph));
        GraphFactory.reset();
        sop("finished testEulerianPath");

    }

    private static void sop(String message){
        System.out.println(message);
    }
    @Test
    public void testVisitBFS() throws Exception {
        sop("inside testVisitBFS");
        Graph graph = this.createGraphInternal("bfsgraph.txt");
        Assert.assertNotNull(graph);
        graphService.bfsVisit(graph);
        Node<String> lastNode = (Node<String>)graph.getNodes().get(graph.getNodes().size()-1);
        System.out.println(lastNode.getLevel());
        Assert.assertEquals(lastNode.getLevel(), 2);
        GraphFactory.reset();
        sop("finished testVisitBFS");

    }

}
