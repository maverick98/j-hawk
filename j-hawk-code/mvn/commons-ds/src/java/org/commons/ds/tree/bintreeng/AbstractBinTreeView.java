package org.commons.ds.tree.bintreeng;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author manosahu
 */
public abstract class AbstractBinTreeView<K> implements IBinTreeView<K> {

    @Override
    public abstract List<Node<K>> showView(final Node<K> rootNode);

    protected Map<Integer, Node<K>> createMap(final Node<K> node) {
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

    protected  boolean storeInMap(final Map<Integer, Node<K>> map, final Node<K> node){
         boolean rtn;
        if (map == null || node == null) {
            return false;
        }
        Node<K> curNode = map.get(this.getKeyCordinateBasis(node));
        if (curNode != null) {
            if (this.compareCoordinate(node.getCoordinate(), curNode.getCoordinate())) {
                map.remove(this.getKeyCordinateBasis(node));
                map.put(this.getKeyCordinateBasis(node), node);
                rtn = true;
            } else {
                rtn = false;
            }
        } else {
            map.put(this.getKeyCordinateBasis(node), node);
            rtn = true;
        }

        return rtn;
    }
    protected abstract Integer getKeyCordinateBasis(final Node<K> node);

    protected abstract boolean compareCoordinate(Coordinate firstCord, Coordinate secondCord);

}
