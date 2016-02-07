


package org.hawk.module.script;


import java.lang.reflect.Method;
import org.hawk.ds.Stack;
import org.hawk.exception.HawkException;
import org.hawk.pattern.PatternMatcher;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import org.hawk.logger.HawkLogger;
import java.util.regex.Pattern;
import org.hawk.module.annotation.CreateScript;
import org.hawk.module.annotation.Implementor;
import org.hawk.module.annotation.Implementors;
import org.hawk.module.annotation.MultiLinePattern;
import org.hawk.module.script.enumeration.ArrayTypeEnum;
import org.hawk.module.script.enumeration.VarTypeEnum;
import org.hawk.module.script.type.IDataType;
import org.hawk.module.script.type.Variable;
/**
 *
 * @version 1.0 10 Apr, 2010
 * @author msahu
 */
@Implementors
(
    {


            @Implementor(clazz="ForLoopScript",parsingSequence=1),
            @Implementor(clazz="WhileLoopScript",parsingSequence=2),
            @Implementor(clazz="DoWhileLoopScript",parsingSequence=3),
            @Implementor(clazz="IfScript",parsingSequence=4),
            @Implementor(clazz="MultiLineCommentScript",parsingSequence=5)


    }
)
@MultiLinePattern(startDelim="{",endDelim="}",startPattern="\\s*\\{\\s*",endPattern="\\s*\\}\\s*")
public class MultiLineScript extends AbstractScript{

    private static final HawkLogger logger = HawkLogger.getLogger(MultiLineScript.class.getName());

    
    protected Map<Integer,IScript> innerScripts = new TreeMap<Integer,IScript>();

    

    protected FunctionScript  functionScript = null;

    protected Stack<Map<Variable,IScript>> localVariableTableStack = new Stack<Map<Variable,IScript>>();

    protected Stack<Set<String>> localStructStack = new Stack<Set<String>>();

    protected boolean defaultMultiLineScript = false;


    


    protected MultiLineContainer multiLineContainer = null;

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
     * @param i line no at which script is present
     * @return <tt>true</tt>  if i is inside any multiline definition block
     *         <tt>false</tt> otherwise
     */
    public boolean isInside(int i,boolean shouldCheckFromMLCDefn){
         boolean status = false;
         if(this.getMultiLineContainer()!= null){
             int start = shouldCheckFromMLCDefn
                    ?
                        this.getMultiLineContainer().getMultiLineStart()
                        :
                        this.getMultiLineContainer().getStart();
             int end = this.getMultiLineContainer().getEnd();
             if(i>= start && (shouldCheckFromMLCDefn ? (i<= end):i<end)){
                    status = true;
             }
        }
         return status;
    }

    /**
     * This checks whether particular script is inside multiline script
     * definition block.
     * @param i line no at which script is present
     * @return <tt>true</tt>  if i is inside any multiline definition block
     *         <tt>false</tt> otherwise
     */
    public boolean isInside(int i){
        return this.isInside(i, false);
    }

    @Override
    public void pushLocalVars(){

        Map<Variable,IScript> localVariableTable = new LinkedHashMap<Variable,IScript>();
        this.localVariableTableStack.push(localVariableTable);
        Set<String> localStructs = new TreeSet<String>();
        this.localStructStack.push(localStructs);
        for(Entry<Integer,IScript> entry:this.innerScripts.entrySet()){
            entry.getValue().pushLocalVars();
        }
    }

    @Override
    public Map<String,IDataType> popLocalVars(){

        this.localVariableTableStack.pop();
        this.localStructStack.pop();
        for(Entry<Integer,IScript> entry:this.innerScripts.entrySet()){
            entry.getValue().popLocalVars();
        }
        return null;
    }


    public IScript getLocalValue(Variable localVar){
        return this.getLocalValueInternal(localVar.getVariableName());
    }

    private IScript getLocalValueInternal(String localVar){
        Map<Variable,IScript> localVarTable = this.localVariableTableStack.top();
        Set<String> localStructs = this.localStructStack.top();
        if( (localVarTable == null) &&  (localStructs == null || localStructs.isEmpty())){
            return null;
        }
        IScript result = null;
        Variable possibleKey = new Variable(VarTypeEnum.VAR,null,localVar);
        result = localVarTable.get(possibleKey);
        if(result == null){
            possibleKey.setVarTypeEnum(VarTypeEnum.STRUCT);

            for(String struct:localStructs){
                possibleKey.setStructName(struct);
                result = localVarTable.get(possibleKey);
                if(result != null){
                    break;
                }
            }
            if(result == null){
                   result = findArrayType(localVar, localVarTable);
            }
        }
        return result;
    }
    public  IScript findArrayType(String localVar,Map<Variable,IScript> localVarTable){
        IScript result = null;
        Variable possibleKey = new Variable(VarTypeEnum.VAR, null, localVar);

        possibleKey.setArrayType(ArrayTypeEnum.VAR);
        possibleKey.setVarTypeEnum(VarTypeEnum.VAR);

        result = localVarTable.get(possibleKey);
        if(result == null){
            Set<String> localStructs = this.localStructStack.top();
            possibleKey.setArrayType(ArrayTypeEnum.STRUCT);
            possibleKey.setVarTypeEnum(VarTypeEnum.STRUCT);
            for(String struct:localStructs){
                possibleKey.setStructName(struct);
                result = localVarTable.get(possibleKey);
                if(result != null){
                    break;
                }
            }
        }
        return result;
    }
    public IScript getLocalValue(String localVar){
        return this.getLocalValueInternal(localVar);
    }

