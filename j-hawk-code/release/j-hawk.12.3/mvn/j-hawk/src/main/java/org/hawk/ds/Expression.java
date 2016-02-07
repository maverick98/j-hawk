

package org.hawk.ds;

import org.hawk.exception.HawkException;
import java.util.ArrayList;
import java.util.List;
import org.hawk.logger.HawkLogger;

/**
 * This represents the input
 * @version 1.0 15 Apr, 2010
 * @author msahu
 * @see Element
 */
public class Expression {

    private static final HawkLogger logger = HawkLogger.getLogger(Expression.class.getName());

    /**
     * This is the placeholder to store the input string in list of Element
     */
    private List<Element>  expression;

    

    public List<Element> getExpression() {
        return expression;
    }

    public void setExpression(List<Element> expression) {
        this.expression = expression;
    }
    

    /**
     * CTOR considering the type as well.
     * Allowed type  being INFIX,POSTFIX AND PREFIX.
     * @param elements
     * @param type
     */
    public Expression(final List<Element> elements){
        this.expression= elements;
        
    }


    public static void main(String args[]) throws Exception{

        String input = "(`exec fib(1.0)`+2)+`exec fib(1.0)`";
        List<Element> elements = Element.parseElements(input);
        Expression infix = new Expression(elements);

        //logger.info("op ="+infix);
        logger.info(infix.toPrefix().toString());


    }
    /**
     * This converts the input expression into PREFIX format.
     * @return returns the prefix format of the input expression.
     * @throws HawkException
     */
    public List<Element> toPrefix() throws HawkException{
        List<Element> tmpExp = null;
        try{

            tmpExp =Element.reverse(this.expression);
            Expression exp = new Expression(tmpExp);

            tmpExp  = exp.toPostfix();

            tmpExp = Element.reverse(exp.getExpression());
            
        }catch(Throwable th){

            throw new HawkException("Invalid ",th);
        }
        return tmpExp;
    }

    /**
     * This returns the the POSTFIX form of the input expression.
     * @return returns the POSTFIX form of the input expression.
     * @throws HawkException
     */
    public List<Element> toPostfix() throws HawkException{



        final int infix_len = this.expression.size();
        List<Element> postfix = new ArrayList<Element>();
        int i = 0;
        final Stack<Element> stack = new Stack<Element>();

        while (i < infix_len) {

            Element element = this.expression.get(i);

            if ( element.isClubber()  && element.isStartingClubber()) {

                stack.push(element);

            } else if (element.isOperator()){
                final Element top = stack.top();
                if (top != null) {
                    if (top.isOperator()){
                        if(top.hasHigherPrecedence(element)){
                            stack.pop();
                            postfix.add(top);
                            stack.push(element);
                            }else{
                                stack.push(element);
                            }
                    }else{
                            stack.push(element);
                    }
                }else{
                        stack.push(element);
                }
            } else if (element.isClubber()  &&  element.isClosingClubber()){

                Element top =  stack.top();

                while (!(top.isClubber()  && top.isStartingClubber())){
                        stack.pop();
                        postfix.add(top);
                        top =  stack.top();

                }
                stack.pop();

            } else {

                postfix.add(element);
            }

            i++;
        }

        Element top = stack.top();

        while (top != null) {
            stack.pop();
            postfix.add(top);
            top = stack.top();
        }

        if(checkForClubbers(postfix)){
            throw new HawkException("Invalid...");
        }
        

        return postfix;
    }

    /**
     * This searches for "(" and ")" in the input elements if one of them is present in the
     * input elements.
     * @param elements
     * @return
     */
    private static  boolean checkForClubbers(List<Element> elements){

        boolean status = false;
        if(elements == null || elements.isEmpty()){
            return false;
        }
        for(Element element : elements){
            if(element.isClubber()){
                status = true;
                break;
            }
        }

        return status;
    }

	
}




