package org.commons.ds.graph.visitor;

import java.util.List;
import org.commons.ds.graph.Edge;
import org.commons.ds.graph.Node;
import org.commons.ds.graph.NodeDiscoveryEnum;
import org.commons.ds.queue.Queue;
import org.commons.implementor.IVisitable;
import org.commons.implementor.IVisitor;

/**
 *
 * @author manosahu
 */
public abstract class AbstractBFSVisitor implements IVisitor {

    @Override
    public void visit(IVisitable visitable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public abstract void onVisit(Node node);

    public void visit(Node startNode) {
        if (startNode != null) {
            startNode.setLevel(0);
            Queue<Node> queue = new Queue<>();
            queue.enqueue(startNode);
            while (queue.hasItems()) {
                Node node = queue.dequeue();

                this.onVisit(node);
                List<Edge> edgeList = node.getAdjacentList();

                for (Edge edge : edgeList) {
                    if (edge.getDest().notYetDiscoverewd()) {
                        edge.getDest().setNodeDiscoveryEnum(NodeDiscoveryEnum.DISCOVERED);
                        edge.getDest().setLevel(node.getLevel()+1);
                        queue.enqueue(edge.getDest());
                    }
                }
                node.setNodeDiscoveryEnum(NodeDiscoveryEnum.EXPLORED);
            }

        }
    }
}
