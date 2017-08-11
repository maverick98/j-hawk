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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;
import org.common.di.AppContainer;
import org.commons.ds.stack.Stack;
import org.commons.ds.exp.IObject;
import org.hawk.ds.exp.IHawkObject;

import org.hawk.input.HawkInput;
import org.hawk.lang.IScript;
import org.hawk.lang.VerticalStrip;
import static org.hawk.lang.constant.HawkLanguageKeyWord.function;
import org.hawk.lang.loop.ForLoopScript;
import org.hawk.lang.multiline.MultiLineContainer;
import org.hawk.lang.multiline.MultiLineScript;
import org.hawk.lang.object.IObjectScript;
import org.hawk.lang.type.IDataType;
import org.hawk.lang.type.Variable;
import org.hawk.logger.HawkLogger;
import org.hawk.pattern.PatternMatcher;
import org.common.di.ScanMe;

/**
 *
 * @version 1.0 10 Apr, 2010
 * @author msahu
 */
@ScanMe(true)
public class FunctionScript extends MultiLineScript implements IObjectScript {

    private static final Pattern FUNCTION_DEFN_PATTERN = Pattern.compile("\\s*" + function + "\\s*([a-z|A-Z]{1,}[a-z|A-Z|\\d]*)\\s*\\(\\s*(.*)\\s*\\)\\s*");

    private static final Pattern FUNCTION_DEFN_BRACKET_START_PATTERN = Pattern.compile("\\s*\\{\\s*");

    private static final Pattern FUNCTION_DEFN_BRACKET_END_PATTERN = Pattern.compile("\\s*\\}\\s*");

    private static boolean validateStrips(Set<VerticalStrip> strips) {

        boolean status;
        if (strips == null || strips.isEmpty()) {
            return false;
        }
        //TODOImplement me
        status = true;
        return status;

    }

    public static FunctionNode parseFunctions(Map<Integer, String> scriptMap, Set<VerticalStrip> strips) throws Exception {

        boolean validated = validateStrips(strips);
        if (!validated) {
            throw new Exception("Kindly strip off properly. Hawk loves to be teased");
        }
        FunctionNode rootNode = FunctionNode.createRootFunctionNode();

        for (VerticalStrip strip : strips) {
            parseStrip(rootNode,scriptMap, strip);
        }

        return rootNode;
    }

    public static List<FunctionScript> parseStrip(FunctionNode rootNode,Map<Integer, String> scriptMap, VerticalStrip strip) throws Exception {
        List<FunctionScript> funcScripts = new ArrayList<>();
        for (int i = strip.getStart(); i < strip.getEnd(); i++) {

            Map<Integer, String> map = PatternMatcher.match(FUNCTION_DEFN_PATTERN, scriptMap.get(i));

            if (map != null) {
                if(rootNode.isInsideFunctionScript(rootNode, i)){
                    //I am not going to cache local functions.
                    continue;
                }
                String functionName = map.get(1);
                String parameters = map.get(2).trim();
                ParameterScript parameterScriptOrig = new ParameterScript().parseFunctionParameters(parameters);

                FunctionScript functionScript = new FunctionScript();
                functionScript.setLineNumber(i + 1);
                functionScript.setFunctionName(functionName);
                functionScript.setOriginalFunctionName(functionName);
                MultiLineContainer functionDefn = extractFunctionDefinition(scriptMap, i + 1, functionName);
                functionScript.setMultiLineContainer(functionDefn);
                functionScript.setParameterScriptOrig(parameterScriptOrig);
                functionScript.pushParameterScript();
                funcScripts.add(functionScript);
                rootNode.addFunctionScript(rootNode, functionScript);

            }
        }
        return funcScripts;
    }

    public FunctionScript createFunctionTemplate() {
        FunctionScript templateCopy = new FunctionScript();
        templateCopy.setFunctionName(this.getFunctionName());
        templateCopy.setMultiLineContainer(this.getMultiLineContainer());
        templateCopy.setParameterScriptOrig(this.getParameterScriptOrig());
        templateCopy.pushParameterScript();
        return templateCopy;
    }

