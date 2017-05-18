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
package org.hawk.lang.function;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.common.di.AppContainer;
import org.commons.ds.exp.IObject;
import org.hawk.ds.exp.IHawkObject;

import org.hawk.lang.IScript;
import static org.hawk.lang.constant.HawkLanguageKeyWord.exec;
import org.hawk.lang.grammar.SingleLineGrammar;
import org.hawk.lang.multiline.MultiLineScript;
import org.hawk.lang.object.IObjectScript;
import org.hawk.lang.singleline.SingleLineScript;
import org.hawk.lang.singleline.pattern.LinePattern;
import org.hawk.lang.type.Variable;
import org.hawk.logger.HawkLogger;
import org.commons.string.StringUtil;
import org.common.di.ScanMe;
/**
 *
 * @version 1.0 11 Apr, 2010
 * @author msahu
 */
@ScanMe(true)
public class ExecFunctionScript extends SingleLineScript  implements IObjectScript{

    private static final HawkLogger logger = HawkLogger.getLogger(ExecFunctionScript.class.getName());
    
    private String functionName;
    
    private ExecParameterScript execParameterScript = null;
    
    private boolean shouldExecute = true;

    public boolean isShouldExecute() {
        return shouldExecute;
    }

    public void setShouldExecute(boolean shouldExecute) {
        this.shouldExecute = shouldExecute;
    }

    @Override
    public Variable getVariable() {
        Variable rtnVar = Variable.createDummyStringVariable(this.functionName);
        return rtnVar;
    }

