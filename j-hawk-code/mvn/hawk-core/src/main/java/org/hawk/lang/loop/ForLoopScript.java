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
package org.hawk.lang.loop;

import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import org.commons.string.StringUtil;
import org.common.di.AppContainer;
import org.commons.ds.stack.Stack;

import org.hawk.input.HawkInput;
import org.hawk.lang.IScript;
import static org.hawk.lang.constant.HawkLanguageKeyWord.forLoop;
import org.hawk.lang.enumeration.VarTypeEnum;
import org.hawk.lang.multiline.MultiLineContainer;
import org.hawk.lang.multiline.MultiLineScript;
import org.hawk.lang.object.AssignmentScript;
import org.hawk.lang.object.IObjectScript;
import org.hawk.lang.object.LocalVarDeclScript;
import org.hawk.lang.type.BooleanDataType;
import org.hawk.lang.type.IDataType;
import org.hawk.lang.type.Variable;
import org.hawk.logger.HawkLogger;
import org.hawk.pattern.PatternMatcher;
import org.common.di.ScanMe;

/**
 *
 * @version 1.0 8 Apr, 2010
 * @author msahu
 */
@ScanMe(true)
public class ForLoopScript extends MultiLineScript {

    private static final HawkLogger logger = HawkLogger.getLogger(ForLoopScript.class.getName());
    private boolean defaultForLoop = false;
    private final Stack<LocalVarDeclScript> variableInitScriptStack = new Stack<>();
    private final Stack<String> boundaryCheckExpStack = new Stack<>();
    private final Stack<AssignmentScript> variableIterationScriptStack = new Stack<>();
    private LocalVarDeclScript variableInitScript = null;
    private String boundaryCheckExp = null;
    private AssignmentScript variableIterationScript = null;
    private LocalVarDeclScript variableInitScriptOrig = null;
    private String boundaryCheckExpOrig = null;
    private AssignmentScript variableIterationScriptOrig = null;

    public String getBoundaryCheckExpOrig() {
        return boundaryCheckExpOrig;
    }

    public void setBoundaryCheckExpOrig(String boundaryCheckExpOrig) {
        this.boundaryCheckExpOrig = boundaryCheckExpOrig;
    }

    public LocalVarDeclScript getVariableInitScriptOrig() {
        return variableInitScriptOrig;
    }

    public void setVariableInitScriptOrig(LocalVarDeclScript variableInitScriptOrig) {
        this.variableInitScriptOrig = variableInitScriptOrig;
    }

    public AssignmentScript getVariableIterationScriptOrig() {
        return variableIterationScriptOrig;
    }

    public void setVariableIterationScriptOrig(AssignmentScript variableIterationScriptOrig) {
        this.variableIterationScriptOrig = variableIterationScriptOrig;
    }

    public String getBoundaryCheckExp() {
        return boundaryCheckExpStack.top();

    }

    public void setBoundaryCheckExp(String boundaryCheckExp) {
        this.boundaryCheckExp = boundaryCheckExp;
    }

    public void pushBoundaryCheckExp() {
        this.boundaryCheckExp = this.boundaryCheckExpOrig;
        this.boundaryCheckExpStack.push(this.boundaryCheckExp);
    }

    public String popBoundaryCheckExp() {
        this.boundaryCheckExp = null;
        return this.boundaryCheckExpStack.pop();
    }

    public LocalVarDeclScript getVariableInitScript() {
        return this.variableInitScriptStack.top();

    }

    public void setVariableInitScript(LocalVarDeclScript variableInitScript) {
        this.variableInitScript = variableInitScript;
    }

    public void pushVariableInitScript() {
        this.variableInitScript = this.variableInitScriptOrig.copy();
        this.variableInitScriptStack.push(this.variableInitScript);
    }

    public LocalVarDeclScript popVariableInitScript() {
        this.variableInitScript = null;
        return this.variableInitScriptStack.pop();
    }

    public AssignmentScript getVariableIterationScript() {
        return this.variableIterationScriptStack.top();

    }

    public void setVariableIterationScript(AssignmentScript variableIterationScript) {
        this.variableIterationScript = variableIterationScript;
    }

    public void pushVariableIterationScript() {
        this.variableIterationScript = this.variableIterationScriptOrig.copy();
        this.variableIterationScriptStack.push(this.variableIterationScript);
    }

    public AssignmentScript popVariableIterationScript() {
        this.variableIterationScript = null;
        return this.variableIterationScriptStack.pop();
    }

    public void pushForLoopData() {
        if (!this.isDefaultForLoop()) {
            this.pushVariableInitScript();
            this.pushBoundaryCheckExp();
            this.pushVariableIterationScript();
        }
    }

