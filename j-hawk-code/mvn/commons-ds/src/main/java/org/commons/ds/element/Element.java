/*
 * This file is part of j-hawk
 * Copyright (C) 2012-2013 Manoranjan Sahu, All Rights are left.
 *

 *
 * END_HEADER
 */
package org.commons.ds.element;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.lang.model.element.VariableElement;
import org.apache.log4j.Logger;
import org.commons.ds.exp.IObject;
import org.commons.ds.operator.OperatorEnum;
import org.commons.ds.operator.OperatorFactory;
import org.commons.ds.stack.Stack;
import org.commons.string.StringUtil;

/**
 * This is a placeholder for perspective element in the input string.
 *
 * @version 1.0 14 Apr, 2010
 * @author msahu
 */
public abstract class Element implements Comparable, IElement {

    private static final Logger logger = Logger.getLogger(Element.class.getName());
    /**
     * RegEx to parse variable in the expression.
     */
    public static final Pattern ARRAY_EXISTENCE_PATTERN = Pattern.compile("([a-z|A-Z|<|>|(|)]{1,}[a-z|A-Z|<|>|(|)|\\d]*)\\s*(\\[\\s*(.*)\\s*\\])\\s*.*");
    /**
     * RegEx to find assignment operation present in the expression
     */
    private static final Pattern ASSIGNMENT_PATTERN = Pattern.compile("(?<!=)=(?!=)(.*)");

    private static Map<String, String> equationCache = new HashMap<String, String>();
    /**
     * Raw element data.
     */
    protected String element;
    /**
     * Starting position of raw element data.
     */
    protected int pos;

    protected IObject value = null;

    @Override
    public IObject getValue() {
        return value;
    }

    @Override
    public void setValue(IObject value) {
        this.value = value;
    }

    @Override
    public String getElement() {
        return this.element;
    }

    @Override
    public void setElement(String element) {
        this.element = element;
    }

    @Override
    public int getPos() {
        return pos;
    }

    @Override
    public void setPos(int pos) {
        this.pos = pos;
    }

    public Element() {

    }

    /**
     *
     * @param element element
     * @param pos starting position of element
     */
    public Element(final String element, final int pos) {
        this.element = element;
        this.pos = pos;

    }

    public static void reset() {
        equationCache = new HashMap<String, String>();
    }

    private static final Map<String, Boolean> operandCache = new HashMap<String, Boolean>();

    @Override
    public final int hashCode() {
        return pos;
    }

    @Override
    public String toString() {
        return "" + element;
    }

    @Override
    public final boolean equals(Object herElement) {
        Element myElement = this;
        return herElement == null || !(herElement instanceof Element)
                ? false
                : myElement.hashCode() == ((Element) herElement).hashCode();
    }

    @Override
    public int compareTo(Object herElement) {
        Element myElement = this;
        return herElement == null || !(herElement instanceof Element)
                ? -1
                : myElement.hashCode() - ((Element) herElement).hashCode();
    }

    /**
     * This checks if the element's position left to the other element
     *
     * @param herElement the input element which is to be checked with this
     * element for position
     * @return returns true if the element's position is left to the other
     * element
     */
    @Override
    public boolean isLeft(IElement herElement) {
        return this.hashCode() < herElement.hashCode();
    }

    /**
     * This checks if the element's position right to the other element
     *
     * @param herElement the input element which is to be checked with this
     * element for position
     * @return returns true if the element's position is right to the other
     * element
     */
    @Override
    public boolean isRight(IElement herElement) {
        return !this.isLeft(herElement);
    }

    public static void main(String args[]) throws Exception {

        //String equation = "`exec test (  `exec test(\"India\")`)` + xaaa";
        //String equation = "board[column-j] == row   || board[column-j] == row-j || board[column-j] == row+j";
        String equation = "a[1][2][3]";
        //a[](1)[](2)
        // String equation = "result[i] = result [j]";
        equation = preProcessEquation(equation);
        System.out.println(equation);
        /*
         List<IElement> elements = parseElementsOld(equation);
         for(IElement element : elements){
         System.out.println(element);
         }
         */

        List<IElement> elements1 = ElementFactory.getInstance().parseElements(equation);
        StringBuilder sb = new StringBuilder();
        for (IElement element : elements1) {
            sb.append(element);
        }
        System.out.println(sb.toString());

    }

    private static String preProcessEquation(String equation) {
        if (equationCache.containsKey(equation)) {
            return equationCache.get(equation);
        }
        String result;
        result = equation;
        Map<Integer, String> tmpArrayStartPositions = new HashMap<Integer, String>();
        Map<Integer, String> tmpArrayEndPositions = new HashMap<Integer, String>();
        do {
            Matcher mEqnMatcher = ARRAY_EXISTENCE_PATTERN.matcher(result);
            if (mEqnMatcher.find()) {

                String arrayBracketData = StringUtil.parseDelimeterData(result.substring(mEqnMatcher.start(2)), '[', ']');

                if (arrayBracketData != null && !arrayBracketData.isEmpty()) {
                    String preEqn = result.substring(0, mEqnMatcher.start(2));
                    String postEqn = result.substring(
                            result.indexOf(
                                    ']',
                                    mEqnMatcher.start(2) + arrayBracketData.length() + 1) + 1);
                    StringBuilder sb = new StringBuilder();
                    sb.append(preEqn);

                    sb.append("<");//temporary internal representation for []
                    sb.append(">");//temporary internal representation for []
                    int preEqnLen = preEqn.length();
                    tmpArrayStartPositions.put(preEqnLen, "<");
                    tmpArrayEndPositions.put(preEqnLen + 1, ">");
                    sb.append("(");
                    sb.append(arrayBracketData);
                    sb.append(")");
                    sb.append(postEqn);
                    result = sb.toString();

                } else {
                    break;
                }

            } else {
                break;
            }
        } while (true);
        result = replaceTmpArrayStartingBrackets(result, tmpArrayStartPositions);
        result = replaceTmpArrayEndBrackets(result, tmpArrayEndPositions);
        result = result.replaceAll("\\)\\s*`", ")}");
        result = result.replaceAll("`", "{");
        if (!result.startsWith("\"")) {//Hack fix me
            Matcher asgnmntMatcher = ASSIGNMENT_PATTERN.matcher(result);
            if (asgnmntMatcher.find()) {
                String pre = result.substring(0, asgnmntMatcher.start(1));
                String post = result.substring(asgnmntMatcher.start(1));
                result = pre + "(" + post + ")";

            }
        }
        equationCache.put(equation, result);
        return result;
    }

