/*
 * This file is part of j-hawk
 * CopyLeft (C) 2012-2013 Manoranjan Sahu, All Rights are left.
 * 

 */
package org.commons.ds.graph;

import java.util.List;

/**
 *
 * @author manosahu
 */
public class Node<T> {

    private T payload;

    private String name;

    private List<Edge> adjacentList;

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Edge> getAdjacentList() {
        return adjacentList;
    }

    public void setAdjacentList(List<Edge> adjacentList) {
        this.adjacentList = adjacentList;
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
        return "Node{" + "name=" + name + '}';
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
