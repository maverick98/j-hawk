package org.commons.ds.tree.bintreeng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author manosahu
 */
public class TopBinTreeViewImpl<K> implements IBinTreeView<K> {

    @Override
    public List<Node<K>> showView(final Node<K> rootNode) {
        List<Node<K>> views = new ArrayList<>();
        Map<Integer, Node<K>> map = this.createMapInternal(rootNode);
        for (Entry<Integer, Node<K>> entry : map.entrySet()) {
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

    private Map<Integer, Node<K>> createMapInternal(final Node<K> node) {
        Map<Integer, Node<K>> map = new LinkedHashMap<>();
        Coordinate rootCord = new Coordinate(0, 0);
        preOrder(node, rootCord, map);

        return map;
    }

    private void preOrder(final Node<K> node, final Coordinate cord, final Map<Integer, Node<K>> map) {

        if (node == null) {
            return;
        }
        node.setCoordinate(cord);
        boolean stored = this.storeInMap(map, node);
        if (stored) {
            // sop(node + " is stored ");

        } else {
            //  sop(node + " is NOT  stored ");
        }

        preOrder(node.getLeft(), cord.getLeft(), map);
        preOrder(node.getRight(), cord.getRight(), map);

    }

    private static void sop(Object obj) {
        System.out.println(obj.toString());
    }

    private boolean storeInMap(final Map<Integer, Node<K>> map, final Node<K> node) {
        boolean rtn;
        if (map == null || node == null) {
            return false;
        }
        Node<K> curNode = map.get(node.getCoordinate().getX());
        if (curNode != null) {
            if (node.getCoordinate().isVerticallyAbove(curNode.getCoordinate())) {
                map.remove(node.getCoordinate().getX());
                map.put(node.getCoordinate().getX(), node);
                rtn = true;
            } else {
                rtn = false;
            }
        } else {
            map.put(node.getCoordinate().getX(), node);
            rtn = true;
        }

        return rtn;
    }

}
