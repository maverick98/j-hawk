/*
 * This file is part of j-hawk
 * CopyLeft (C) BigBang<->BigCrunch Manoranjan Sahu, All Rights are left.
 * 

 */
package org.commons.ds.graph;

import java.math.BigDecimal;

/**
 *
 * @author manosahu
 */
public class EdgeWeight {

    private BigDecimal weight;

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "EdgeWeight{" + "weight=" + weight + '}';
    }
    
    
}
