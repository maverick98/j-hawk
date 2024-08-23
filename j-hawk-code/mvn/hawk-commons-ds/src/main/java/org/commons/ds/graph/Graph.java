/*
 * This file is part of j-hawk
 *  
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

  
   
    
   

}
