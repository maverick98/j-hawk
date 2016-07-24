/*
 * This file is part of j-hawk
 * CopyLeft (C) 2012-2013 Manoranjan Sahu, All Rights are left.
 * 

 */
package org.commons.ds.graph;

import java.util.List;
import org.commons.implementor.IVisitable;
import org.commons.implementor.IVisitor;

/**
 *
 * @author manosahu
 */
public abstract class DFSVisitor implements IVisitor {

    public abstract void onVisit(Node node);

    public void visit(Node node) {
        if (node != null) {
            node.setNodeDiscoveryEnum(NodeDiscoveryEnum.DISCOVERED);
            this.onVisit(node);
            List<Edge> edgeList = node.getAdjacentList();
            for (Edge edge : edgeList) {
                Node destNode = edge.getDest();
                if (destNode.notYetDiscoverewd()) {
                    this.visit(destNode);
                } else {
                    System.out.println("Cycle exists");
                }
            }

        }
    }

    @Override
    public void visit(IVisitable visitable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
