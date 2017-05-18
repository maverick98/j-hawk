package org.commons.ds.tree.bintreeng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author manosahu
 */
public abstract class AbstractVerticalView<K> extends AbstractBinTreeView<K> {

    @Override
    public List<Node<K>> showView(final Node<K> rootNode) {
        List<Node<K>> views = new ArrayList<>();
        Map<Integer, Node<K>> map = this.createMap(rootNode);
        for (Map.Entry<Integer, Node<K>> entry : map.entrySet()) {
            views.add(entry.getValue());
        }
        Collections.sort(views, new Comparator<Node<K>>() {
            @Override
            public int compare(Node<K> node1, Node<K> node2) {
                return node1.getCoordinate().getX().compareTo(node2.getCoordinate().getX());
            }
        });
        return views;
    }

    @Override
    protected Integer getKeyCordinateBasis(final Node<K> node) {
        return node.getCoordinate().getX();
    }

    @Override
    protected abstract boolean compareCoordinate(Coordinate firstCord, Coordinate secondCord);

}
