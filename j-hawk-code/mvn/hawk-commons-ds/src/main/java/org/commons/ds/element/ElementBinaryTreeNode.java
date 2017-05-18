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

package org.commons.ds.element;

import org.commons.ds.IPayload;
import org.commons.ds.exp.IObject;
import org.commons.ds.tree.bintree.IBinTreeNode;

/**
 *
 * @author manosahu
 */
public class ElementBinaryTreeNode implements IBinTreeNode {

    /**
     * The data part of Node
     *
     * @see Element
     */
    private ElementBinTreePayload data;
    /**
     * This represents left side of the node.
     */
    private IBinTreeNode left;
    /**
     * This represents right side of the node.
     */
    private IBinTreeNode right;

    public ElementBinaryTreeNode(ElementBinTreePayload data) {
        this.data = data;
    }
    
    
    

    @Override
    public void setLeft(IBinTreeNode left) {
        this.left = left;
    }

    @Override
    public void setRight(IBinTreeNode right) {
        this.right = right;
    }

    @Override
    public boolean isCalculated() {
        return this.getPayload().isCalculated();
    }

    @Override
    public IBinTreeNode getLeft() {
        return this.left;
    }

    @Override
    public IBinTreeNode getRight() {
        return this.right;
    }

    @Override
    public void setValue(IObject script) {
        this.getPayload().setValue(script);
    }

    @Override
    public IObject getValue() {
        return this.getPayload().getValue();
    }

    @Override
    public void setPayload(IPayload payload) {
        this.data = (ElementBinTreePayload) payload;
    }

    @Override
    public IPayload getPayload() {
        return this.data;
    }

}
