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
package org.hawk.lang.object;

import org.hawk.ds.exp.IHawkObject;

import org.hawk.lang.IScript;
import org.hawk.lang.type.Variable;

/**
 *
 * @author user
 */
public interface IObjectScript extends IScript,IHawkObject {

    Variable getVariable();

    void setVariable(Variable value);

    void setVariableValue(Variable value);

    Variable getVariableValue();

    String mangle();

    int length();

    boolean passByReference();

    
    
    boolean isProxy();
    
    IObjectScript getActualObjectScript();

    

   
    /**
     * This converts the hawk object into java object
     * @return
     * @throws java.lang.Exception
     */
    Object toJava() throws Exception;
}
