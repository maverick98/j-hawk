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
