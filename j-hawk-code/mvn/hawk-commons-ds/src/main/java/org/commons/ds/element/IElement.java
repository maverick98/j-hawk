/*
 * This file is part of j-hawk
 * Copyright (C) 2012-2013 Manoranjan Sahu, All Rights are left.
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
