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

import java.util.Collection;
import java.util.List;
import org.commons.ds.exp.IObject;
import org.commons.ds.stack.Stack;

/**
 *
 * @author user
 */
public interface IElement {

    public int getPos();

    public void setPos(int pos);

    public int getPrecedence();

    public boolean hasHigherPrecedence(IElement otherOpr);

    public boolean isLeft(IElement herElement);

    public boolean isRight(IElement herElement);

    public IObject getValue();

    public void setValue(IObject value);

    public String getElement();

    public void setElement(String element);

    public int getShiftLength();

    public IElement parse(String input, int pos);

    public boolean shouldAdd();

    String getHorizontalParserSequence();

    public void onPostfixVisit(Stack<IElement> stack, List<IElement> postfix);

    public void onReverseVisit(List<IElement> result, int j);

    public boolean exists(Collection<IElement> elements);

    public boolean shouldEvaluate();

}