    private static MultiLineContainer extractFunctionDefinition(Map<Integer, String> scriptMap, int x, String functionName) throws Exception {
        MultiLineContainer mlc = new MultiLineContainer();
        boolean firstStartBracktFound = false;
        Stack stack = new Stack();
        int start = x;
        int end = -1;
        int i = x;
        mlc.setMultiLineStart(x - 1);
        for (; i < scriptMap.size(); i++) {

            Map<Integer, String> startingBracketMap = PatternMatcher.match(FUNCTION_DEFN_BRACKET_START_PATTERN, scriptMap.get(i));

            if (startingBracketMap != null) {

                if (!firstStartBracktFound) {
                    firstStartBracktFound = true;
                    start = i;
                }
                //  System.out.println(i);
                stack.push("{");

            }

            Map<Integer, String> endingBracketMap = PatternMatcher.match(FUNCTION_DEFN_BRACKET_END_PATTERN, scriptMap.get(i));

            if (endingBracketMap != null) {

                stack.pop();

            }

            if (stack.isEmpty()) {
                break;
            }

        }

        if (!stack.isEmpty() || !firstStartBracktFound) {
            throw new Exception("invalid function {" + functionName + "}");
        }

        end = i;

        mlc.setStart(start);
        mlc.setEnd(end);

        return mlc;
    }

    private ForLoopScript defaultForLoopScript = null;

    private final Stack<ParameterScript> parameterScriptStack = new Stack<>();

    private ParameterScript parameterScript = null;

    private ParameterScript parameterScriptOrig = null;

    public ParameterScript getParameterScriptOrig() {
        return parameterScriptOrig;
    }

    public void setParameterScriptOrig(ParameterScript parameterScriptOrig) {
        this.parameterScriptOrig = parameterScriptOrig;
    }

    public ParameterScript getParameterScript() {
        return this.parameterScriptStack.top();

    }

    public void setParameterScript(ParameterScript parameterScript) {
        this.parameterScript = parameterScript;
    }

    public void pushParameterScript() {
        this.parameterScript = this.parameterScriptOrig.copy();
        this.parameterScriptStack.push(this.parameterScript);
    }

    public ParameterScript popParameterScript() {
        this.parameterScript = null;
        return this.parameterScriptStack.pop();
    }

    @Override
    public String mangle() {
        StringBuilder sb = new StringBuilder();
        sb.append("_var_");
        //sb.append(this.functionName);
        //sb.append(this.mangleParameters());
        return sb.toString();
    }

    private String mangleParameters() {
        return this.getParameterScript().mangle();
    }

    public boolean initializeParams() {
        return this.getParameterScript().initializeParams();
    }

    public boolean initializeParamsValue(Map<Integer, IObjectScript> params) throws Exception {
        return this.getParameterScript().initializeParamsValue(params);
    }

    public IScript getParamValue(Variable paramVar) {
        return this.getParameterScript().getParamValue(paramVar);
    }

    public IScript getParamValue(String localVar) {

        return this.getParameterScript().getParamValue(localVar);
    }

    public void setParamValue(Variable variable, IObjectScript localValue) {
        this.getParameterScript().setParamValue(variable, localValue);
    }

    public boolean isParamVarDeclared(Variable paramVar) {

        return this.getParameterScript().isParamVarDeclared(paramVar);
    }

    public ForLoopScript getDefaultForLoopScript() {
        return defaultForLoopScript;
    }

    public void setDefaultForLoopScript(ForLoopScript defaultForLoopScript) {
        this.defaultForLoopScript = defaultForLoopScript;
        this.defaultForLoopScript.setOuterMultiLineScript(this);
    }

    private String originalFunctionName;
    private String functionName;

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getOriginalFunctionName() {
        return originalFunctionName;
    }

    public void setOriginalFunctionName(String originalFunctionName) {
        this.originalFunctionName = originalFunctionName;
    }

    public boolean isMyOverloadingMate(FunctionScript theOtherFunctionScript) {
        return this.getOriginalFunctionName().equals(theOtherFunctionScript.getOriginalFunctionName());
    }

    public IScript executeDefaultForLoopScript(Map<Integer, IObjectScript> params) throws Exception {
        if (this.defaultForLoopScript == null) {
            throw new Exception("could not find defaultScript to execute");
        }
        this.pushParameterScript();
        this.initializeParamsValue(params);
        IScript result = this.defaultForLoopScript.execute();
        this.popParameterScript();
        return result;

    }

