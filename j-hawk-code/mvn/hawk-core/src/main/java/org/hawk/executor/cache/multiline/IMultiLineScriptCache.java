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

package org.hawk.executor.cache.multiline;

import org.hawk.executor.cache.IScriptCache;

/**
 *
 * @author Manoranjan Sahu
 */
public interface IMultiLineScriptCache extends IScriptCache{

    public boolean isInsideMultiLineScript(int i);
}
