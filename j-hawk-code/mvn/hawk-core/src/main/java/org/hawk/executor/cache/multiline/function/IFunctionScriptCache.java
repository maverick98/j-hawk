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
package org.hawk.executor.cache.multiline.function;

import java.util.List;
import java.util.Map;

import org.hawk.executor.cache.multiline.IMultiLineScriptCache;
import org.hawk.lang.function.FunctionNode;
import org.hawk.lang.function.FunctionScript;
import org.hawk.lang.object.IObjectScript;

/**
 *
 * @author user
 */
public interface IFunctionScriptCache extends IMultiLineScriptCache {

    public boolean isIterpretable();

    public FunctionScript getMainFunction();

    public FunctionNode getRootFunctionNode();

    public FunctionScript findFunctionScript(String functionName ,Map<Integer, IObjectScript> paramMap) throws Exception;
    
    public List<FunctionScript> findAllOverloadedFunctionScripts(String functionName) throws Exception;
    
    public FunctionScript findAnyFunctionScript(String functionName) throws Exception;
}
