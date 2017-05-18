package org.commons.ds.tree.bst;

import java.util.List;
import org.commons.ds.stack.Stack;

public class BinarySearchTree<K extends Comparable> {

    private BinaryNode<K> rootNode;

    public BinaryNode<K> getRootNode() {
        return rootNode;
    }

    public void setRootNode(BinaryNode<K> rootNode) {
        this.rootNode = rootNode;
    }

    public void createMirror() {

        this.createMirrorInternal(this.getRootNode());
    }

    public void createMirrorInternal(BinaryNode<K> node) {
        if (node == null) {
            return;
        }
        this.createMirrorInternal(node.getLeft());
        BinaryNode<K> left = node.getLeft();
        BinaryNode<K> right = node.getRight();
        node.setLeft(right);
        node.setRight(left);
        sop(node.getKey().toString());
        this.createMirrorInternal(node.getLeft()); // because now left is right and right is left.
    }

    public boolean isMirror(BinarySearchTree<K> otherBST) {
        if (otherBST == null) {
            return false;
        }
        return this.isMirror(this.getRootNode(), otherBST.getRootNode());
    }

    public boolean isMirror(BinaryNode<K> firstNode, BinaryNode<K> secondNode) {
        if (firstNode == null && secondNode == null) {
            return true;
        }
        if (firstNode == null && secondNode != null) {
            return false;
        }
        if (firstNode != null && secondNode == null) {
            return false;
        }
        if (firstNode.getKey().compareTo(secondNode.getKey()) == 0) {
            boolean left = this.isMirror(firstNode.getLeft(), secondNode.getRight());
            boolean right = this.isMirror(firstNode.getRight(), secondNode.getLeft());
            return left && right;
        } else {
            return false;
        }
    }

    public void inorder(BinaryNode<K> node, List<K> inorderList) {
        if (node == null) {
            return;
        }
        inorder(node.getLeft(), inorderList);

        inorderList.add(node.getKey());
        inorder(node.getRight(), inorderList);
    }

    public void inorderIterative(BinaryNode<K> node, List<K> inorderList) {
        if (node == null) {
            return;
        }
        BinaryNode<K> curNode = node;
        Stack<BinaryNode<K>> stack = new Stack<>();
        do {
            do {
                if (curNode != null) {
                    stack.push(curNode);
                    curNode = curNode.getLeft();
                }
            } while (curNode != null);
            BinaryNode<K> node1 = stack.pop();

            inorderList.add(node1.getKey());
            curNode = node1.getRight();

        } while (curNode != null || !stack.isEmpty());
    }

    public void preorder(BinaryNode<K> node, List<K> preorderList) {
        if (node == null) {
            return;
        }

        preorderList.add(node.getKey());
        preorder(node.getLeft(), preorderList);
        preorder(node.getRight(), preorderList);
    }

    public void preorderIterative(BinaryNode<K> node, List<K> preorderList) {
        if (node == null) {
            return;
        }
        BinaryNode<K> curNode = node;
        Stack<BinaryNode<K>> stack = new Stack<>();
        do {

            do {
                if (curNode != null) {
                    stack.push(curNode);
                    preorderList.add(curNode.getKey());
                    curNode = curNode.getLeft();
                }
            } while (curNode != null);
            BinaryNode<K> node1 = stack.pop();

            curNode = node1.getRight();

        } while (curNode != null || !stack.isEmpty());
    }

    public void postorder(BinaryNode<K> node, List<K> postorderList) {
        if (node == null) {
            return;
        }

        postorder(node.getLeft(), postorderList);
        postorder(node.getRight(), postorderList);
        postorderList.add(node.getKey());

    }

    public void postorderIterative(BinaryNode<K> node, List<K> postorderList) {
        if (node == null) {
            return;
        }
        BinaryNode<K> curNode = node;
        Stack<BinaryNode<K>> stack = new Stack<>();
        do {
            if (curNode != null) {
                while (curNode != null) {
                    if (curNode.getRight() != null) {
                        stack.push(curNode.getRight());
                    }
                    stack.push(curNode);
                    curNode = curNode.getLeft();
                }
            } else {
                BinaryNode<K> poppedNode = stack.pop();
                BinaryNode<K> topNode = stack.top();
                if (poppedNode.getRight() != null && poppedNode.getRight() == topNode) {
                    stack.pop();
                    stack.push(poppedNode);
                    curNode = poppedNode.getRight();

                } else {
                    postorderList.add(poppedNode.getKey());
                    curNode = null;
                }

            }

        } while (!stack.isEmpty());
    }

    private static void sop(String msg) {
        System.out.println(msg);
    }
}