    public void popForLoopData() {
        if (!this.isDefaultForLoop()) {
            this.popVariableInitScript();
            this.popBoundaryCheckExp();
            this.popVariableIterationScript();
        }
    }

    @Override
    public void pushLocalVars() {

        super.pushLocalVars();
    }

    @Override
    public Map<String, IDataType> popLocalVars() {

        super.popLocalVars();
        return null;
    }

    @Override
    public IObjectScript getLocalValue(Variable localVar) {
        LocalVarDeclScript tmp = this.getVariableInitScript();
        IObjectScript rtn = null;
        if (tmp != null && tmp.getLocalVar().equals(localVar)) {

            tmp.getLocalVar().setValue(tmp.getLocalVarValue().getValue());
            rtn = tmp;

        }
        if (rtn == null) {
            rtn = super.getLocalValue(localVar);
        }
        return rtn;
    }

    @Override
    public IObjectScript getLocalValue(String localVar) {
        IObjectScript result = null;
        Variable variable = new Variable(VarTypeEnum.VAR, null, localVar);
        result = this.getLocalValue(variable);
        if (result == null) {
            variable = new Variable(VarTypeEnum.VARX, null, localVar);
            result = this.getLocalValue(variable);
        }
        return result;
    }

    @Override
    public void setLocalValue(Variable variable, IObjectScript localValue) {

        if (this.isLocalVarDeclared(variable)) {

            LocalVarDeclScript tmp = this.getVariableInitScript();
            if (tmp != null) {
                tmp.setLocalVarValue(localValue.getVariableValue());
            }

        } else {
            super.setLocalValue(variable, localValue);
        }
    }

    @Override
    public void unsetLocalValue(Variable localVar) {
        if (this.isLocalVarDeclared(localVar)) {
            LocalVarDeclScript tmp = this.getVariableInitScript();
            if (tmp != null) {
                tmp.setLocalVar(null);
            }

        } else {
            super.unsetLocalValue(localVar);
        }
    }

    @Override
    public boolean isLocalVarDeclared(Variable localVar) {

        return super.isLocalVarDeclared(localVar);
    }

    @Override
    public String toString() {
        return "For::" + this.getVariableInitScript().toString() + "::" + this.getBoundaryCheckExp() + "::" + this.getVariableIterationScript().toString();
    }

    public boolean isDefaultForLoop() {
        return defaultForLoop;
    }

    public void setDefaultForLoop(boolean defaultForLoop) {
        this.defaultForLoop = defaultForLoop;
    }

    @Override
    public IScript execute() throws Exception {

        this.pushLocalVars();
        this.pushForLoopData();
        boolean breakApplied = false;
        IObjectScript rtnVal = LocalVarDeclScript.createDummyBooleanScript();
        boolean returned = false;
        LocalVarDeclScript variableInitScriptTmp = this.getVariableInitScript();
        if (variableInitScriptTmp != null && !this.isDefaultForLoop()) {
            variableInitScriptTmp.execute();
        }
        boolean boundaryCheck = true;


        do {

            String boundaryCheckExpTmp = this.getBoundaryCheckExp();
            if (boundaryCheckExpTmp != null && !this.isDefaultForLoop()) {

                Variable rtn = this.evaluateLocalVariable(
                        boundaryCheckExpTmp).getVariableValue();
                BooleanDataType dd = null;
                if (rtn.getValue() instanceof BooleanDataType) {
                    dd = (BooleanDataType) rtn.getValue();
                } else {
                    throw new Exception("Invalid expression");
                }

                boundaryCheck = dd.isData();

            }

            if (boundaryCheck) {
                for (Entry<Integer, IScript> entry : this.getInnerScripts().entrySet()) {
                    IScript script = entry.getValue();
                    IObjectScript status = null;
                    try {
                        status = (IObjectScript)script.execute();
                    } catch (Throwable th) {
                        th.printStackTrace();
                        logger.error(th);
                        throw new Exception("error while executing script at line no {" + (entry.getKey() + 1) + "}");

                    }
                    if(this.breakApplied()){
                        breakApplied = true;
                        this.releaseBreak();
                        break;
                    }
                 //   if (status != null && (script instanceof BreakScript) && !this.isDefaultForLoop()) {
                 //       breakApplied = true;
                 //       break;
                 //   }
                    if(this.returnOrdered()){
                        returned = true;
                        this.acceptReturn();
                        rtnVal = status;
                        break;
                    }
                /*
                    if (status != null && status.getVariableValue().isRtrn()) {
                        returned = true;
                        rtnVal = status;

                        break;
                    }
                */    

                }

                this.unsetAllLocalValue();
                if (breakApplied || returned) {
                    break;
                }

                AssignmentScript variableIterationScriptTmp = this.getVariableIterationScript();
                if (variableIterationScriptTmp != null && !this.isDefaultForLoop()) {
                    variableIterationScriptTmp.execute();
                }
            } else {
                this.popForLoopData();
            }
            if (this.isDefaultForLoop()) {
                break;
            }

        } while (boundaryCheck);

        this.popLocalVars();
        if (returned) {
            return rtnVal;
        }
        rtnVal.getVariableValue().setValue(new BooleanDataType(true));
        return rtnVal;
    }
    /*
    @Override
    public boolean isVariable() {
        return false;
    }
    */ 

