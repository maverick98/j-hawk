/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.commons.ds.operator;

/**
 *
 * @author manosahu
 */
public class Operator {
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

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }


    public int getPrecedence() {
        return precedence;
    }

    public void setPrecedence(int precedence) {
        this.precedence = precedence;
    }
    public Operator(){
        
    }
    public Operator(String symbol,int precedence){
        this.symbol = symbol;
        this.precedence = precedence;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.symbol != null ? this.symbol.hashCode() : 0);
        hash = 97 * hash + this.precedence;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Operator other = (Operator) obj;
        if ((this.symbol == null) ? (other.symbol != null) : !this.symbol.equals(other.symbol)) {
            return false;
        }
        if (this.precedence != other.precedence) {
            return false;
        }
        return true;
    }
    
    
}