    @Override
    public void setVariable(Variable value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setVariableValue(Variable value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Variable getVariableValue() {

        Variable rtnVar = Variable.createDummyStringVariable(this.functionName);
        return rtnVar;
    }

   
     @Override
    public String mangle() {
        StringBuilder sb = new StringBuilder();
        sb.append("_var_");
        //sb.append(this.getMangledParams());
        return sb.toString();
    }
    @Override
    public int length() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean passByReference() {
        return true;
    }

    @Override
    public String toUI() {
        return this.getFunctionName();
    }

     @Override
    public IScript copy() {
        return this;
    }
    @Override
    public boolean isProxy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObjectScript getActualObjectScript() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    

    @Override
    public Object toJava() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IHawkObject refer(IHawkObject otherScript) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IHawkObject assign(IHawkObject otherScript) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IHawkObject arrayBracket(IHawkObject otherScript) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject add(IObject otherObject) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject subtract(IObject otherObject) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject multiply(IObject otherObject) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject divide(IObject otherObject) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject modulus(IObject otherObject) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject equalTo(IObject otherObject) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject greaterThan(IObject otherObject) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject lessThan(IObject otherObject) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject greaterThanEqualTo(IObject otherObject) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject lessThanEqualTo(IObject otherObject) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject and(IObject otherObject) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject or(IObject otherObject) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static class ExecFunction extends SingleLineGrammar {

        @Override
        public String toString() {
            return "ExecFunction" + "parsingSequence=" + this.getParsingSequence() + ", linePattern=" + this.getLinePattern() + '}';
        }

    }

    @Override
    public Integer getVerticalParserSequence() {

        return this.getGrammar().getSingleLine().getExecFunction().getParsingSequence();
    }

    @Override
    public Set<LinePattern> getPatterns() {

        return this.getGrammar().getSingleLine().getExecFunction().getLinePattern();
    }

    @Override
    public ExecFunctionScript createScript(Map<Integer, String> lineFunctionMatcherMap) throws Exception {

        if (lineFunctionMatcherMap == null) {
            return null;
        }
        ExecFunctionScript functionScript = new ExecFunctionScript();
        String execStr = lineFunctionMatcherMap.get(1);
        if(!StringUtil.isNullOrEmpty(execStr)){
            if(execStr.equals(exec)){
                functionScript.setShouldExecute(true);
            }
        }else{
            functionScript.setShouldExecute(false);
        }
        functionScript.setFunctionName(lineFunctionMatcherMap.get(2));
        ExecParameterScript execParameterScript = (ExecParameterScript) new ExecParameterScript().createScript(lineFunctionMatcherMap.get(3));
        functionScript.setExecParameterScript(execParameterScript);
        return functionScript;
    }

    @Override
    public void setOuterMultiLineScript(MultiLineScript outerMultiLineScript) {
        super.setOuterMultiLineScript(outerMultiLineScript);
        this.getExecParameterScript().setOuterMultiLineScript(this.getOuterMultiLineScript());
    }

    public ExecParameterScript getExecParameterScript() {
        return execParameterScript;
    }

    public void setExecParameterScript(ExecParameterScript execParameterScript) {
        this.execParameterScript = execParameterScript;
    }

    public Map<Integer, IObjectScript> getParamMap() {
        return this.getExecParameterScript().getParamMap();
    }

    public Map<Integer, IObjectScript> cloneParamMap() {

        return this.getExecParameterScript().cloneParamMap();

    }

    public String getParams() {
        return this.getExecParameterScript().getParams();
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    protected String getMangledParams() {
        StringBuilder sb = new StringBuilder();
        for (Entry<Integer, IObjectScript> entry : this.getParamMap().entrySet()) {
            sb.append(entry.getValue().mangle());
        }
        return sb.toString();
    }

    public List<IObjectScript> getParamList() {
        return this.getExecParameterScript().getParamList();
    }

    @Override
    public IScript execute() throws Exception {

        if(!this.isShouldExecute()){
            return this;
        }
        

        IObjectScript functionScript = this.evaluateLocalVariable(this.functionName);
        //String funName = this.functionName;
        String funName = functionScript.toUI();
       
        FunctionInvocationInfo functionInvocationInfo = new FunctionInvocationInfo();
        String originalFuncName = null;
        if(this.findEncapsulatingFunctionScript()!= null){
            originalFuncName = this.findEncapsulatingFunctionScript().getOriginalFunctionName();
        }
        functionInvocationInfo.setEncapsulatingFunctionName(originalFuncName);
        functionInvocationInfo.setLineNo(this.getLineNumber());
        functionInvocationInfo.setFunctionName(funName);
        if(functionScript instanceof ExecFunctionScript) {
            ExecFunctionScript thatExecFunctionScript = (ExecFunctionScript) functionScript;
            thatExecFunctionScript.getExecParameterScript().execute();
            functionInvocationInfo.setParamMap(thatExecFunctionScript.getExecParameterScript().getParamMap());
        }else{
            this.getExecParameterScript().execute();
            functionInvocationInfo.setParamMap(this.getParamMap());
        }
        
        //IObjectScript result = this.evaluateLocalVariable(funName);
        //functionInvocationInfo.setFunctionName(result.getVariableValue().getValue().toString());
        functionInvocationInfo.setFunctionScript(this.find(functionInvocationInfo));
        IFunctionExecutor functionExecutor = AppContainer.getInstance().getBean(FunctionExecutor.class);
        IObjectScript rtnVar = functionExecutor.execute(functionInvocationInfo);
        if (!this.isLastScript()) {
            rtnVar.getVariableValue().setRtrn(false);
        }
        return rtnVar;

    }

    private FunctionScript find(FunctionInvocationInfo functionInvocationInfo) {
        FunctionScript result = null;
        MultiLineScript outerMLS = this.getOuterMultiLineScript();
        do {
            if (outerMLS.getRootFunctionNode() != null) {

                result = outerMLS.getRootFunctionNode().findFunctionScript(functionInvocationInfo.getFunctionName(), functionInvocationInfo.getParamMap());
            }
            if (result != null) {
                break;
            }
            outerMLS = outerMLS.getOuterMultiLineScript();
        } while (outerMLS != null);

        return result;

    }

}