    /**
     * This returns <tt>EchoScript</tt> from echo matcher map.
     *
     * @param multiLineScript
     * @param i
     * @return
     * @throws org.hawk.exception.Exception
     */
    
    @Override
    public  int createScript(
            MultiLineScript multiLineScript,
            int i) throws Exception {

        Map<Integer, String> scriptMap = AppContainer.getInstance().getBean(HawkInput.class).getScriptMap();
        Map<Integer, String> lineForMatcherMap = PatternMatcher.match(FOR_LOOP_PATTERN, scriptMap.get(i));
        if (lineForMatcherMap == null) {
            return -1;
        }
        ForLoopScript innerForLoopScript ;



        MultiLineContainer mlc = multiLineScript.extractMultiLineContainer(scriptMap, i + 1);
        innerForLoopScript = new ForLoopScript();


        String forLoopData = lineForMatcherMap.get(1);


        forLoopData = StringUtil.parseBracketData(forLoopData);
        forLoopData = forLoopData.trim();
        Map<Integer, String> lineForLoopDataMatcherMap = PatternMatcher.match(
                FOR_LOOP_DATA_PATTERN,
                forLoopData);
        if (lineForLoopDataMatcherMap != null) {

            String varInit = lineForLoopDataMatcherMap.get(1);
            String exp = lineForLoopDataMatcherMap.get(2);
            String varItr = lineForLoopDataMatcherMap.get(3);

            if (!varInit.trim().isEmpty()) {



                Map<Integer, String> lineVarDeclMatcherMap = PatternMatcher.match(
                        new LocalVarDeclScript().getPattern(),
                        varInit);

                LocalVarDeclScript script = (LocalVarDeclScript)new LocalVarDeclScript().createScript(lineVarDeclMatcherMap);
                if (script != null) {
                    if (!LocalVarDeclScript.isValid(script.getLocalVar(), multiLineScript)) {
                        throw new Exception("the variable " + script.getLocalVar() + " is already declared");
                    }
                    script.setLineNumber(i + 1);
                    innerForLoopScript.setVariableInitScriptOrig(script);

                    script.setOuterMultiLineScript(innerForLoopScript);
                }

            } else {
                innerForLoopScript.setVariableInitScriptOrig(null);
            }
            if (!exp.trim().isEmpty()) {

                innerForLoopScript.setBoundaryCheckExpOrig(exp);

            } else {
                innerForLoopScript.setBoundaryCheckExpOrig(null);
            }

            if (!varItr.trim().isEmpty()) {



                Map<Integer, String> lineVarAsgnMatcherMap = PatternMatcher.match(
                        new AssignmentScript().getPatterns(),
                        varItr);

                AssignmentScript script = (AssignmentScript)new AssignmentScript().createScript(lineVarAsgnMatcherMap);
                if (script != null) {
                    script.setLineNumber(i + 1);
                    innerForLoopScript.setVariableIterationScriptOrig(script);

                    script.setOuterMultiLineScript(innerForLoopScript);
                }

            } else {
                innerForLoopScript.setVariableIterationScriptOrig(null);
            }
        }

        innerForLoopScript.setLineNumber(i + 1);
        multiLineScript.addScript(i, innerForLoopScript);
        innerForLoopScript.setOuterMultiLineScript(multiLineScript);
        innerForLoopScript.parseMultiLines(scriptMap, mlc.getStart() + 1, mlc.getEnd() );
        i = mlc.getEnd() + 1;



        return i;
    }
    @Override
    public boolean isLoop() {
        return true;
    } 
    private static final Pattern FOR_LOOP_PATTERN = Pattern.compile("\\s*" + forLoop + "\\s*(.*)\\s*");
    private static final Pattern FOR_LOOP_DATA_PATTERN =
            Pattern.compile("\\s*(.*)\\s*;\\s*(.*)\\s*;\\s*(.*)\\s*");
}
