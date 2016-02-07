

package org.hawk.ds;

import org.hawk.exception.HawkException;
import org.hawk.module.script.IScript;
import org.hawk.module.script.type.Variable;

/**
 * This computes hawk operation on the two input hawk variable.
 * @version 1.0 14 Apr, 2010
 * @author msahu
 * @see Variable
 * @see BinaryTree
 */
public class HawkCalculator {

    /**
     * This operates on two input variable.
     * @param leftElement left element
     * @param rightElement right element
     * @param operatorElement operator
     * @return returns hawk variable as result
     * @throws HawkException
     */
    public static IScript calculate(IScript leftElement, IScript rightElement, Element operatorElement)
            throws HawkException{

        if(leftElement == null || rightElement == null || operatorElement == null){
            throw new HawkException("Invalid operation");
        }
        String operator = operatorElement.getElement();
        IScript result = null;
        if(operator.equals(OperatorEnum.PLUS.getOperator())){

            result = leftElement.add(rightElement);
        }else if(operator.equals(OperatorEnum.MINUS.getOperator())){

            result = leftElement.subtract(rightElement);
        }else if(operator.equals(OperatorEnum.DIVIDE.getOperator())){

            result = leftElement.divide(rightElement);
        }else if(operator.equals(OperatorEnum.MULTIPLY.getOperator())){

            result = leftElement.multiply(rightElement);
        }else if(operator.equals(OperatorEnum.REMAINDER.getOperator())){

            result = leftElement.modulus(rightElement);
        }else if(operator.equals(OperatorEnum.LESSTHANEQUAL.getOperator())){//<=

             result = leftElement.lessThanEqualTo(rightElement);
        }else if(operator.equals(OperatorEnum.GREATERTHANEQUAL.getOperator())){//>=

             result = leftElement.greaterThanEqualTo(rightElement);
        }else if(operator.equals(OperatorEnum.EQUAL.getOperator())){//==

             result = leftElement.equalTo(rightElement);
        }else if(operator.equals(OperatorEnum.LESSER.getOperator())){

            result = leftElement.lessThan(rightElement);
        }else if(operator.equals(OperatorEnum.GREATER.getOperator())){

             result = leftElement.greaterThan(rightElement);
        }else if(operator.equals(OperatorEnum.OR.getOperator())){

           result = leftElement.or(rightElement);
           
        }else if(operator.equals(OperatorEnum.AND.getOperator())){

           result = leftElement.and(rightElement);
        }else if(operator.equals(OperatorEnum.REFERENCE.getOperator())){

           result = leftElement.refer(rightElement);
        }else if(operator.equals(OperatorEnum.ASSIGN.getOperator())){

           result = leftElement.assign(rightElement);
        }else if(operator.equals(OperatorEnum.ARRAYBRACKET.getOperator())){

           result = leftElement.arrayBracket(rightElement);
        }

        return result;
    }
    
   

}




