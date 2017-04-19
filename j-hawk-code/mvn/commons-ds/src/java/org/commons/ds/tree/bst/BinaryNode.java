package org.commons.ds.tree.bst;

import java.util.Objects;

/**
 *
 * @author manosahu
 */
public class BinaryNode<K extends Comparable> {

    private K key;
    private BinaryNode<K> left;
    private BinaryNode<K> right;

     public BinaryNode(){
         
     }
    public BinaryNode(BinaryNode<K> copy){
        this.key = copy.getKey();
        
    }
    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public BinaryNode<K> getLeft() {
        return left;
    }

    public void setLeft(BinaryNode<K> left) {
        this.left = left;
    }

    public BinaryNode<K> getRight() {
        return right;
    }

    public void setRight(BinaryNode<K> right) {
        this.right = right;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.key);
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
        final BinaryNode<?> other = (BinaryNode<?>) obj;
        if (!Objects.equals(this.key, other.key)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BinaryNode{" + "key=" + key + '}';
    }
    

}
