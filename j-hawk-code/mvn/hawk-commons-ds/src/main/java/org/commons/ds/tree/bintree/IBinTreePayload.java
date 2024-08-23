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

import org.commons.ds.IPayload;

/**
 *
 * @author manosahu
 */
public interface IBinTreePayload extends IPayload {

    public IBinTreePayload onVisit(IBinTreeNode treeNode);

    public IBinTreePayload beforeLeftVisit(IBinTreeNode treeNode);

    public IBinTreePayload beforeRightVisit(IBinTreeNode treeNode);

    public int getLength();

    public void setLength(int i);

}