    public void setLocalValue(Variable variable,IScript localValue){
        Map<Variable,IScript> localVarTable = this.localVariableTableStack.top();
        Set<String> localStructs = this.localStructStack.top();
        

        if(localVarTable != null){
            localVarTable.put(variable,localValue);
            if(
                variable.getVarTypeEnum()!= null
                    &&
                variable.getVarTypeEnum() == VarTypeEnum.STRUCT
                    &&
                variable.getStructName()!= null
              )
            localStructs.add(variable.getStructName());
        }


    }

    public void unsetLocalValue(Variable localVar){
        Map<String,IDataType> localVarTable = this.localVariableTableStack.top();
        if(localVarTable != null){
            localVarTable.remove(localVar);
        }
        Set<String> localStructs = this.localStructStack.top();
        if(localStructs != null){
            StringBuilder key = new StringBuilder();
            for(String structName:localStructs){
                key.append(structName);
                key.append(localVar);
                if(localVarTable.remove(key) != null){
                    break;
                }
            }
        }
    }

    public void unsetAllLocalValue(){
        Map<String,IDataType> localVarTable = this.localVariableTableStack.top();
        if(localVarTable != null){
            localVarTable.clear();
        }
        Set<String> localStructs = this.localStructStack.top();
        localStructs.clear();
    }

    public boolean isLocalVarDeclared(Variable localVar){

        IScript result = this.getLocalValue(localVar);
        return result == null
                        ?
                                false
                            :
                                result.getVariable().getVariableValue()!= null
                        ;
    }

    public StructureScript getStructVariable(String structVariable){
        if(structVariable == null || structVariable.isEmpty()){
            return null;
        }
        StructureScript result = null;
        for(Entry<Integer,IScript>entry:this.innerScripts.entrySet()){
            if(entry.getValue() instanceof StructureScript){
                StructureScript thatScript = (StructureScript)entry.getValue();
                if(structVariable.equals(thatScript.getStructVarName().getVariableName())){
                    result = thatScript;
                    break;
                }
            }
        }
        return result;
    }






    public boolean isDefaultMultiLineScript() {
        return defaultMultiLineScript;
    }

    public void setDefaultMultiLineScript(boolean defaultMultiLineScript) {
        this.defaultMultiLineScript = defaultMultiLineScript;
    }

    

    public FunctionScript getFunctionScript() {
        return functionScript;
    }

    public void setFunctionScript(FunctionScript functionScript) {
        this.functionScript = functionScript;
        this.defaultMultiLineScript =true;
    }


    

    

    public boolean addScript(Integer line,AbstractScript script){
        if(script != null){
            this.innerScripts.put(line, script);
            return true;
        }
        return false;
    }

    


    public static boolean parseMultiLines(Map<Integer,String> scriptMap,int x , int y,MultiLineScript multiLineScript) throws HawkException{

        if(x==y){
            logger.info("warning....empty method found...");
            return true;
        }
        boolean parsed = true;
        int lastLine = -1;
        for(int i = x; i<y;){

            i = addScripts(multiLineScript, i);
            lastLine = i-1;
        }
        
        AbstractScript lastScript = (AbstractScript)multiLineScript.getInnerScripts().get(lastLine);
        if(lastScript != null){
            lastScript.setLastScript(true);
        }
        return parsed;
    }

