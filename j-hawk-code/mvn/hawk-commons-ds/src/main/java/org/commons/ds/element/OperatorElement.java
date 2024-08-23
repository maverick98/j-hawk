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
package org.commons.ds.element;


import java.util.List;
import org.common.di.ScanMe;
import org.commons.ds.exp.IObject;
import org.commons.ds.operator.IOperator;
import org.commons.ds.operator.OperatorEnum;
import org.commons.ds.operator.OperatorFactory;
import org.commons.ds.stack.Stack;

/**
 *
 * @author user
 */
@ScanMe(true)
public class OperatorElement extends Element {

    public OperatorElement() {
    }

    public OperatorElement(final String element, final int pos) {
        super(element, pos);
    }

    public static OperatorElement create(final String operator, final int pos) {
        OperatorElement operatorElement = new OperatorElement(operator, pos);
        return operatorElement;
    }

    @Override
    public int getShiftLength() {
        return this.getElement().length();
    }

    public IObject calculate(IObject leftScript, IObject rightScript) throws Exception {
        if (leftScript == null || rightScript == null) {
            throw new Exception("Invalid operation");
        }
        String operatorSymbol = this.getElement();

        IObject result = null;
        IOperator operator = OperatorFactory.getInstance().findBySymbol(operatorSymbol);
        result = operator.operate(leftScript, rightScript);
        
        return result;
    }

    @Override
    public IElement parse(String input, int pos) {
        IElement result = null;
        OperatorEnum operatorEnum;
        if ((operatorEnum = OperatorEnum.matches(input)) != null) {
            result = OperatorElement.create(operatorEnum.getOperator(), pos);
        }
        return result;
    }

    @Override
    public String getHorizontalParserSequence() {
        return "1";
    }

    @Override
    public void onPostfixVisit(Stack<IElement> stack, List<IElement> postfix) {
        final IElement top = stack.top();
        if (top != null) {
            boolean isTopAOperator = OperatorFactory.getInstance().isOperator(top.getElement());
            if (isTopAOperator) {
                if (top.hasHigherPrecedence(this)) {
                    stack.pop();
                    postfix.add(top);
                    stack.push(this);
                } else {
                    stack.push(this);
                }
            } else {
                stack.push(this);
            }
        } else {
            stack.push(this);
        }
    }

}
