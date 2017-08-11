/*
 * This file is part of j-hawk
 * Copyright (C) BigBang<->BigCrunch Manoranjan Sahu, All Rights are left.
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

import org.commons.ds.exp.InfixExpression;
import java.util.List;
import org.commons.ds.exp.IObject;
import org.commons.ds.exp.DefaultExpressionServiceImpl;
import org.commons.ds.exp.IExpressionService;
import org.commons.ds.operator.OperatorFactory;
import org.commons.ds.tree.bintree.IBinTreeNode;
import org.commons.ds.tree.bintree.IBinaryTree;
import org.commons.ds.tree.bintree.PreOrderBinTreeIterator;
import org.commons.ds.tree.bintree.ShowBinTreePayload;

/**
 * This creates a binary tree from the input string
 *
 * @version 1.0 15 Apr, 2010
 * @author msahu
 *
 */
public class ElementBinaryTree implements IBinaryTree {

    private IExpressionService expressionService;

    private IBinTreeNode root = null;

    private String inputExpression;

    private InfixExpression infixExpression;

    public InfixExpression getInfixExpression() {
        return infixExpression;
    }

    public void setInfixExpression(InfixExpression infixExpression) {
        this.infixExpression = infixExpression;
    }

    public IExpressionService getExpressionService() {
        return expressionService;
    }

    @Override
    public IBinTreeNode getRoot() {
        return root;
    }

    public void setRoot(IBinTreeNode root) {
        this.root = root;
    }
        
    @Override
    public void setExpressionService(IExpressionService expressionService) {
        this.expressionService = expressionService;
    }

    public String getInputExpression() {
        return inputExpression;
    }

    @Override
    public void setInputExpression(String inputExpression) {
        this.inputExpression = inputExpression;
    }

    public static void main(String args[]) throws Exception {

        String eqn = "abc[def[k]->def->y] + a*(b+d*e+(g->h->j))";
        String eqn1 = "a*(b+c*d)+e";
        String eqn2 = "enterpriseInfo->userAccountTO.enterpriseInfoTO.companyName = 	enterpriseInfo->userAccountTO.enterpriseInfoTO.companyName +\"\"+i";
        ElementBinaryTree bt = new ElementBinaryTree(eqn1);
        bt.createTree();
        ShowBinTreePayload payload = new ShowBinTreePayload();
        //payload.setNode(bt.getRoot());
                
   //     PreOrderBinTreeIterator<ShowBinTreePayload> itr = new PreOrderBinTreeIterator<ShowBinTreePayload>(payload);
        //itr.iterate();
    }

    /**
     * Initializes a newly created {@code BinaryTree} object.
     *
     * @param str
     * @throws org.commons.string.ParserException
     */
    public ElementBinaryTree(String str) throws Exception {

        this(str, new DefaultExpressionServiceImpl());

    }

    /**
     * Initializes a newly created {@code BinaryTree} object.
     *
     * @param str
     * @param expressionService
     * @throws org.commons.string.ParserException
     */
    public ElementBinaryTree(String str, IExpressionService expressionService) throws Exception {

        this.inputExpression = str;
        this.expressionService = expressionService;
        this.infixExpression = expressionService.toInfix(str);

    }

    /**
     * Creates the binary tree from the infix expression.
     *
     * @return
     * @throws java.lang.Exception
     */
    @Override
    public boolean createTree() throws Exception {

        List<IElement> postfix = this.getExpressionService().toPostfix(this.getInfixExpression()).getExpression();

        int i = postfix.size() - 1;
        IBinTreeNode tmp = new ElementBinaryTreeNode( new ElementBinTreePayload(postfix.get(i)));
        i--;
        IBinTreeNode toor = tmp;
        while (i >= 0) {

            IElement element = postfix.get(i);

            if (element.isLeft( ((ElementBinTreePayload)toor.getPayload()).getData())) {

                if (toor.getLeft() == null) {

                    IBinTreeNode left = new ElementBinaryTreeNode(new ElementBinTreePayload(element));
                    toor.setLeft(left);
                    toor = tmp;
                    i--;
                } else {

                    toor = toor.getLeft();

                }
            } else {

                if (toor.getRight() == null) {

                    IBinTreeNode right = new ElementBinaryTreeNode(new ElementBinTreePayload(element));
                    toor.setRight(right);
                    toor = tmp;
                    i--;
                } else {

                    toor = toor.getRight();
                }
            }
        }

        this.root = tmp;

        return true;
    }

    /**
     * This returns the output of the binary tree.
     *
     * @return returns the output of the binary tree.
     */
    @Override
    public IObject getOutput() {
        return root.getValue();
    }

    @Override
    public void showTree() {
        showTree(5);
    }

    @Override
    public void showTree(final int length) {
        showTree(root, length);
    }

    private void showTree(final IBinTreeNode root, final int length) {
        if (root == null) {
            return;
        }
        this.showTree(root.getRight(), length + 1);
        int i = 0;

        while (i < length) {
            System.out.print("  ");
            i++;
        }
      //  System.out.println(root.getData().getElement());

        this.showTree(root.getLeft(), length + 1);
    }

    @Override
    public IObject calculate() throws Exception {
        return this.calculate(this.root);
    }
    @Override
    public IObject calculate(IBinTreeNode toor) throws Exception {
        boolean isAOperator = OperatorFactory.getInstance().isOperator(((ElementBinTreePayload)toor.getPayload()).getData().getElement());
        if (isAOperator && toor.getLeft() != null && toor.getRight() != null) {
            OperatorElement operatorElement = (OperatorElement) ((ElementBinTreePayload)toor.getPayload()).getData();
            toor.setValue(
                    operatorElement.calculate(
                            this.calculate(toor.getLeft()),
                            this.calculate(toor.getRight())
                    )
            );
        }

        return toor.getValue();
    }


 
    
}
