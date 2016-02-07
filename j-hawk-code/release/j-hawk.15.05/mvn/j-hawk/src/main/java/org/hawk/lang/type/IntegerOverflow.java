/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hawk.lang.type;

import org.commons.ds.operator.OperatorEnum;


/**
 *
 * @author user
 */
//@Component(INTEGEROVERFLOW)
//@Qualifier(DEFAULTQUALIFIER)
public class IntegerOverflow {

    public boolean detectOverflow(int a, int b, OperatorEnum operatorEnum) {
        boolean result = false;
        int c = 0;
        switch (operatorEnum) {
            case MULTIPLY:
                c = a*b;
                if (
                        ( ((a > 0 && b < 0) || (a < 0 && b > 0)) && c > 0) 
                        || 
                        ( ((a > 0 && b > 0) || (a < 0 && b < 0)) && c < 0)
                   ) {
                    result = true;
                }
                break;
            case PLUS:
    
                c = a + b;
                if (
                        ( ((a > 0 && b < 0) || (a < 0 && b > 0)) && c > 0) 
                        || 
                        ( ((a > 0 && b > 0) || (a < 0 && b < 0)) && c < 0)
                   ) {
                    result = true;
                }
                break;
                
            case MINUS:
                c = a-b;
                if (   (a > 0  && b < 0  && a > c)  || (a < 0  && b > 0  && a < c)){
                    result = true;
                }
                break;
            default:
               // throw new HawkInterpretationError("invalid operator");
                
        }


        return result;
    }

    
}