    public static int addScripts(MultiLineScript multiLineScript,int i) throws HawkException{
        Map<Class,MultiLineScript> cachedMultiLineScripts = MultiLineScriptFactory.getMultiLineScripts();
        boolean multiLineScriptFound = false;
        for(Entry<Class,MultiLineScript> entry:cachedMultiLineScripts.entrySet()){
            Method[] methods = entry.getKey().getMethods();
            Method createScriptMethod = null;
            for(Method method:methods){
                if(method.isAnnotationPresent(CreateScript.class)){
                    createScriptMethod  = method;
                    break;
                }
            }
            if(createScriptMethod != null){
                try{
                    int nextLine =  -1;
                    nextLine = (Integer)createScriptMethod.invoke(entry.getValue(), multiLineScript,i);
                    if(nextLine != -1){
                        i = nextLine;
                        multiLineScriptFound = true;
                        break;
                    }
                } catch (Throwable ex) {
                    //ex.getCause().printStackTrace();
                    //logger.severe(ex.getCause().getMessage());
                    throw new HawkException(ex.getCause().getMessage());
                }
            }else{
                throw new HawkException("Invalid multiline script");
            }

        }

        if(!multiLineScriptFound){
            Map<Integer,String> scriptMap = ScriptInterpreter.getInstance().getScriptMap();
            boolean parsed  = SingleLineScript.addScripts(i, scriptMap.get(i), multiLineScript);
            if(!parsed){
                throw new HawkException("Unrecognizable characters"+scriptMap.get(i)+" at {"+i+"}");
            }
            i++;
        }
        return i;
    }

    public static String getStartDelim(Class multiLineClazz){
        String result = null;
        MultiLinePattern multiLinePattern = getMultiLinePatternAnnotation(multiLineClazz);
        if(multiLinePattern != null){
            result = multiLinePattern.startDelim();
        }
        return result;
    }
    public static String getEndDelim(Class multiLineClazz){
        String result = null;
        MultiLinePattern multiLinePattern = getMultiLinePatternAnnotation(multiLineClazz);
        if(multiLinePattern != null){
            result = multiLinePattern.endDelim();
        }
        return result;
    }
    public static Pattern getStartPattern(Class multiLineClazz){
        Pattern result = null;
        MultiLinePattern multiLinePattern = getMultiLinePatternAnnotation(multiLineClazz);
        if(multiLinePattern != null){
            String startPatternStr = multiLinePattern.startPattern();
            result = Pattern.compile(startPatternStr);
        }
        return result;
    }
    public static Pattern getEndPattern(Class multiLineClazz){
        Pattern result = null;
        MultiLinePattern multiLinePattern = getMultiLinePatternAnnotation(multiLineClazz);
        if(multiLinePattern != null){
            String endPatternStr = multiLinePattern.endPattern();
            result = Pattern.compile(endPatternStr);
        }
        return result;
    }
    private static MultiLinePattern getMultiLinePatternAnnotation(Class multiLineClazz){
        
        MultiLinePattern result = null;
        do{
            if(multiLineClazz.isAnnotationPresent(MultiLinePattern.class)){
                result = (MultiLinePattern)multiLineClazz.getAnnotation(MultiLinePattern.class);
                break;
            }else{
                multiLineClazz = multiLineClazz.getSuperclass();
            }
        }while(true);

        return result;
    }
    public static MultiLineContainer extractMultiLineContainer(Class multiLineClazz,Map<Integer,String> scriptMap,int x)    throws HawkException{
        MultiLineContainer mlc = new MultiLineContainer();
        boolean firstStartBracktFound = false;
        Stack<String> stack = new Stack<String>();
        int start = x+1;
        int end = -1;
        int i = x;
        for(;i<scriptMap.size();i++){
            Pattern multiLineStartPattern = getStartPattern(multiLineClazz);
            Pattern multiLineEndPattern = getEndPattern(multiLineClazz);
            String startDelim = getStartDelim(multiLineClazz);
            Map<Integer,String> startingBracketMap = PatternMatcher.match
                                                                    (
                                                                        multiLineStartPattern,
                                                                        scriptMap.get(i)
                                                                    );

            if(startingBracketMap!=null){

                    if(!firstStartBracktFound){
                        firstStartBracktFound = true;
                        start = i;
                    }
                    stack.push(startDelim);

            }

            Map<Integer,String> endingBracketMap = PatternMatcher.match
                                                                    (
                                                                        multiLineEndPattern,
                                                                        scriptMap.get(i)
                                                                    );

            if(endingBracketMap!=null){

                    stack.pop();

            }

            if(stack.isEmpty()){
                break;
            }


        }

        if(!stack.isEmpty() || !firstStartBracktFound){
            logger.severe("could not find matching {...} or /*...*/ sequence at line no {"+(x+1)+"}");
            System.out.println("could not find matching {...} or /*...*/ sequence at line no {"+(x+1)+"}");
            throw new HawkException ("could not find matching {...} or /*...*/ sequence at line no {"+(x+1)+"}");
        }

        end = i;
        mlc.setStart(start);
        mlc.setEnd(end);

        return mlc;
    }

    public IScript execute() throws HawkException {
        throw new UnsupportedOperationException("Can't execute this. Check your implementation");
    }

    @Override
    public boolean isVariable() {
        return false;
    }

    public Variable getVariable() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setVariableValue(Variable value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Variable getVariableValue() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setVariable(Variable value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public IScript copy() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected MultiLineScript findNearestOuterMLScript() {
        return this;

    }

    @Override
    public String toUI() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    }




