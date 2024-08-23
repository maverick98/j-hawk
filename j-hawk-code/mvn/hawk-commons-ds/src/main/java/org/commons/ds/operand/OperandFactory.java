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
package org.commons.ds.operand;

import java.util.HashMap;
import java.util.Map;
import org.commons.ds.clubber.ClubberFactory;
import org.commons.ds.operator.OperatorFactory;

/**
 *
 * @author manosahu
 */
public class OperandFactory {

    private static final OperandFactory theInstance = new OperandFactory();

    private final Map<String, Boolean> operandCache = new HashMap<>();

    public static OperandFactory getInstance() {
        return theInstance;
    }

    /**
     * This checks if the element is operand
     *
     * @param element
     * @return returns true if the element is
     */

    public boolean isOperand(String element) {

        if (operandCache.containsKey(element)) {
            return operandCache.get(element);
        }
        boolean isAnOperator = OperatorFactory.getInstance().isOperator(element);
        boolean isClubber = ClubberFactory.getInstance().isClubber(element);
        boolean result = isClubber? false : !isAnOperator;

        operandCache.put(element, result);
        return result;
    }
}
