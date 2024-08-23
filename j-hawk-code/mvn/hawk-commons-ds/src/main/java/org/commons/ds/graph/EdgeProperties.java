/*
 * This file is part of j-hawk
 *  
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
    private EdgeWeight edgeWeight;

    private boolean outward=true;

    public boolean isOutward() {
        return outward;
    }

    public void setOutward(boolean outward) {
        this.outward = outward;
    }
    
    
    public EdgeWeight getEdgeWeight() {
        return edgeWeight;
    }

    public void setEdgeWeight(EdgeWeight edgeWeight) {
        this.edgeWeight = edgeWeight;
    }

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
