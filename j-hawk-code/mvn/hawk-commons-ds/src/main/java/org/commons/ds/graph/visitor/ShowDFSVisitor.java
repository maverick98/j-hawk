/*
 * This file is part of j-hawk
 * CopyLeft (C) BigBang<->BigCrunch Manoranjan Sahu, All Rights are left.
 * 

 */
package org.commons.ds.graph.visitor;

import org.commons.ds.graph.Node;

/**
 *
 * @author manosahu
 */
public class ShowDFSVisitor extends AbstractDFSVisitor {

    @Override
    public void onVisit(Node node) {
        System.out.println(node);
    }

}
