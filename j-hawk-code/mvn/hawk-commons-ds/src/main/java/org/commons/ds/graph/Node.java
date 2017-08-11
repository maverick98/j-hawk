/*
 * This file is part of j-hawk
 * CopyLeft (C) BigBang<->BigCrunch Manoranjan Sahu, All Rights are left.
 * 

 */
package org.commons.ds.graph;

import java.util.ArrayList;
import java.util.List;
import org.commons.implementor.IVisitable;
import org.commons.implementor.IVisitor;

/**
 *
 * @author manosahu
 */
public class Node<T> implements IVisitable {

    private T payload;

    private List<Edge> adjacentList = new ArrayList<>();

    private int degree;

    private int arrivalTime;

    private int departureTime;

    private int level;

    private NodeDiscoveryEnum nodeDiscoveryEnum = NodeDiscoveryEnum.NOT_YET_DISCOVERED;

    public Node() {

    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(int departureTime) {
        this.departureTime = departureTime;
    }

    public boolean partOfThisEdge(Edge edge) {
        boolean result = false;
        if (!edge.getEdgeProperties().isDirected()) {
            // undirected case

            if (edge.getSrc().equals(this) || edge.getDest().equals(this)) {
                result = true;
            }

        } else {
            //Implement for digraphs later
        }
        return result;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public boolean isOddDegree() {
        return this.getDegree() % 2 != 0;
    }

    public boolean isEvenDegree() {
        return !this.isOddDegree();
    }

    public boolean incrementDegree() {
        this.setDegree(this.getDegree() + 1);
        return true;
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

    private EdgeProperties createUndirectedEdgeProps() {
        return this.createEdgeProps(false);
    }
    
    private EdgeProperties createDirectedEdgeProps() {
        return this.createEdgeProps(true);
    }
    private EdgeProperties createEdgeProps(boolean directed) {
        EdgeProperties edgeProperties = new EdgeProperties();
        edgeProperties.setDirected(directed);
        return edgeProperties;
    }

    public boolean addUndirectedEdge(Node node) {
        Edge edge = new Edge();
        edge.setSrc(this);
        edge.setDest(node);
        EdgeProperties edgeProperties = createUndirectedEdgeProps();
        edge.setEdgeProperties(edgeProperties);
        this.getAdjacentList().add(edge);
        this.incrementDegree();
        node.incrementDegree();
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
        return "Node{" + "payload=" + payload + ", degree=" + degree + "level=" + level + '}';
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
