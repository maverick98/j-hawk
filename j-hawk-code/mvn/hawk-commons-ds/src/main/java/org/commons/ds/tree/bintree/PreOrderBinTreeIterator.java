/*
 * This file is part of j-hawk
 *  
 *
 * 
 *
 *  
 *  
 *  
 *  
 *
 *  
 * http://www.gnu.org/licenses/gpl.txt
 *
 * END_HEADER
 */
package org.commons.ds.tree.bintree;

/**
 *
 * @author manosahu
 * @param <K>
 */
public class PreOrderBinTreeIterator<K> extends BinaryTreeIterator<K> {

    public PreOrderBinTreeIterator(IBinTreeNode rootNode) {
        super(rootNode);
    }

    @Override
    public void iterate() {
        IBinTreeNode binTreeNode = this.getTreeNode();
        this.iterateInternal(binTreeNode, (IBinTreePayload) binTreeNode.getPayload());
    }

    private void iterateInternal(final IBinTreeNode root, IBinTreePayload payload) {
        if (root == null) {
            return;
        }
        this.iterateInternal(root.getRight(), payload.beforeRightVisit(root));
        payload.onVisit(root);

        this.iterateInternal(root.getLeft(), payload.beforeLeftVisit(root));
    }

}
