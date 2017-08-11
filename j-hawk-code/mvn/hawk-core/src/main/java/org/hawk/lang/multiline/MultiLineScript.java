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
package org.hawk.lang.multiline;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Pattern;
import org.common.di.AppContainer;
import org.commons.ds.stack.Stack;

import org.hawk.lang.AbstractScript;
import org.hawk.lang.IScript;
import org.hawk.lang.comment.SingleLineCommentScript;
import org.hawk.lang.console.EchoScript;
import org.hawk.lang.enumeration.ArrayTypeEnum;
import org.hawk.lang.enumeration.VarTypeEnum;
import org.hawk.lang.function.FunctionNode;
import org.hawk.lang.function.FunctionScript;
import org.hawk.lang.object.IObjectScript;
import org.hawk.lang.object.StructureScript;
import org.hawk.lang.singleline.SingleLineScript;
import org.hawk.lang.singleline.SingleLineScriptFactory;
import org.hawk.lang.singleline.pattern.LinePattern;
import org.hawk.lang.type.IDataType;
import org.hawk.lang.type.Variable;
import org.hawk.logger.HawkLogger;
import org.hawk.module.annotation.PostCreateScript;
import org.hawk.module.annotation.SingleTonSetter;
import org.hawk.pattern.PatternMatcher;
import org.hawk.sequence.ISequenceProvider;
import org.commons.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @version 1.0 10 Apr, 2010
 * @author msahu
 */
public class MultiLineScript extends AbstractScript implements IMultiLineScript {

    private static final HawkLogger logger = HawkLogger.getLogger(MultiLineScript.class.getName());
    protected Map<Integer, IScript> innerScripts = new TreeMap<>();
    protected FunctionScript functionScript = null;
    protected Stack<Map<Variable, IScript>> localVariableTableStack = new Stack<>();
    protected Stack<Set<String>> localStructStack = new Stack<>();
    protected MultiLineContainer multiLineContainer = null;
    @Autowired(required = true)
       
    private ISequenceProvider multiLineScriptVerticalSequenceProvider;

    public ISequenceProvider getMultiLineScriptVerticalSequenceProvider() {
        return multiLineScriptVerticalSequenceProvider;
    }

    public void setMultiLineScriptVerticalSequenceProvider(ISequenceProvider multiLineScriptVerticalSequenceProvider) {
        this.multiLineScriptVerticalSequenceProvider = multiLineScriptVerticalSequenceProvider;
    }

    public MultiLineContainer getMultiLineContainer() {
        return multiLineContainer;
    }

    public void setMultiLineContainer(MultiLineContainer multiLineContainer) {
        this.multiLineContainer = multiLineContainer;
    }

    public Map<Integer, IScript> getInnerScripts() {
        return innerScripts;
    }

    public void setInnerScripts(Map<Integer, IScript> innerScripts) {
        this.innerScripts = innerScripts;
    }

    /**
     * This checks whether particular script is inside multiline script
     * definition block.
     *
     * @param i line no at which script is present
     * @return <tt>true</tt> if i is inside any multiline definition block
     * <tt>false</tt> otherwise
     */
    public boolean isInside(int i, boolean shouldCheckFromMLCDefn) {
        boolean status = false;
        if (this.getMultiLineContainer() != null) {
            int start = shouldCheckFromMLCDefn
                    ? this.getMultiLineContainer().getMultiLineStart()
                    : this.getMultiLineContainer().getStart();
            int end = this.getMultiLineContainer().getEnd();
            if (i >= start && (shouldCheckFromMLCDefn ? (i <= end) : i < end)) {
                status = true;
            }
        }
        return status;
    }

    /**
     * This checks whether particular script is inside multiline script
     * definition block.
     *
     * @param i line no at which script is present
     * @return <tt>true</tt> if i is inside any multiline definition block
     * <tt>false</tt> otherwise
     */
    public boolean isInside(int i) {
        return this.isInside(i, false);
    }

    @Override
    public void pushLocalVars() {

        Map<Variable, IScript> localVariableTable = new LinkedHashMap<>();
        this.localVariableTableStack.push(localVariableTable);
        Set<String> localStructs = new TreeSet<>();
        this.localStructStack.push(localStructs);
        this.innerScripts.entrySet().stream().filter((entry) -> (entry.getValue() instanceof IMultiLineScript)).map((entry) -> (IMultiLineScript) entry.getValue()).forEach((multiLineScript) -> {
            multiLineScript.pushLocalVars();
        });
    }

