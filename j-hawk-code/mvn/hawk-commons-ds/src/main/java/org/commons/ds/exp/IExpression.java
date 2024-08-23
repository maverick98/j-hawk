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

import java.util.List;
import org.commons.ds.element.IElement;

/**
 *
 * @author manosahu
 */
public interface IExpression {

    public String getEquation();

    public void setEquation(String equation);

    public List<IElement> getExpression();

    public void setExpression(List<IElement> expression);
}
