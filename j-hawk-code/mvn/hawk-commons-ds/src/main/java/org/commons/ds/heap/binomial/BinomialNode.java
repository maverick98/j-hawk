package org.commons.ds.heap.binomial;

import java.util.Objects;

/**
 *
 * @author manosahu
 * @param <K>
 * @param <V>
 */
public class BinomialNode<K extends Comparable, V> {

    private BinomialNode<K, V> parent;
    private BinomialNode<K, V> child;
    private BinomialNode<K, V> leftSibling;
    private BinomialNode<K, V> sibling;
    private int degree;
    private K key;
    private V value;

    public BinomialNode() {

    }

    public BinomialNode(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public BinomialNode<K, V> getParent() {
        return parent;
    }

    public void setParent(BinomialNode<K, V> parent) {
        this.parent = parent;
    }

    public BinomialNode<K, V> getChild() {
        return child;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public void setChild(BinomialNode<K, V> child) {
        this.child = child;
    }

    public BinomialNode<K, V> getSibling() {
        return sibling;
    }

    public void setSibling(BinomialNode<K, V> sibling) {
        this.sibling = sibling;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public int incrementDegree() {
        this.setDegree(this.getDegree() + 1);
        return this.getDegree();
    }

    public int decrementDegree() {
        this.setDegree(this.getDegree() - 1);
        return this.getDegree();
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.key);
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
        final BinomialNode<?, ?> other = (BinomialNode<?, ?>) obj;
        if (!Objects.equals(this.key, other.key)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BinomialNode{" + "key=" + key + '}';
    }

    
}
