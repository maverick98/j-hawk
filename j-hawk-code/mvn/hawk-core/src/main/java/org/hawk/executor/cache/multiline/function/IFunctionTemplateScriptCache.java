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

import java.util.Set;

import org.hawk.executor.cache.multiline.IMultiLineScriptCache;
import org.hawk.lang.VerticalStrip;
import org.hawk.lang.function.FunctionNode;

/**
 *
 * @author user
 */
public interface IFunctionTemplateScriptCache extends IMultiLineScriptCache {

    public FunctionNode createRootFunctionNode(Set<VerticalStrip> all) throws Exception;
    
    public FunctionNode getRootFunctionNode();
}
