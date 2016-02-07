
package org.hawk.ds;
import java.util.Map;
import java.util.HashMap;
/**
 * This defines all the operation supported in hawk.
 * This requires the operator symbol along with its precedence.
 * @author msahu
 */
public enum OperatorEnum {

    PLUS("+",4),
    MINUS("-",4),
    REMAINDER("%",5),
    MULTIPLY("*",5),
    DIVIDE("/",5),
    GREATER(">",3),
    LESSER("<",3),
    GREATERTHANEQUAL(">=",3),
    LESSTHANEQUAL("<=",3),
    EQUAL("==",3),
    OR("||",2),
    AND("&&",2),
    ARRAYBRACKET("[]",6),
    REFERENCE("->",7),
    XOR("$",5),
    XNOR("#",5),
    ASSIGN("=",1);
    
    

    /**
     * This holds the operator symbol
     * @see Element
     */
    private String operator;

    /**
     * This holds the precedence of the operator
     * @see Element
     */
    private int precedence;

    private static final Map<String,OperatorEnum> operatorEnumMatchesCache = new HashMap<String,OperatorEnum>();

    private static final Map<String,OperatorEnum> operatorEnumExactMatchesCache = new HashMap<String,OperatorEnum>();

    /**
     * CTOR
     * @param operator operator symbol
     * @param precedence precedence of the operator
     */
    OperatorEnum(String operator,int precedence){
        this.operator = operator;
        this.precedence = precedence;
    }

    public String getOperator() {
        return operator;
    }

    public int getPrecedence() {
        return precedence;
    }

    public void setPrecedence(int precedence) {
        this.precedence = precedence;
    }

    

    

    /**
     * This creates <tt>OperatorEnum</tt> from operator raw string.
     * @param operatorStr raw string input
     * @return returns the <tt>OperatorEnum</tt> if it is valid otherwise null
     */
    public static OperatorEnum value(String operatorStr){
        if(operatorStr == null || operatorStr.isEmpty()){
            return null;
        }
        OperatorEnum resultEnum = null;
        for(OperatorEnum oprEnum:OperatorEnum.values()){
            if(oprEnum.operator.equals(operatorStr)){
                resultEnum = oprEnum;
                break;
            }
        }
        return resultEnum;
    }

    /**
     * This checks equality of two operators
     * @param other checks for equality against it
     * @return returns <tt>Boolean.TRUE</tt> if the operators are equal or <tt>Boolean.FALSE</tt>.
     * If the input is null it returns <tt>Boolean.FALSE</tt>
     */
    public boolean equals(OperatorEnum other){
        if(other == null){
            return false;
        }
        return this.getOperator().equals(other.getOperator());
    }

    /**
     * This returns  <tt>OperatorEnum</tt> if the input parameter matches with any of the known
     * operators.
     * @param substr
     * @return returns <tt>OperatorEnum</tt> if the input parameter matches with any of the known
     * operators.
     */
    public static OperatorEnum matchesExactly(String substr){
        if(operatorEnumExactMatchesCache.containsKey(substr)){
            return operatorEnumExactMatchesCache.get(substr);
        }
        OperatorEnum resultEnum = matches(substr);
        if(resultEnum != null){

            if(!resultEnum.getOperator().equals(substr)){
                resultEnum = null;
            }
        }

        operatorEnumExactMatchesCache.put(substr, resultEnum);
        return resultEnum;
    }

    /**
     * This returns  <tt>OperatorEnum</tt> if the input parameter matches with any of the known
     * operators.
     * @param substr 
     * @return returns <tt>OperatorEnum</tt> if the input parameter matches with any of the known
     * operators.
     */
    public static OperatorEnum matches(String substr){
        if(operatorEnumMatchesCache.containsKey(substr)){
            return operatorEnumMatchesCache.get(substr);
        }
        OperatorEnum resultEnum = null;
        for(OperatorEnum oprEnum:OperatorEnum.values()){
            if(substr.startsWith(oprEnum.operator)){
                resultEnum = oprEnum;
                if(oprEnum.equals(OperatorEnum.MINUS)){
                    if(substr.startsWith(OperatorEnum.REFERENCE.getOperator())){
                        resultEnum =OperatorEnum.REFERENCE;
                    }
                }else if(oprEnum.equals(OperatorEnum.LESSER)){
                    if(substr.startsWith(OperatorEnum.LESSTHANEQUAL.getOperator())){
                        resultEnum =OperatorEnum.LESSTHANEQUAL;
                    }
                }else if(oprEnum.equals(OperatorEnum.GREATER)){
                    if(substr.startsWith(OperatorEnum.GREATERTHANEQUAL.getOperator())){
                        resultEnum =OperatorEnum.GREATERTHANEQUAL;
                    }
                }else if(oprEnum.equals(OperatorEnum.ASSIGN)){
                    if(substr.startsWith(OperatorEnum.EQUAL.getOperator())){
                        resultEnum =OperatorEnum.EQUAL;
                    }
                }
                break;
            }
        }
        operatorEnumMatchesCache.put(substr, resultEnum);
        return resultEnum;
    }

    
}
