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

import java.util.LinkedHashMap;
import java.util.Map;
import org.common.di.AppContainer;

import org.hawk.executor.cache.multiline.IMultiLineScriptCache;
import org.hawk.executor.cache.multiline.MultiLineScriptCacheFactory;
import org.hawk.executor.cache.singleline.SingleLineScriptCache;
import org.hawk.lang.comment.SingleLineCommentScript;
import org.hawk.lang.enumeration.VarTypeEnum;
import org.hawk.lang.object.IObjectScript;
import org.hawk.lang.object.VariableDeclScript;
import org.hawk.lang.object.XMLStructIncludeScript;
import org.hawk.lang.type.Variable;
import org.hawk.lang.util.AliasScript;
import org.hawk.pattern.PatternMatcher;
 import org.commons.reflection.Create;
import org.common.di.ScanMe;

/**
 *
 * @author Manoranjan Sahu
 */
@ScanMe(true)
   
   
public class GlobalVariableScriptCache extends SingleLineScriptCache implements IGlobalVariableScriptCache{

       
    private IMultiLineScriptCache functionScriptCache;
    /**
     * Global map containing global var and its value
     *
     * @see VariableDeclScript
     */
    protected Map<Variable, IObjectScript> globalVariableTable = new LinkedHashMap<Variable, IObjectScript>();

    /**
     * Setter for global variable
     *
     * @param globalVar
     * @param globalValue
     */
    @Override
    public void setGlobalValue(Variable globalVar, IObjectScript globalValue) {

        this.globalVariableTable.put(globalVar, globalValue);
    }
     
    /**
     * Getter for global variable
     * @param globalVar
     * @return getter for global variable
     */
    @Override
    public IObjectScript getGlobalValue(String globalVar){

        if(this.globalVariableTable == null || this.globalVariableTable.isEmpty()){

            return null;

        }

        Variable variable = new Variable(VarTypeEnum.VAR,null,globalVar);

        return this.globalVariableTable.get(variable);
    }

   

    /**
     * Removes the global variable from the global variable map.
     * @param globalVar the variable to be removed.
     */
    @Override
    public void unsetGlobalValue(String globalVar){

        Variable variable = new Variable(VarTypeEnum.VAR,null,globalVar);

        this.globalVariableTable.remove(variable);
    }

    /**
     * Checks whether this global variable is declared.
     * @param globalVar
     * @return <tt>true</tt> on success <tt>false</tt> on failure
     */
    @Override
    public boolean isGlobalVarDeclared(Variable globalVar){

        return this.globalVariableTable.containsKey(globalVar);
    }

    
    @Override
    public boolean cache() throws Exception {
        boolean status = true;

        for (int i = 0; i < this.getHawkInput().getScriptMap().size(); i++) {

            String scriptStr = this.getHawkInput().getScriptMap().get(i);

            if (!MultiLineScriptCacheFactory.isInsideMultiLineScript(i)) {

                Map<Integer, String> lineCommentMatcherMap = null;

                Map<Integer, String> lineAliasMatcherMap = null;

                Map<Integer, String> lineXMLStructMatcherMap = null;

                Map<Integer, String> lineGlobalVarMatcherMap = PatternMatcher.match(
                        new VariableDeclScript().getPatterns(),
                        scriptStr);

                if (lineGlobalVarMatcherMap != null) {

                    String globalVar = lineGlobalVarMatcherMap.get(1);

                    String globalVarExp = lineGlobalVarMatcherMap.get(2);

                    //FIX ME struct can be global vars....

                    Variable variable = new Variable(VarTypeEnum.VAR, null, globalVar);

                    VariableDeclScript globalVarScript = new VariableDeclScript();

                    globalVarScript.setVariable(variable);

                    globalVarScript.setVariableValue(globalVarScript.evaluateGlobalVariable(globalVarExp).getVariableValue());

                    globalVarScript.getVariable().setValue(globalVarScript.getVariableValue().getValue());

                    this.setGlobalValue(variable, globalVarScript);

                } else {

                    lineCommentMatcherMap = PatternMatcher.match(
                            new SingleLineCommentScript().getPatterns(),
                            scriptStr);
                    lineAliasMatcherMap = PatternMatcher.match(
                            new AliasScript().getPattern(),
                            scriptStr);
                    lineXMLStructMatcherMap = PatternMatcher.match(new XMLStructIncludeScript().getPattern(), scriptStr);

                }

                if ((lineGlobalVarMatcherMap == null && lineCommentMatcherMap == null
                        && lineAliasMatcherMap == null && lineXMLStructMatcherMap == null)
                        && (scriptStr != null && !scriptStr.trim().isEmpty())) {
                    throw new Exception("unrecognized characters {" + scriptStr + "} at line no {" + (++i) + "}");
                }
            }

        }
        return status;
    }

   

    @Override
    public boolean reset() {
         globalVariableTable = new LinkedHashMap<Variable,IObjectScript>();
         return true;
    }
    @Override
    @Create
    public SingleLineScriptCache create() {
        return AppContainer.getInstance().getBean(GlobalVariableScriptCache.class);
    }
    
    
}
