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
public abstract class BinaryTreeIterator<K> implements IBinaryTreeIterator<K> {

    private IBinTreeNode treeNode;

    public BinaryTreeIterator(IBinTreeNode treeNode) {
        this.treeNode = treeNode;
    }

    @Override
    public IBinTreeNode getTreeNode() {
        return treeNode;
    }

    public void setTreeNode(IBinTreeNode treeNode) {
        this.treeNode = treeNode;
    }

   

   

    
    @Override
    public void iterate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  
}
