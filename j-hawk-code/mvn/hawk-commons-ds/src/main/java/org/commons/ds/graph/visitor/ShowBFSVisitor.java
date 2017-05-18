

package org.commons.ds.graph.visitor;

import org.commons.ds.graph.Node;

/**
 *
 * @author manosahu
 */
public class ShowBFSVisitor extends AbstractBFSVisitor{

    @Override
    public void onVisit(Node node) {
        System.out.println(node);
    }

}