    @Override
    public Map<String, IDataType> popLocalVars() {

        this.localVariableTableStack.pop();
        this.localStructStack.pop();
        for (Entry<Integer, IScript> entry : this.innerScripts.entrySet()) {
            if (entry.getValue() instanceof IMultiLineScript) {
                IMultiLineScript multiLineScript = (IMultiLineScript) entry.getValue();
                multiLineScript.popLocalVars();
            }
        }
        return null;
    }

    public IObjectScript getLocalValue(Variable localVar) {
        return this.getLocalValueInternal(localVar.getName());
    }

    private IObjectScript getLocalValueInternal(String localVar) {
        Map<Variable, IObjectScript> localVarTable = this.localVariableTableStack.top();
        Set<String> localStructs = this.localStructStack.top();
        if ((localVarTable == null) && (localStructs == null || localStructs.isEmpty())) {
            return null;
        }
        IObjectScript result = null;
        Variable possibleKey = new Variable(VarTypeEnum.VAR, null, localVar);
        if (localVarTable != null) {
            result = localVarTable.get(possibleKey);

            if (result == null) {
                possibleKey.setVarTypeEnum(VarTypeEnum.VARX);
                result = localVarTable.get(possibleKey);
                if (result == null) {
                    possibleKey.setVarTypeEnum(VarTypeEnum.STRUCT);

                    for (String struct : localStructs) {
                        possibleKey.setStructName(struct);
                        result = localVarTable.get(possibleKey);
                        if (result != null) {
                            break;
                        }
                    }
                    if (result == null) {
                        result = findArrayType(localVar, localVarTable);
                    }
                }
            }

        }
        return result;
    }

    public IObjectScript findArrayType(String localVar, Map<Variable, IObjectScript> localVarTable) {
        IObjectScript result = null;
        Variable possibleKey = new Variable(VarTypeEnum.VAR, null, localVar);

        possibleKey.setArrayType(ArrayTypeEnum.VAR);
        possibleKey.setVarTypeEnum(VarTypeEnum.VAR);

        result = localVarTable.get(possibleKey);
        if (result == null) {
            possibleKey.setArrayType(ArrayTypeEnum.VARX);
            possibleKey.setVarTypeEnum(VarTypeEnum.VARX);
            result = localVarTable.get(possibleKey);
            if (result == null) {
                Set<String> localStructs = this.localStructStack.top();
                possibleKey.setArrayType(ArrayTypeEnum.STRUCT);
                possibleKey.setVarTypeEnum(VarTypeEnum.STRUCT);
                for (String struct : localStructs) {
                    possibleKey.setStructName(struct);
                    result = localVarTable.get(possibleKey);
                    if (result != null) {
                        break;
                    }
                }
            }
        }

        return result;
    }

    public IObjectScript getLocalValue(String localVar) {
        return this.getLocalValueInternal(localVar);
    }

    public void setLocalValue(Variable variable, IObjectScript localValue) {
        Map<Variable, IObjectScript> localVarTable = this.localVariableTableStack.top();
        Set<String> localStructs = this.localStructStack.top();

        if (localVarTable != null) {
            localVarTable.put(variable, localValue);
            if (variable.getVarTypeEnum() != null
                    && variable.getVarTypeEnum() == VarTypeEnum.STRUCT
                    && variable.getStructName() != null) {
                localStructs.add(variable.getStructName());
            }
        }

    }

    public void unsetLocalValue(Variable localVar) {
        Map<Variable, IObjectScript> localVarTable = this.localVariableTableStack.top();
        if (localVarTable != null) {
            localVarTable.remove(localVar);
        }
        Set<String> localStructs = this.localStructStack.top();
        if (localStructs != null) {
            StringBuilder key = new StringBuilder();
            for (String structName : localStructs) {
                key.append(structName);
                key.append(localVar);
                if (localVarTable.remove(key) != null) {
                    break;
                }
            }
        }
    }

    public void unsetAllLocalValue() {
        Map<String, IDataType> localVarTable = this.localVariableTableStack.top();
        if (localVarTable != null) {
            localVarTable.clear();
        }
        Set<String> localStructs = this.localStructStack.top();
        localStructs.clear();
    }

    public boolean isLocalVarDeclared(Variable localVar) {

        IObjectScript result = this.getLocalValue(localVar);
        return result == null
                ? false
                : result.getVariable().getValue() != null;
    }

