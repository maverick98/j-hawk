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


package org.commons.ds.exp;

/**
 *
 * @author manosahu
 */
public interface IExpressionService {

    public String preProcess(String equation) throws Exception;
    
    public InfixExpression toInfix(String equation) throws Exception;
    
    public PrefixExpression toPrefix(String equation) throws Exception;
    
    public PrefixExpression toPrefix(InfixExpression infixExpression) throws Exception;
    
    public PostfixExpression toPostfix(String equation) throws Exception;
    
    public PostfixExpression toPostfix(InfixExpression infixExpression) throws Exception;
}
