/*
 * This file is part of j-hawk
 *  
 * 

 */
package org.commons.ds.graph;

import java.util.Objects;

/**
 *
 * @author manosahu
 */
public class Edge {

    private Node src;

    private Node dest;

    private EdgeProperties edgeProperties;

    public EdgeProperties getEdgeProperties() {
        return edgeProperties;
    }

    public void setEdgeProperties(EdgeProperties edgeProperties) {
        this.edgeProperties = edgeProperties;
    }

    public Node getSrc() {
        return src;
    }

    public void setSrc(Node src) {
        this.src = src;
    }

    public Node getDest() {
        return dest;
    }

    public void setDest(Node dest) {
        this.dest = dest;
    }

    @Override
    public String toString() {
        return this.getSrc() + "-" + this.getDest();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.src);
        hash = 89 * hash + Objects.hashCode(this.dest);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Edge other = (Edge) obj;
        if (!Objects.equals(this.src, other.src)) {
            return false;
        }
        if (!Objects.equals(this.dest, other.dest)) {
            return false;
        }
        return true;
    }

}