    public StructureScript getStructVariable(String structVariable) {
        if (structVariable == null || structVariable.isEmpty()) {
            return null;
        }
        StructureScript result = null;
        for (Entry<Integer, IScript> entry : this.innerScripts.entrySet()) {
            if (entry.getValue() instanceof StructureScript) {
                StructureScript thatScript = (StructureScript) entry.getValue();
                if (structVariable.equals(thatScript.getStructVarName().getName())) {
                    result = thatScript;
                    break;
                }
            }
        }
        return result;
    }

    public FunctionScript getFunctionScript() {
        return functionScript;
    }

    public void setFunctionScript(FunctionScript functionScript) {
        this.functionScript = functionScript;
    }

    public boolean addScript(Integer line, AbstractScript script) {
        if (script != null) {
            this.innerScripts.put(line, script);
            return true;
        }
        return false;
    }

    public boolean parseMultiLines(Map<Integer, String> scriptMap, int x, int y) throws Exception {

        if (x == y) {
            logger.info("warning....empty method found...");
            return true;
        }
        boolean parsed = true;
        int lastLine = -1;
        for (int i = x; i < y;) {

            i = this.addScripts(scriptMap, i);
            lastLine = i - 1;
        }

        AbstractScript lastScript = (AbstractScript) this.getInnerScripts().get(lastLine);
        if (lastScript != null) {
            lastScript.setLastScript(true);
        }
        return parsed;
    }

    /**
     * This checks script at line i with with All the multilinescripts known to
     * hawk if not found treats it as single line script
     *
     * @param scriptMap
     * @param i
     * @return
     * @throws Exception
     */
    private int addScripts(Map<Integer, String> scriptMap, int i) throws Exception {
        Map<Class, MultiLineScript> cachedMultiLineScripts = MultiLineScriptFactory.getMultiLineScripts();
        boolean multiLineScriptFound = false;
        for (Entry<Class, MultiLineScript> entry : cachedMultiLineScripts.entrySet()) {

            try {
                int nextLine = -1;
                nextLine = entry.getValue().createScript(this, i);
                if (nextLine != -1) {
                    i = nextLine;
                    multiLineScriptFound = true;
                    break;
                }
            } catch (Throwable ex) {
                ex.printStackTrace();
                throw new Exception(ex.getCause().getMessage());
            }

        }

        if (!multiLineScriptFound) {
            this.addScripts(scriptMap.get(i), i);
            i++;
        }
        return i;
    }

    /**
     * This simply treats the input as single line scrit
     *
     * @param scriptStr
     * @param i
     * @return
     * @throws Exception
     */
    private boolean addScripts(String scriptStr, int i) throws Exception {

        boolean scriptFound;

        try {
            scriptFound = this.addSingleLineScripts(i, scriptStr);
        } catch (Exception ex) {
            logger.error("Unrecognized characters {" + scriptStr + "} at line no {" + (++i) + "}");
            scriptFound = false;
        }

        if (!scriptFound && (!StringUtil.isNullOrEmpty(scriptStr))) {

            EchoScript echoScript = new EchoScript();
            echoScript.setMessage(scriptStr);
            echoScript.setCandidateForEvaluation(false);
            this.addScript(i, echoScript);
        }
        return true;
    }

    private boolean addSingleLineScripts(int i, String scriptStr) throws Exception {

        Map<Class, SingleLineScript> cachedSingleLineScripts = SingleLineScriptFactory.getSingleLineScripts();

        for (Entry<Class, SingleLineScript> entry : cachedSingleLineScripts.entrySet()) {

            Method[] methods = entry.getKey().getDeclaredMethods();

            Method postCreateScriptMethod = null;
            Method singleTonSetterMethod = null;

            for (Method method : methods) {

                if (method.isAnnotationPresent(PostCreateScript.class)) {
                    postCreateScriptMethod = method;
                }
                if (method.isAnnotationPresent(SingleTonSetter.class)) {
                    singleTonSetterMethod = method;
                }

            }
            try {

                
                Map<Integer, String> lineMatcherMap = PatternMatcher.match(entry.getValue().getPatterns(), scriptStr);
                if (lineMatcherMap != null) {

                    SingleLineScript script = (SingleLineScript) entry.getValue().createScript(lineMatcherMap);
                    if (script != null && script instanceof SingleLineCommentScript) {
                        return true;
                    }

                    if (singleTonSetterMethod != null && script != null) {
                        singleTonSetterMethod.invoke(entry.getValue(), script);
                    }

                    if (script != null) {
                        script.setLineNumber(i + 1);
                        script.setOuterMultiLineScript(this);
                        if (postCreateScriptMethod != null) {
                            postCreateScriptMethod.invoke(script);
                        }
                        this.addScript(i, script);

                        return true;
                    }
                }
            } catch (Throwable ex) {
                ex.printStackTrace();
                logger.error("error while parsing at line no " + i + "{" + scriptStr + "} ..." + ex.getMessage());
                System.out.println("error while parsing at line no " + i + "{" + scriptStr + "} ..." + ex.getMessage());
                throw new Exception(ex);
            }
        }

        return false;
    }

