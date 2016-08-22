package org.commons.ds.tree.bintreeng;

/**
 *
 * @author manosahu
 */
public class Node<K> {

    private K key;
    private Node<K> left;
    private Node<K> right;

    private Coordinate coordinate;

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public Node<K> getLeft() {
        return left;
    }

    public void setLeft(Node<K> left) {
        this.left = left;
    }

    public Node<K> getRight() {
        return right;
    }

    public void setRight(Node<K> right) {
        this.right = right;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    @Override
    public String toString() {
        return "Node{" + "key=" + key + ", coordinate=" + coordinate + '}';
    }

    
   
    

}
