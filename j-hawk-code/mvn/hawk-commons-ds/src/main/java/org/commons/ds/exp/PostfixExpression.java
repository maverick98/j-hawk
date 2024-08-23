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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.commons.ds.exp;

import java.util.List;
import org.commons.ds.element.IElement;

/**
 *
 * @author manosahu
 */
public class PostfixExpression implements IExpression{
    private String equation;
    /**
     * This is the placeholder to store the input string in list of Element
     */
    private List<IElement> expression;

    public String getEquation() {
        return equation;
    }

    public void setEquation(String equation) {
        this.equation = equation;
    }

    public List<IElement> getExpression() {
        return expression;
    }

    public void setExpression(List<IElement> expression) {
        this.expression = expression;
    }
   
    

}