    private static String replaceTmpArrayStartingBrackets(String result, Map<Integer, String> tmpPositions) {
        return replaceTmpArrayBrackets(result, tmpPositions, '[');
    }

    private static String replaceTmpArrayEndBrackets(String result, Map<Integer, String> tmpPositions) {
        return replaceTmpArrayBrackets(result, tmpPositions, ']');

    }

    private static String replaceTmpArrayBrackets(String input, Map<Integer, String> tmpPositions, char bracket) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            if (tmpPositions.containsKey(i)) {
                sb.append(bracket);
            } else {
                sb.append(input.charAt(i));
            }
        }

        return sb.toString();
    }

    /**
     * This parses input equation into list of Element
     *
     * @param equation input equation to be parsed.
     * @return returns list of element
     * @throws org.commons.string.ParserException
     * @throws HawkException
     */
    public static List<IElement> parseElementsOld(String equation) {

        List<IElement> elements = new ArrayList<IElement>();
        equation = preProcessEquation(equation);

        int operatorCount = 0;
        int inputCount = 0;
        int pos = 0;
        for (int i = 0; i < equation.length();) {
            char ele = equation.charAt(i);
            String eleStr = ele + "";
            IElement element = null;
            String subStr = equation.substring(i);
            OperatorEnum operatorEnum;
            if ((operatorEnum = OperatorEnum.matches(subStr)) != null) {
                element = OperatorElement.create(operatorEnum.getOperator(), pos++);
                i += element.getElement().length();
                operatorCount++;
            } else if (eleStr.startsWith("(") || eleStr.startsWith(")")) {
                element = ClubberElement.create(eleStr, pos++);
                i += element.getElement().length();
            } else if (eleStr.trim().equals("")) {
                i++;
                continue;
            } else {

            //    element = new ExecElement().parse(subStr, pos);
                if (element != null) {
                    i += element.getElement().length() + 2;
                    inputCount++;
                } else {

                    element = new StringElement().parse(subStr, pos);
                    if (element != null) {
                        i += element.getElement().length() + 2;
                        pos++;
                        inputCount++;
                    } else {
         //               element = new VariableElement().parse(subStr, pos);

                        if (element != null) {
                            pos++;
                            i += element.getElement().length();
                            inputCount++;
                        } else {
                            element = new NumberElement().parse(subStr, pos);
                            if (element != null) {
                                pos++;
                                i += element.getElement().length();
                                inputCount++;
                            }
                        }
                    }
                }

            }
            elements.add(element);

        }

        return inputCount == operatorCount + 1
                ? elements
                : null;

    }

    /**
     * This returns the precedence of operators supported in hawk.
     *
     * @return
     * @throws HawkException
     */
    @Override
    public int getPrecedence() {

        boolean isAOperator = OperatorFactory.getInstance().isOperator(this.getElement());
        if (!isAOperator) {
            throw new Error("Not an operator");
        }
        return OperatorEnum.value(element).getPrecedence();

    }

    @Override
    public boolean hasHigherPrecedence(IElement otherOpr) {
        /*
         if (!this.isOperator() || !otherOpr.isOperator()) {
         throw new Error("Not an operator");
         }
         */
        boolean result;
        OperatorEnum opr1 = OperatorEnum.value(this.getElement());
        OperatorEnum opr2 = OperatorEnum.value(otherOpr.getElement());
        if (opr1.equals(OperatorEnum.ARRAYBRACKET) && opr2.equals(OperatorEnum.REFERENCE)) {
            result = this.getPos() < otherOpr.getPos();
        } else {
            result = this.getPrecedence() >= otherOpr.getPrecedence();
        }

        return result;
    }

    @Override
    public abstract int getShiftLength();

    @Override
    public abstract IElement parse(String input, int pos);

    @Override
    public boolean shouldAdd() {
        return true;
    }

    @Override
    public void onPostfixVisit(Stack<IElement> stack, List<IElement> postfix) {
        postfix.add(this);
    }

    public void onReverseVisit(List<IElement> result, int j) {
        result.add(j, this);
    }

    @Override
    public boolean exists(Collection<IElement> elements) {
        boolean status = false;
        if (elements == null || elements.isEmpty()) {
            status = false;
        } else {
            for (IElement ele : elements) {
                if (ele.getClass().equals(this.getClass())) {
                    status = true;
                    break;
                }
            }
        }

        return status;
    }

    @Override
    public boolean shouldEvaluate() {
        return false;
    }
}
