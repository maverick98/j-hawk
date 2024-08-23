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
