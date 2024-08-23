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

import java.util.Map;
import org.hawk.lang.object.IObjectScript;

/**
 *
 * @author Manoranjan Sahu
 */
public class FunctionInvocationInfo {

    private String encapsulatingFunctionName;
    private int lineNo;

    private String functionName;

    private Map<Integer, IObjectScript> paramMap;
    
    private FunctionScript functionScript;

    public FunctionScript getFunctionScript() {
        return functionScript;
    }

    public void setFunctionScript(FunctionScript functionScript) {
        this.functionScript = functionScript;
    }

    public FunctionInvocationInfo() {

    }

    public FunctionInvocationInfo(String name, int lineNo) {
        this.encapsulatingFunctionName = name;
        this.lineNo = lineNo;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public Map<Integer, IObjectScript> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<Integer, IObjectScript> paramMap) {
        this.paramMap = paramMap;
    }

    @Override
    public String toString() {
        return "at " + encapsulatingFunctionName + "(" + lineNo + ")";
    }

    public int getLineNo() {
        return lineNo;
    }

    public void setLineNo(int lineNo) {
        this.lineNo = lineNo;
    }

    public String getEncapsulatingFunctionName() {
        return encapsulatingFunctionName;
    }

    public void setEncapsulatingFunctionName(String name) {
        this.encapsulatingFunctionName = name;
    }
}
