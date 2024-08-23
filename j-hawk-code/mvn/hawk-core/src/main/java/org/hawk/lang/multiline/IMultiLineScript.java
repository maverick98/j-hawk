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
