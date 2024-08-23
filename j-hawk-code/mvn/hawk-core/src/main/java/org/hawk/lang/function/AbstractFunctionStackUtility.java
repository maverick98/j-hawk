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
package org.hawk.lang.function;

import org.commons.ds.stack.Stack;

import org.hawk.lang.IScript;
import org.hawk.lang.object.IObjectScript;

/**
 * Fix me.. find me a better name.. Shaksepere is wrong.. a lot can happen over name
 * @author Manoranjan Sahu
 */
public abstract class AbstractFunctionStackUtility {

    private static final ThreadLocal<Stack<FunctionInvocationInfo>> functionStack = new ThreadLocal<>();

    static {
        Stack<FunctionInvocationInfo> functionInvocationStack = new Stack<>();
        functionStack.set(functionInvocationStack);
    }
    

    public static ThreadLocal<Stack<FunctionInvocationInfo>> getFunctionStack() {
        return functionStack;
    }

    public IObjectScript pushExecutePop(FunctionInvocationInfo functionInvocationInfo) throws Exception{
        
        IObjectScript result;
        
        Stack<FunctionInvocationInfo> fStack = functionStack.get();

        if (fStack == null) {

            fStack = new Stack<>();

        }

        fStack.push(functionInvocationInfo);

        functionStack.set(fStack);

        result = (IObjectScript)this.execute();

        fStack.pop();
        
        return result;

    }
    
    public abstract IScript execute()  throws Exception;
    
    
}
