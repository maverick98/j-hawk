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

import org.commons.ds.INode;
import org.commons.ds.exp.IObject;

/**
 *
 * @author manosahu
 */
/**
 * This represents the node of the binary tree.
 */
public interface IBinTreeNode extends INode {

    public void setLeft(IBinTreeNode left);

    public void setRight(IBinTreeNode right);

    public boolean isCalculated();

    public IBinTreeNode getLeft();

    public IBinTreeNode getRight();

    public void setValue(IObject script);

    public IObject getValue();

  
}
