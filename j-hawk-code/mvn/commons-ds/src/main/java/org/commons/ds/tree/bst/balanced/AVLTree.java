package org.commons.ds.tree.bst.balanced;

/**
 *
 * @author manosahu
 * @param <Key>
 * @param <Value>
 */
public class AVLTree<Key extends Comparable<Key>, Value> {

    private class Node {

        private Key key;
        private Value value;
        private Node left;
        private Node right;
        private int height;
    }

    private Node root;

    public int height() {
        return height(root);
    }

    private int height(Node node) {
        if (node == null) {
            return -1;
        }
        return node.height;

    }

    public Key min() {
        return min(root).key;
    }

    private Node min(Node x) {
        if (x.left == null) {
            return x;
        }
        return min(x.left);
    }

    public Key max() {
        return max(root).key;
    }

    private Node max(Node x) {
        if (x.right == null) {
            return x;
        }
        return max(x.right);
    }

    public void deleteMin() {
        deleteMin(root);
    }

    private Node deleteMin(Node x) {

        if (x.left == null) {
            return x.right;
        }
        x.left = deleteMin(x.left);
        x.height = 1 + Math.max(height(x.left), height(x.right));
        return balance(x);

    }

    public void deleteMax() {
        deleteMax(root);
    }

    private Node deleteMax(Node x) {

        if (x.right == null) {
            return x.left;
        }
        x.right = deleteMax(x.right);
        x.height = 1 + Math.max(height(x.left), height(x.right));
        return balance(x);

    }

    public boolean isEmpty() {
        return root == null;
    }

    public Value get(Key key) {
        if (key == null) {
            return null;
        }
        return get(root, key);
    }

    private Value get(Node node, Key key) {
        if (node == null) {
            return null;
        }
        Value value = null;
        int cmp = node.key.compareTo(key);
        if (cmp < 0) {
            value = get(node.left, key);
        } else if (cmp > 0) {
            value = get(node.right, key);
        } else {
            value = node.value;
        }
        return value;
    }

    public boolean contains(Key key) {
        return this.get(key) != null;
    }

    public void put(Key key, Value val) {

        this.put(root, key, val);
    }

    private Node put(Node x, Key key, Value value) {
        if (x == null) {
            Node node = new Node();
            node.key = key;
            node.value = value;
            node.height = 0;
            return node;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = put(x.left, key, value);
        } else if (cmp > 0) {
            x.right = put(x.right, key, value);
        } else {
            x.value = value;
            return x;
        }
        x.height = 1 + Math.max(height(x.left), height(x.right));
        return balance(x);

    }

    public void delete(Key key) {
        if (key == null) {
            return;
        }

        delete(root, key);

    }

    private Node delete(Node x, Key key) {
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = delete(x.left, key);
        } else if (cmp > 0) {
            x.right = delete(x.right, key);
        } else {
            if (x.left == null) {
                return x.right;
            }
            if (x.right == null) {
                return x.left;
            }

        }
        x.height = 1 + Math.max(height(x.left), height(x.right));
        return balance(x);
    }

    private int balanceFactor(Node x) {
        return this.height(x.left) - this.height(x.right);
    }

    private Node balance(Node x) {
        if (this.balanceFactor(x) < -1) {
            if (this.balanceFactor(x.right) > 0) {
                x.right = rotateLeft(x.right);
            }
            x = rotateLeft(x);

        } else if (this.balanceFactor(x) > 1) {
            if (this.balanceFactor(x.left) > 0) {
                x.left = rotateLeft(x.left);
            }
            x = rotateRight(x);
        }
        return x;
    }

    private Node rotateRight(Node x) {
        Node y = x.left;
        x.left = y.right;
        y.right = x;
        x.height = 1 + Math.max(height(x.left), height(x.right));
        y.height = 1 + Math.max(height(y.left), height(y.right));
        return y;
    }

    private Node rotateLeft(Node x) {
        Node y = x.right;
        x.right = y.left;
        y.left = x;
        x.height = 1 + Math.max(height(x.left), height(x.right));
        y.height = 1 + Math.max(height(y.left), height(y.right));
        return y;
    }

}
