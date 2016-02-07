/*
 * This file is part of j-hawk
 * Copyright (C) 2012-2013 Manoranjan Sahu, All Rights are left.
 *
 * 
 *
 * 1) If you modify a source file, make a comment in it containing your name and the date.
 * 2) If you distribute a modified version, you must do it under the GPL 2.
 * 3) Developers are encouraged but not required to notify the j-hawk maintainers at abeautifulmind98@gmail.com
 * when they make a useful addition. It would be nice if significant contributions could be merged into the main distribution.
 *
 * A full copy of the license can be found in gpl.txt or online at
 * http://www.gnu.org/licenses/gpl.txt
 *
 * END_HEADER
 */
package org.commons.ds.tree.bintree;

/**
 *
 * @author manosahu
 * @param <IPayload>
 */
public class PreOrderBinTreeIterator<IPayload> extends BinaryTreeIterator<IPayload> {

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
