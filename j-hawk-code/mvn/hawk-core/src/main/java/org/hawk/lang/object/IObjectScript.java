/*
 * This file is part of j-hawk
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
