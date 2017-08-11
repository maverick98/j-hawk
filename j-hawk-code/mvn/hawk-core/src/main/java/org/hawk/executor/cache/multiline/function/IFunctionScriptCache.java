/*
 * This file is part of j-hawk
 * CopyLeft (C) BigBang<->BigCrunch Manoranjan Sahu, All Rights are left.
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
