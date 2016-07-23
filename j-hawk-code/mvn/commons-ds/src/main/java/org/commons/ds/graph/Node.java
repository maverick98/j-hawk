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
public class Node<T> {

    private T payload;

    private List<Edge> adjacentList = new ArrayList<>();

    private NodeDiscoveryEnum nodeDiscoveryEnum = NodeDiscoveryEnum.NOT_YET_DISCOVERED;

    public Node() {

    }

    public Node(T payload, List<Edge> adjacentList) {
        this.payload = payload;

        this.adjacentList = adjacentList;
    }

    public boolean notYetDiscoverewd() {
        return this.getNodeDiscoveryEnum() == NodeDiscoveryEnum.NOT_YET_DISCOVERED;
    }

    public boolean discovered() {
        return this.getNodeDiscoveryEnum() == NodeDiscoveryEnum.DISCOVERED;
    }

    public boolean explored() {
        return this.getNodeDiscoveryEnum() == NodeDiscoveryEnum.EXPLORED;
    }

    public NodeDiscoveryEnum getNodeDiscoveryEnum() {
        return nodeDiscoveryEnum;
    }

    public void setNodeDiscoveryEnum(NodeDiscoveryEnum nodeDiscoveryEnum) {
        this.nodeDiscoveryEnum = nodeDiscoveryEnum;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public List<Edge> getAdjacentList() {
        return adjacentList;
    }

    public void setAdjacentList(List<Edge> adjacentList) {
        this.adjacentList = adjacentList;
    }
    public boolean addEdge(Node node){
        Edge edge = new Edge();
        edge.setSrc(this);
        edge.setDest(node);
        EdgeProperties edgeProperties = new EdgeProperties();
        edgeProperties.setDirected(false);
        edge.setEdgeProperties(edgeProperties);
        this.getAdjacentList().add(edge);
        return true;
    }

    @Override
    public int hashCode() {
        return this.getPayload().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return this.getPayload().equals(obj);
    }

    @Override
    public String toString() {
        return "Node{" + "name=" + this.getPayload() + '}';
    }

    public boolean hasAdjacentList() {
        return !this.hasEmptyAdjacentList();
    }

    public boolean hasEmptyAdjacentList() {
        boolean noAdjacentList = false;
        noAdjacentList = (this.getAdjacentList() == null || this.getAdjacentList().isEmpty());
        return noAdjacentList;
    }

}
