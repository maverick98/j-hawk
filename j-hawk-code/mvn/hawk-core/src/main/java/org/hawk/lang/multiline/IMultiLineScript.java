/*
 * This file is part of j-hawk
 * CopyLeft (C) 2012-2013 Manoranjan Sahu, All Rights are left.
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

package org.hawk.lang.multiline;

import java.util.Map;

import org.hawk.lang.IScript;
import org.hawk.lang.singleline.pattern.LinePattern;
import org.hawk.lang.type.IDataType;

/**
 *
 * @author user
 */
public interface IMultiLineScript extends IScript{

    /**
     * This pushes local variables into a stack.
     * This is useful in case of function invocation.
     */
    void pushLocalVars();

    /**
     * This pops the local variable map out  from the stack.
     * @return a map containing local var and its value.
     */
    Map<String,IDataType> popLocalVars();
    
    String getStartDelim();
    
    String getEndDelim();
    
    LinePattern getStartPattern();
    
    LinePattern getEndPattern();
    
    boolean isLoop();
    
    void orderReturn();
    
    void acceptReturn();
    
    boolean returnOrdered();
    
    void applyBreak();
    
    void releaseBreak();
    
    boolean breakApplied();
    
    int createScript(MultiLineScript multiLineScript,int i) throws Exception;
}
