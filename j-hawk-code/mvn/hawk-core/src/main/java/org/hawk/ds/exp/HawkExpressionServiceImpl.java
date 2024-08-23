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
 * 
 *
 * 
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hawk.ds.exp;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import org.commons.ds.exp.AbstractExpressionServiceImpl;
import org.commons.ds.exp.InfixExpression;
import org.commons.string.StringUtil;
import static org.hawk.ds.exp.HawkDSConstant.ARRAY_EXISTENCE_PATTERN;
import static org.hawk.ds.exp.HawkDSConstant.ASSIGNMENT_PATTERN;

/**
 *
 * @author manosahu
 */
public class HawkExpressionServiceImpl extends AbstractExpressionServiceImpl {

   
    private static Map<String, String> equationCache = new HashMap<>();

    @Override
    public String preProcess(String equation) throws Exception {
        if (equationCache.containsKey(equation)) {
            return equationCache.get(equation);
        }
        String result;
        result = equation;
        Map<Integer, String> tmpArrayStartPositions = new HashMap<>();
        Map<Integer, String> tmpArrayEndPositions = new HashMap<>();
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
    
     public static void main(String args[]) throws Exception {
       
        String equation = "`exec InningFetcher->equalOppositionName(\"Australia\")`";
        HawkExpressionServiceImpl hawk  = new HawkExpressionServiceImpl();
        InfixExpression infix =  hawk.toInfix(equation);
        
    }

}

