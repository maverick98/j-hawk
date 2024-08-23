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
 * @param <IPayload>
 */
public interface IBinaryTreeIterator< IPayload> {

    public IBinTreeNode getTreeNode();

   
    public void iterate();
}
