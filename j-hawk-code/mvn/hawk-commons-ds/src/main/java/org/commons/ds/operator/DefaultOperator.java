/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.commons.ds.operator;

import org.commons.ds.element.Element;
import org.commons.ds.exp.IObject;

/**
 *
 * @author manosahu
 */
public class DefaultOperator implements IOperator{
    public DefaultOperator(){} 
    /**
     * This holds the operator symbol
     *
     * @see Element
     */
    private String symbol;
    /**
     * This holds the precedence of the operator
     *
     * @see Element
     */
    private int precedence;
    
    
    private Operator operator;
  
    public DefaultOperator(Operator supportedOperator) {
        this.operator = supportedOperator;
        this.processOperator();
    }
    private void processOperator(){
        this.symbol = this.getOperator().getSymbol().trim();
        this.precedence = this.getOperator().getPrecedence();
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
        this.processOperator();
    }
    
    
    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public void setSymbol(String operator) {
        this.symbol = operator;
    }

    @Override
    public int getPrecedence() {
        return precedence;
    }

    @Override
    public void setPrecedence(int precedence) {
        this.precedence = precedence;
    }

    @Override
    public IObject operate(IObject leftObject, IObject rightObject) throws Exception {
        throw new UnsupportedOperationException(this+" has not yet implemented operate(leftObject, rightObject )"); 
    }

   
}
