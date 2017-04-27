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
