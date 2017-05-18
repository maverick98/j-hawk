/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.commons.ds.operator;

import org.commons.ds.exp.IObject;

/**
 *
 * @author manosahu
 */
public interface IOperator {

    /*
     public IOperator matchesExactly(String substr);

     public IOperator matches(String substr);
     */
    public Operator getOperator();

    public void setOperator(Operator operator);

    public String getSymbol();

    public void setSymbol(String opr);

    public int getPrecedence();

    public void setPrecedence(int precedence);

    public IObject operate(IObject leftObject, IObject rightObject) throws Exception;
}
