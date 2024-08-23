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

package org.hawk.executor.cache.singleline.globalvar;

import org.hawk.executor.cache.singleline.ISingleLineScriptCache;
import org.hawk.lang.object.IObjectScript;
import org.hawk.lang.type.Variable;

/**
 *
 * @author user
 */
public interface IGlobalVariableScriptCache extends ISingleLineScriptCache{
    
    public void setGlobalValue(Variable globalVar, IObjectScript globalValue);
    public IObjectScript getGlobalValue(String globalVar);
    public void unsetGlobalValue(String globalVar);
    public boolean isGlobalVarDeclared(Variable globalVar);
    
}