    @Override
    public String toString() {
        return this.originalFunctionName;
    }

    public boolean isMainFunction() {
        boolean isMain = false;
        if (this.functionName != null) {
            isMain = this.functionName.equals("main");
        }
        return isMain;
    }

    @Override
    public void pushLocalVars() {

        this.defaultForLoopScript.pushLocalVars();

    }

    @Override
    public Map<String, IDataType> popLocalVars() {

        return this.defaultForLoopScript.popLocalVars();
    }
    /*
     @Override
     public boolean isVariable() {
     return false;
     }
     */

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
    public int length() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean passByReference() {
        return true;
    }

    @Override
    public String toUI() {
        return this.originalFunctionName;
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
    public IObject add(IObject otherScript) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject subtract(IObject otherScript) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject multiply(IObject otherScript) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject divide(IObject otherScript) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject modulus(IObject otherScript) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject equalTo(IObject otherScript) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject greaterThan(IObject otherScript) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject lessThan(IObject otherScript) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject greaterThanEqualTo(IObject otherScript) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject lessThanEqualTo(IObject otherScript) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject and(IObject otherScript) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject or(IObject otherScript) throws Exception {
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
    public Object toJava() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FunctionScript copy() {
        return this;
    }
    
    @Override
    public int createScript(final MultiLineScript multiLineScript, int i) throws Exception {
        Map<Integer, String> scriptMap =AppContainer.getInstance().getBean( HawkInput.class).getScriptMap();
        Map<Integer, String> map = PatternMatcher.match(FUNCTION_DEFN_PATTERN, scriptMap.get(i));
        if(map == null){
            return -1;
        }
        //FunctionScript.parseFunctions(null, null)
        VerticalStrip verticalStrip = new VerticalStrip(i, multiLineScript.getMultiLineContainer().getEnd());
        Set<VerticalStrip> all = new TreeSet<>();
        all.add(verticalStrip);
        
        if(multiLineScript.getRootFunctionNode() == null){
            multiLineScript.setRootFunctionNode(FunctionScript.parseFunctions(scriptMap, all));
             multiLineScript.getRootFunctionNode()
                .visitFunctionNode(
                        multiLineScript.getRootFunctionNode(),
                        new AbstractFunctionNodeVisitor() {
                            private  final HawkLogger logger = HawkLogger.getLogger(AbstractFunctionNodeVisitor.class.getName());
                            @Override
                            public boolean onVisit(FunctionNode functionNode) {
                                boolean shouldIcontrolVisit = false;
                                try {
                                    FunctionScript functionScript = functionNode.getValue();
                                    functionScript.setOuterMultiLineScript(multiLineScript);
                                    createFunctionScript(functionScript);
                                } catch (Exception ex) {
                                    logger.error(ex);
                                }
                                return shouldIcontrolVisit;
                            }
                        }
                );
        }
        
        int result = -1;
        
        String funName = map.get(1);
        MultiLineContainer functionDefn = extractFunctionDefinition(scriptMap, i + 1, funName);
        if(functionDefn != null){
            result = functionDefn.getEnd() +1;
        }
        
        return result;
    }
    
   
    
     public static  boolean createFunctionScript(FunctionScript functionScript) throws Exception {

        ForLoopScript defaultForLoopScript = new ForLoopScript();

        defaultForLoopScript.setDefaultForLoop(true);

        functionScript.initializeParams();
        functionScript.setDefaultForLoopScript(defaultForLoopScript);

        defaultForLoopScript.setFunctionScript(functionScript);
        defaultForLoopScript.setMultiLineContainer(functionScript.getMultiLineContainer());
        
        boolean parsed;
         Map<Integer, String> scriptMap =AppContainer.getInstance().getBean( HawkInput.class).getScriptMap();
       
        try {
            parsed = defaultForLoopScript.parseMultiLines
                                            (
                                                scriptMap,
                                                functionScript.getMultiLineContainer().getStart() + 1,
                                                functionScript.getMultiLineContainer().getEnd()
                                            );
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("compilation failed..." + ex.getMessage());
        }

        return parsed;
    }
   
    
}
