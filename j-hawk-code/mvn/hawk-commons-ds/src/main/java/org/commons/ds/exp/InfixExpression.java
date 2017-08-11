/*
 * This file is part of j-hawk
 * Copyright (C) BigBang<->BigCrunch Manoranjan Sahu, All Rights are left.
 *
 * 
 *
 * 1) If you modify a source file, make a comment in it containing your name and the date.
 * 2) If you distribute a modified version, you must do it under the GPL 2.
 * 3) Developers are encouraged but not required to notify the j-hawk maintainers at abeautifulmind98@gmail.com
 * when they make a useful addition. It would be nice if significant contributions could be merged into the main distribution.
 *
 * A full copy of the license can be found in gpl.txt or online at
 * http://www.gnu.org/licenses/gpl.txt
 *
 * END_HEADER
 */
package org.commons.ds.exp;

import java.util.List;
import org.commons.ds.element.Element;
import org.commons.ds.element.IElement;


/**
 * This represents the input
 *
 * @version 1.0 15 Apr, 2010
 * @author msahu
 * @see Element
 */
public class InfixExpression implements IExpression{

    
    
    private String equation;

    @Override
    public String getEquation() {
        return equation;
    }

    @Override
    public void setEquation(String equation) {
        this.equation = equation;
    }
    
    
    
    /**
     * This is the placeholder to store the input string in list of Element
     */
    private List<IElement> expression;

    @Override
    public List<IElement> getExpression() {
        return expression;
    }

    @Override
    public void setExpression(List<IElement> expression) {
        this.expression = expression;
    }

    public InfixExpression() {
    }

    public InfixExpression(String equation) {
        this.equation = equation;
    }
    
    /**
     * CTOR considering the type as well. Allowed type being INFIX,POSTFIX AND
     * PREFIX.
     *
     * @param elements
     * @param type
     */
    public InfixExpression(final List<IElement> elements) {
        this.expression = elements;

    }

  
  }
