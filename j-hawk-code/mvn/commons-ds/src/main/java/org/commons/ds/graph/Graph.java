/*
 * This file is part of j-hawk
 * CopyLeft (C) 2012-2013 Manoranjan Sahu, All Rights are left.
 * 

 */
package org.commons.ds.graph;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author manosahu
 */
public class Graph<T> {

    private List<Node<T>> nodes = new ArrayList<>();

    public Graph() {

    }

    public List<Node<T>> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node<T>> nodes) {
        this.nodes = nodes;
    }

    public boolean addNode(Node node) {
        return this.getNodes().add(node);
    }

    public void dfsFromStart(){
        dfs(nodes.get(0));
    }
    public void dfs(Node node) {
        if (node != null) {
            node.setNodeDiscoveryEnum(NodeDiscoveryEnum.DISCOVERED);
            List<Edge> edgeList = node.getAdjacentList();
            for(Edge edge : edgeList){
                Node destNode = edge.getDest();
                if(destNode.notYetDiscoverewd()){
                    this.dfs(destNode);
                }else{
                    System.out.println("Cycle exists");
                }
            }

        }
    }

}
