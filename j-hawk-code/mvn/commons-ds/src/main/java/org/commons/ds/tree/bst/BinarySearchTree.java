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
            System.out.println(node1.getKey());
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

    public void postorder(BinaryNode<K> node, List<K> postorderList) {
        if (node == null) {
            return;
        }

        postorder(node.getLeft(), postorderList);
        postorder(node.getRight(), postorderList);
        postorderList.add(node.getKey());

    }

    private static void sop(String msg) {
        System.out.println(msg);
    }
}
