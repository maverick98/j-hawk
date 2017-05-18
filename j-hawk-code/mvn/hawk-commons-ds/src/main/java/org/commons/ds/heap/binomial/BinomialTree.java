package org.commons.ds.heap.binomial;

/**
 * The Binomial Tree Bk is an ordered tree defined recursively. The binomial
 * tree B0 consists of single node. B1 contains two B0 nodes where one node
 * being parent and other being child. Therefor Bk would contain pow(2,k) nodes.
 * The height of the tree is k. there are exactly k!/(i! (k-i)! ) nodes at depth
 * i = 0 , 1, ... k. The root has highest degree
 *
 * @author manosahu
 * @param <K>
 * @param <V>
 */
public class BinomialTree<K extends Comparable, V> {

    private BinomialNode<K, V> root;

    public BinomialNode<K, V> getRoot() {
        return root;
    }

    public void setRoot(BinomialNode<K, V> root) {
        this.root = root;
    }

    public int getDegree() {
        return this.getRoot().getDegree();
    }
}
