/*
 * This file is part of j-hawk
 * CopyLeft (C) BigBang<->BigCrunch Manoranjan Sahu, All Rights are left.
 *
 * 
 *
 * 1) If you modify a source file, make a comment in it containing your name and the date.
 * 2) If you distribute a modified version, you must do it under the GPL 2.
 * 3) Developers are encouraged but not required to notify the j-hawk maintainers at abeautifulmind98@gmail.com
 * when they make a useful addition. It would be nice if significant contributions could be merged into the main distribution.
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
