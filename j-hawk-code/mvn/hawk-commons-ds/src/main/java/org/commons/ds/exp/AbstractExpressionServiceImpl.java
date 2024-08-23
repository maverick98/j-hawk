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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.commons.ds.element.ClubberElement;
import org.commons.ds.element.ElementFactory;
import org.commons.ds.element.ElementUtil;
import org.commons.ds.element.IElement;
import org.commons.ds.stack.Stack;

/**
 *
 * @author manosahu
 */
public abstract class AbstractExpressionServiceImpl implements IExpressionService {

    private final Map<String, InfixExpression> infixCache = new HashMap<>();

    private final Map<String, PostfixExpression> postfixCache = new HashMap<>();

    private final Map<String, PrefixExpression> prefixCache = new HashMap<>();

    @Override
    public InfixExpression toInfix(String inputExpression) throws Exception {
        if (infixCache.containsKey(inputExpression)) {

            return infixCache.get(inputExpression);
        }
        String preProcessedEqn = this.preProcess(inputExpression);
        InfixExpression infixExpression = new InfixExpression(preProcessedEqn);
        List<IElement> elements = ElementFactory.getInstance().parseElements(infixExpression.getEquation());
        infixExpression.setExpression(elements);
        infixCache.put(inputExpression, infixExpression);
        return infixExpression;
    }

    @Override
    public PrefixExpression toPrefix(String inputExpression) throws Exception {
        if (prefixCache.containsKey(inputExpression)) {

            return prefixCache.get(inputExpression);
        }
        InfixExpression infixExpression = this.toInfix(inputExpression);
        return this.toPrefix(infixExpression);
    }

    @Override
    public PrefixExpression toPrefix(InfixExpression infixExpression) throws Exception {
         if (prefixCache.containsKey(infixExpression.getEquation())) {
            return prefixCache.get(infixExpression.getEquation());
        }
        List<IElement> tmpExp;
        PrefixExpression prefixExpression = new PrefixExpression();
        prefixExpression.setEquation(infixExpression.getEquation());

        tmpExp = ElementUtil.reverse(infixExpression.getExpression());
        PostfixExpression postfixExpression = this.toPostfix(infixExpression.getEquation());

        if (tmpExp != null) {
            tmpExp = ElementUtil.reverse(postfixExpression.getExpression());
        } else {
            throw new Exception("could not calculate prefix");
        }
        prefixExpression.setExpression(tmpExp);
        return prefixExpression;
    }

    @Override
    public PostfixExpression toPostfix(String inputExpression) throws Exception {
        if (postfixCache.containsKey(inputExpression)) {
            return postfixCache.get(inputExpression);
        }
        InfixExpression infixExpression = this.toInfix(inputExpression);

        PostfixExpression postfixExpression =  this.toPostfix(infixExpression);
        postfixCache.put(inputExpression, postfixExpression);
        return postfixExpression;
    }

    @Override
    public PostfixExpression toPostfix(InfixExpression infixExpression) throws Exception {
        if (postfixCache.containsKey(infixExpression.getEquation())) {
            return postfixCache.get(infixExpression.getEquation());
        }
        final int infix_len = infixExpression.getExpression().size();

        PostfixExpression postfixExpression = new PostfixExpression();
        postfixExpression.setEquation(infixExpression.getEquation());
        List<IElement> postfix = new ArrayList<>();
        int i = 0;
        final Stack<IElement> stack = new Stack<>();

        while (i < infix_len) {

            IElement element = infixExpression.getExpression().get(i);

            element.onPostfixVisit(stack, postfix);
            i++;
        }

        IElement top = stack.top();

        while (top != null) {
            stack.pop();
            postfix.add(top);
            top = stack.top();
        }

        if (checkForClubbers(postfix)) {
            throw new Exception("Invalid...");
        }
        postfixExpression.setExpression(postfix);
        return postfixExpression;
    }

    @Override
    public abstract String preProcess(String equation) throws Exception;

    /**
     * This searches for "(" and ")" in the input elements if one of them is
     * present in the input elements.
     *
     * @param elements
     * @return
     */
    private boolean checkForClubbers(List<IElement> elements) {

        ClubberElement clubberElement = new ClubberElement();
        return clubberElement.exists(elements);

    }

}
