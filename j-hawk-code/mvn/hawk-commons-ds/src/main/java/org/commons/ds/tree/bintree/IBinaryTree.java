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

import org.commons.ds.exp.IExpressionService;
import org.commons.ds.exp.IObject;

/**
 *
 * @author manosahu
 */
public interface IBinaryTree {

    /**
     * BinaryTree implementor would require an expression service which
     * will eventually take care of all those infix->prefix->posfix conversations
     * so that BinaryTree would be isolated from these details.
     * If it is not passed , Implementors may wish to use the DefaultExpressionServie
     * which does not have any preProcess implementations.
     * @param expressionService 
     */
    public void setExpressionService(IExpressionService expressionService);

    /**
     * This ensures the BinaryTree has stored the inputExpression.
     * @param inputExpression 
     */
    public void setInputExpression(String inputExpression);

    /**
     * Implementors are enforced to have a getter for root as this is the handle to the 
     * entire binary tree.
     * @return 
     */
    public IBinTreeNode getRoot();

    /**
     * This returns the output of the binary tree.
     *
     * @return returns the output of the binary tree.
     */
    public IObject getOutput();

    /**
     * Utility method to show the tree on console.
     */
    public void showTree();

    /**
     * Utility method to show the tree on console with spacing choice.
     * @param length 
     */
    public void showTree(final int length);

    /**
     * 
     * This creates the tree.
     * implementors may chose to invoke this in 
     * the constructor
     * @return 
     * @throws java.lang.Exception
     */
    public boolean createTree() throws Exception;

    /**
     * this calculates the tree from input toor node
     * @param toor
     * @return
     * @throws Exception 
     */
    public IObject calculate(IBinTreeNode toor) throws Exception;

    /**
     * this calculates the tree from the root node.
     * @return
     * @throws Exception 
     */
    public IObject calculate() throws Exception;
    
    
   

}
