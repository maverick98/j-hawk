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

import java.util.regex.Pattern;

/**
 *
 * @author manosahu
 */
public interface HawkDSConstant {

    /**
     * RegEx to parse variable in the expression.
     */
    Pattern ARRAY_EXISTENCE_PATTERN = Pattern.compile("([a-z|A-Z|<|>|(|)]{1,}[a-z|A-Z|<|>|(|)|\\d]*)\\s*(\\[\\s*(.*)\\s*\\])\\s*.*");
    /**
     * RegEx to find assignment operation present in the expression
     */
    Pattern ASSIGNMENT_PATTERN = Pattern.compile("(?<!=)=(?!=)(.*)");
    /**
     * RegEx to catch exec statements in the expression.
     */
    Pattern EXEC_PATTERN = Pattern.compile("\\{\\s*e?x?e?c?\\s*.*");

}
