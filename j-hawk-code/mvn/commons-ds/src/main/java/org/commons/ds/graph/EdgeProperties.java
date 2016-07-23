/*
 * This file is part of j-hawk
 * CopyLeft (C) 2012-2013 Manoranjan Sahu, All Rights are left.
 * 

 */
package org.commons.ds.graph;

/**
 *
 * @author manosahu
 */
public class EdgeProperties {

    private boolean directed;
    private EdgeTypeEnum edgeTypeEnum;

    public boolean isDirected() {
        return directed;
    }

    public void setDirected(boolean directed) {
        this.directed = directed;
    }

    public EdgeTypeEnum getEdgeTypeEnum() {
        return edgeTypeEnum;
    }

    public void setEdgeTypeEnum(EdgeTypeEnum edgeTypeEnum) {
        this.edgeTypeEnum = edgeTypeEnum;
    }

    @Override
    public String toString() {
        return "EdgeProperties{" + "directed=" + directed + ", edgeTypeEnum=" + edgeTypeEnum + '}';
    }
    
    

    
}
