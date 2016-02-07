

package org.hawk.ds;

import org.hawk.exception.HawkException;
import java.util.List;
import org.hawk.logger.HawkLogger;
import org.hawk.module.script.IScript;
import org.hawk.module.script.ScriptUsage;
import org.hawk.module.script.enumeration.BuildModeEnum;
import org.hawk.module.script.enumeration.VarTypeEnum;
import org.hawk.module.script.type.DoubleDataType;
import org.hawk.module.script.type.Variable;
import java.util.Map;
import java.util.HashMap;
/**
 * This creates a binary tree from the input string
 * @version 1.0 15 Apr, 2010
 * @author msahu
 * @see Element
 * @see Expression
 * @see Variable
 * @see DoubleDataType
 * @see VarTypeEnum 
 */
public class BinaryTree {
    private static final HawkLogger logger = HawkLogger.getLogger(BinaryTree.class.getName());

    private Node root =null;

    private String inputExpression;

    private Expression infixExpression;

    public Expression getInfixExpression() {
        return infixExpression;
    }

    public void setInfixExpression(Expression infixExpression) {
        this.infixExpression = infixExpression;
    }

    
    

    public String getInputExpression() {
        return inputExpression;
    }

    public void setInputExpression(String inputExpression) {
        this.inputExpression = inputExpression;
    }


    public static void main(String args[]) throws Exception{

        String eqn = "abc[def[k]->def->y] + a*(b+d*e+(g->h->j))";
        String eqn1 = "a[b[j*k+l]] = (5*k)+(2/j)";
        String eqn2 ="enterpriseInfo->userAccountTO.enterpriseInfoTO.companyName = 	enterpriseInfo->userAccountTO.enterpriseInfoTO.companyName +\"\"+i";
        BinaryTree bt = new BinaryTree(eqn1);
        bt.createTree();
        bt.showTree();
    }

    private static final Map<String,Expression> infixCache = new HashMap<String,Expression>();

    private static final Map<String,List<Element>> postfixCache = new HashMap<String,List<Element>>();

    /**
     * Initializes a newly created {@code BinaryTree} object.
     */
    public BinaryTree( String str ) throws HawkException{
        
        this.inputExpression=str;

        if(!infixCache.containsKey(this.inputExpression)){
            this.infixExpression = new Expression(Element.parseElements(str));
            infixCache.put(this.inputExpression, this.infixExpression);
        }else{
            this.infixExpression = infixCache.get(this.inputExpression);
        }

        
    }

    /**
     * Creates the binary tree from the infix expression.
     * 
     */
    public  boolean createTree() throws HawkException{

         List<Element> postfix = null;
         if(!postfixCache.containsKey(this.inputExpression)){
             postfix = this.infixExpression.toPostfix();
         }else{
             postfix = postfixCache.get(this.inputExpression);
         }
         

         int i=postfix.size()-1;
         Node tmp= new Node(postfix.get(i));
         i--;
         Node toor= tmp;
         while(i>=0){

             Element element = postfix.get(i);

             if (element.isLeft(toor.getData())) {

                 if (toor.getLeft() == null) {

                     Node left = new Node(element);
                     toor.setLeft(left);
                     toor = tmp;
                     i--;
                 } else {

                     toor = toor.getLeft();

                 }
             } else {

                 if (toor.getRight() == null) {


                     Node right = new Node(element);
                     toor.setRight(right);
                     toor = tmp;
                     i--;
                 } else {

                     toor = toor.getRight();
                 }
             }
         }

         this.root = tmp;

         if(ScriptUsage.getInstance().getBuildMode() != BuildModeEnum.PERF){
            //System.out.println("     -------");
            //this.showTree();
            //System.out.println("     -------");
         }
         postfixCache.put(this.inputExpression, postfix);
         return true;
    }

    /**
     * This returns the output of the binary tree.
     * @return returns the output of the binary tree.
     */
    public IScript  getOutput(){
        return root.getValue();
    }

    /**
     * This represents the node of the binary tree.
     */
    public  static final class Node{

        /**
         * The data part of Node
         * @see Element
         */
        private Element data;
        /**
         * This represents left side of the node.
         */
        private Node left;
        /**
         * This represents right side of the node.
         */
        private Node right;

        public void setLeft(Node left) {
            this.left = left;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public void setData(Element data) {
            this.data = data;
        }

        public Element getData() {
            return data;
        }

        @Override
        public String toString() {
            return "" + data;
        }

        public Node(Element data) {
            this.data = data;
        }

        public boolean isCalculated() {
            return this.data.getValue() != null;
        }

        public Node getLeft() {
            return left;
        }

        public Node getRight() {
            return right;
        }

        public void setValue(IScript script) {

            this.getData().setValue(script);
        }

        public IScript getValue() {

            return this.data.getValue();
        }
    }

    public void showTree(){
        showTree(5);
    }

    public void showTree(final int length){
        showTree(root,length);
    }


    private  void showTree(final Node root,final int length){
        if(root==null){
           return;
        }
        this.showTree( root.getRight(),length+1);
        int i=0;
        
        while(i<length){
         System.out.print("  ");
         i++;
        }
        System.out.println(root.getData().getElement());

        this.showTree( root.getLeft(),length+1);
    }

    public IScript  calculate() throws HawkException {
        return this.calculate(this.root);
    }

    public IScript calculate(Node toor) throws HawkException{

        if(toor.getData().isOperator() && toor.getLeft()!= null && toor.getRight()!=null){

            toor.setValue
                (
                        HawkCalculator.calculate
                                        (
                                            this.calculate(toor.getLeft()),
                                            this.calculate(toor.getRight()),
                                            toor.getData()
                                        )
                );
        }

        return toor.getValue();
    }
}