    @Override
    public String getStartDelim() {

        return "{";
    }

    @Override
    public String getEndDelim() {

        return "}";
    }

    @Override
    public LinePattern getStartPattern() {
        return this.getPatternInternal(this.getStartDelim());
    }

    @Override
    public LinePattern getEndPattern() {
        return this.getPatternInternal(this.getEndDelim());
    }

    private LinePattern getPatternInternal(String delim) {
        StringBuilder sb = new StringBuilder();
        sb.append("\\s*\\");
        sb.append(delim);
        sb.append("\\s*");
        LinePattern linePattern = new LinePattern();
        linePattern.setSequence(1);
        linePattern.setPattern(Pattern.compile(sb.toString()));
        return linePattern;
    }

    public MultiLineContainer extractMultiLineContainer(Map<Integer, String> scriptMap, int x) throws Exception {
        MultiLineContainer mlc = new MultiLineContainer();
        boolean firstStartBracktFound = false;
        Stack<String> stack = new Stack<>();
        int start = x + 1;
        int end = -1;
        int i = x;
        for (; i < scriptMap.size(); i++) {
            Pattern multiLineStartPattern = this.getStartPattern().getPattern();
            Pattern multiLineEndPattern = this.getEndPattern().getPattern();
            String startDelim = getStartDelim();
            Map<Integer, String> startingBracketMap = PatternMatcher.match(
                    multiLineStartPattern,
                    scriptMap.get(i));

            if (startingBracketMap != null) {

                if (!firstStartBracktFound) {
                    firstStartBracktFound = true;
                    start = i;
                }
                stack.push(startDelim);

            }

            Map<Integer, String> endingBracketMap = PatternMatcher.match(
                    multiLineEndPattern,
                    scriptMap.get(i));

            if (endingBracketMap != null) {

                stack.pop();

            }

            if (stack.isEmpty()) {
                break;
            }

        }

        if (!stack.isEmpty() || !firstStartBracktFound) {
            logger.error("could not find matching {...} or /*...*/ sequence at line no {" + (x + 1) + "}");
            System.out.println("could not find matching {...} or /*...*/ sequence at line no {" + (x + 1) + "}");
            throw new Exception("could not find matching {...} or /*...*/ sequence at line no {" + (x + 1) + "}");
        }

        end = i;
        mlc.setStart(start);
        mlc.setEnd(end);

        return mlc;
    }

    @Override
    public IScript execute() throws Exception {
        throw new UnsupportedOperationException("Can't execute this. Check your implementation");
    }

    @Override
    public IScript copy() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected MultiLineScript findNearestOuterMLScript() {
        return this;

    }

    @Override
    public Integer getVerticalParserSequence() {
        ISequenceProvider sequenceProvider = AppContainer.getInstance().getBean(MultiLineScriptVerticalSequenceProvider.class);
        return sequenceProvider.getSequence(this.getClass());
    }

    @Override
    public int createScript(MultiLineScript multiLineScript, int i) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private FunctionNode rootFunctionNode;

    public FunctionNode getRootFunctionNode() {
        return rootFunctionNode;
    }

    public void setRootFunctionNode(FunctionNode rootFunctionNode) {
        this.rootFunctionNode = rootFunctionNode;
    }

    @Override
    public boolean isLoop() {
        return false;
    }
    private boolean breakApplied = false;
    
    private boolean returnOrdered = false;
    
    
    @Override
    public void applyBreak() {
        this.breakApplied = true;
    }
    @Override
    public void releaseBreak() {
        this.breakApplied = false;
    }
    @Override
    public boolean breakApplied() {
        return breakApplied;
    }

    @Override
    public void orderReturn() {
        this.returnOrdered = true;
    }

    @Override
    public void acceptReturn() {
        this.returnOrdered = false;
    }

    @Override
    public boolean returnOrdered() {
        return this.returnOrdered;
    }
    
    
    
    
    
}
