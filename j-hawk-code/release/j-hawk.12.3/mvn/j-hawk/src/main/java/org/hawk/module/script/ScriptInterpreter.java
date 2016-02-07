


package org.hawk.module.script;


import org.hawk.data.perf.HawkPerfDataCollector;
import org.hawk.module.IModule;
import org.hawk.module.annotation.SubTask;
import org.hawk.module.script.enumeration.BuildModeEnum;
import org.hawk.module.task.SubTaskContainer;
import org.hawk.pattern.PatternMatcher;
import org.hawk.startup.HawkStartup;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import static org.hawk.constant.HawkConstant.*;
import org.hawk.logger.HawkLogger;
import org.hawk.exception.*;
import org.hawk.module.script.enumeration.VarTypeEnum;
import org.hawk.module.script.type.Variable;
import org.hawk.shutdown.HawkShutdown;
import org.hawk.util.HawkUtil;


/**
 * <p>This parses the hawk script and runs the same.
 * This reads through the hawk file and parses the function definition and
 * hence creates the function script out of them.
 * The function script contains all the scripts written inside the function definition.
 *
 * <p>This is the place where all the global variables and aliases are stored.
 *
 * <p>This parses through target application's <tt>IModule</tt> implementations
 * to cache all the modules <tt>IModule</tt> and subtask <tt>SubTaskContainer</tt>
 * Note: The developer has to annotate the tasks with <tt>SubTask</tt> to register with
 * Hawk framework as subtask failing to which Hawk will ignore the same.
 *
 * 
 * @VERSION 1.0 7 Apr, 2010
 * @author msahu
 * @see ScriptUsage
 * @see IModule
 * @see SubTaskContainer
 * @see SubTask
 */
public class ScriptInterpreter {

    private static final HawkLogger logger = HawkLogger.getLogger(ScriptInterpreter.class.getName());

    /**
     * Creates the singleton instance of <tt>ScriptInterpreter</tt>
     */
    private static final ScriptInterpreter scriptInterpreter = new ScriptInterpreter();

    /**
     * Private CTOR of <tt>ScriptInterpreter</tt>
     */
    private ScriptInterpreter(){

    }

    /**
     * SingleTon accessor method of <tt>ScriptInterpreter</tt>
     * @return
     */
    public static ScriptInterpreter getInstance(){

        return scriptInterpreter;

    }


    
    /**
     * This stores the entire hawk script file's contents
     * in a map with key value as line no and line respectively.
     */
    private  Map<Integer,String> scriptMap = null;

    
    /**
     * A map containing key as function name
     * and value as <tt>MultiLineContainer</tt>
     * @see MultiLineContainer
     */
    private  Map<String,StructureDefnScript> structureDefnMap = new HashMap<String,StructureDefnScript>();

    /**
     * A map containing key as function name
     * and value as <tt>FunctionScript</tt>
     * @see FunctionScript
     */
    private  Map<String,FunctionScript> functionScriptMap = null;

  
    /**
     * Global map containing global var and its
     * value
     * @see VariableDeclScript
     */
    protected Map<Variable,IScript> globalVariableTable = new LinkedHashMap<Variable,IScript>();


    /**
     * map containing multiline globals
     */
    protected Map<Integer,Integer> globalMultiLineScriptMap = null;
    /**
     * A flag to check whether a hawk script can be interpreted.
     */
    private  boolean iterpretable = false;

    /**
     * A reference to main function which is the entry point
     * of hawk script execution.
     */
    private  FunctionScript mainFunction = null;

    public Map<Integer, String> getScriptMap() {
        return scriptMap;
    }

    public void setScriptMap(Map<Integer, String> scriptMap) {
        this.scriptMap = scriptMap;
    }


    /**
     * Getter for global variable
     * @param globalVar
     * @return getter for global variable
     */
    public IScript getGlobalValue(String globalVar){

        if(this.globalVariableTable == null && this.globalVariableTable.isEmpty()){

            return null;

        }

        Variable variable = new Variable(VarTypeEnum.VAR,null,globalVar);

        return this.globalVariableTable.get(variable);
    }

    /**
     * Setter for global variable
     * @param globalVar
     * @param globalValue
     */
    public void setGlobalValue(Variable globalVar,IScript globalValue){
        
        this.globalVariableTable.put(globalVar,globalValue);
    }

    /**
     * Removes the global variable from the global variable map.
     * @param globalVar the variable to be removed.
     */
    public void unsetGlobalValue(String globalVar){

        Variable variable = new Variable(VarTypeEnum.VAR,null,globalVar);

        this.globalVariableTable.remove(variable);
    }

    /**
     * Checks whether this global variable is declared.
     * @param globalVar
     * @return <tt>true</tt> on success <tt>false</tt> on failure
     */
    public boolean isGlobalVarDeclared(Variable globalVar){

        return this.globalVariableTable.containsKey(globalVar);
    }

    
    

    
   /**
     * This caches the structure definition
     * @return a map containing key as structure name
     * and value as <tt>StructureDefnScript</tt>
     * @throws org.hawk.exception.HawkException
     * @see StructureDefnScript
     */
    public  Map<String,StructureDefnScript> cacheStructureDefnMap() throws HawkException{

        Map<String,Map<String,Object>> xmlStructMap = XMLStructIncludeScript.getInstance().getXmlStructMap();

        StructureDefnScript.parseStructuresMap(xmlStructMap,this.structureDefnMap);

        StructureDefnScript.parseStructures(this.scriptMap,this.structureDefnMap);

        return this.structureDefnMap;
    }

    /**
     * This checks if structName is defined in the hawk script
     * @param structName
     * @return true if structName is defined otherwise false
     * @throws org.hawk.exception.HawkException
     */
    public boolean doesStructExist(String structName) throws HawkException{

        if(structName == null || structName.isEmpty()){

            throw new HawkException("Invalid struct name");

        }

        return this.getStructureDefnMap().containsKey(structName);
    }

    /**
     * This checks if a structName and it's member structMember are defined in the hawk script
     * @param structName
     * @param structMember
     * @return
     * @throws org.hawk.exception.HawkException
     */
    public boolean doesStructMemberExist(String structName,String structMember) throws HawkException{

        if(structName == null || structName.isEmpty()){

            throw new HawkException("Invalid struct name");

        }

        if(structMember == null || structMember.isEmpty()){

            throw new HawkException("Invalid struct member");

        }

        boolean status = false;

        if(this.doesStructExist(structName)){

            StructureDefnScript structDefnScript = this.getStructureDefnMap().get(structName);

            status = structDefnScript.doesMemberExist(structMember);

        }else{

            status = false;

        }

        return status;
    }

    /**
     * This returns the cached structure-><tt>StructureDefnScript</tt> map.
     * If it is not cached it invokes {@link ScriptInterpreter#cacheStructureDefnMap}
     * @return a map containing key as structure name
     * and value as <tt>StructureDefnScript</tt>
     * @throws org.hawk.exception.HawkException
     */
    public  Map<String,StructureDefnScript> getStructureDefnMap() throws HawkException{

        if(this.structureDefnMap != null && !(this.structureDefnMap.isEmpty())){

            return this.structureDefnMap;

        }

        return this.cacheStructureDefnMap();
    }

    
    /**
     * This clones input <tt>FunctionScript</tt>.
     * This is used in parallel execution of hawk functions
     * @see ExecParallelSingleLineScript
     * @param functionScript
     * @return
     */
    public FunctionScript cloneFunctionScript(FunctionScript functionScript){

        if(functionScript == null){

            return null;

        }

        FunctionScript clonedFunctionScript = functionScript.createFunctionTemplate();

        try {

            this.createFunctionScript(functionScript.mangle(), clonedFunctionScript);

        } catch (HawkException ex) {

            logger.info(ex.getMessage());

            return null;

        }

        return clonedFunctionScript;
    }

    public  Map<String,FunctionScript> parseFunctionScriptMap() throws HawkException{

        this.functionScriptMap = FunctionScript.parseFunctions(this.scriptMap);

        return this.functionScriptMap;
    }
    
    public  boolean cacheAliases() throws HawkException{

        return AliasScript.getInstance().parseAliases(this.scriptMap);
    }

    public  boolean cacheXMLStructs() throws HawkException{

        return XMLStructIncludeScript.getInstance().parseXMLStructs(this.scriptMap);
    }

    public boolean cacheMultiLineCommentBoundaries() throws HawkException {

        this.globalMultiLineScriptMap = MultiLineCommentScript.cacheGlobalMultiLineScripts();

        return true;
    }

    public Map<Integer,Integer> getMultiLineCommentBoundaries() throws HawkException{

        if(this.globalMultiLineScriptMap == null){

            this.cacheMultiLineCommentBoundaries();

        }

        return this.globalMultiLineScriptMap;
    }
    /**
     * This caches the function script map
     * @return a map containing key as function name
     * and value as <tt>FunctionScript</tt>
     * @see FunctionScript
     * @throws org.hawk.exception.HawkException
     */
    public  Map<String,FunctionScript> cacheFunctionScriptMap() throws HawkException{

        return cacheFunctionScriptMap(true);
    }
    /**
     * This caches the function script map
     * @return a map containing key as function name
     * and value as <tt>FunctionScript</tt>
     * @see FunctionScript
     * @throws org.hawk.exception.HawkException
     */
    public  Map<String,FunctionScript> cacheFunctionScriptMap(boolean shouldParseDetails) throws HawkException{

        if(shouldParseDetails){

            this.parseFunctionScriptMap();

        }

        for(Entry<String,FunctionScript> entry: this.functionScriptMap.entrySet()){

            if(this.createFunctionScript(entry.getKey(),entry.getValue())){

                this.functionScriptMap.put(entry.getKey(),entry.getValue());

            }

        }

        return this.functionScriptMap;
    }

    public boolean createFunctionScript(String functionName, FunctionScript functionScript) throws HawkException {

        ForLoopScript defaultForLoopScript = new ForLoopScript();

        defaultForLoopScript.setDefaultForLoop(true);

        functionScript.initializeParams();

        functionScript.setFunctionName(functionName);

        functionScript.setDefaultForLoopScript(defaultForLoopScript);

        defaultForLoopScript.setFunctionScript(functionScript);

        boolean parsed = false;

        try {
            parsed = MultiLineScript.parseMultiLines
                                        (
                                            this.scriptMap,

                                            functionScript.getMultiLineContainer().getStart() + 1,

                                            functionScript.getMultiLineContainer().getEnd(),

                                            defaultForLoopScript
                                        );

            if (parsed) {

                if (functionScript.isMainFunction()) {

                    this.iterpretable = true;

                    this.mainFunction = functionScript;
                }
                
            } else {
                throw new HawkException("parsing failure");
            }
        } catch (HawkException ex) {
            throw new HawkException("compilation failed..."+ex.getMessage());
        }

        return true;
    }


    /**
     * This finds the <tt>FunctionScript</tt> for the input mangled function name
     * When invoked in threads other than main thread, this method clones the function
     * script and returns the same.
     * @param mangledFunctionName
     * @return returns <tt>FunctionScript</tt>
     * @throws HawkException 
     * @see FunctionScript
     */
    public FunctionScript findFunctionScript(String mangledFunctionName) throws HawkException{

        if(mangledFunctionName == null || mangledFunctionName.isEmpty()){

            throw new HawkException("invalid function name");

        }

        FunctionScript functionScript = this.getFunctionScriptMap().get(mangledFunctionName);

        if(functionScript == null){

            throw new HawkException("could not find function {"+mangledFunctionName.substring(0,mangledFunctionName.indexOf("_"))+"}");

        }

        if(!HawkUtil.isMainThread()){

            logger.info("Executing in thread {"+Thread.currentThread().getName()+"}");

            functionScript = this.cloneFunctionScript(functionScript);

        }

        return functionScript;
    }
    /**
     * This returns the cached function-><tt>FunctionScript</tt> map.
     * If it is not cached it invokes {@link ScriptInterpreter#cacheFunctionScriptMap}
     * @return a map containing key as function name
     * and value as <tt>FunctionScript</tt>
     * @throws org.hawk.exception.HawkException
     */
    public  Map<String,FunctionScript> getFunctionScriptMap() throws HawkException{

        if(this.functionScriptMap != null && !(this.functionScriptMap.isEmpty())){

            return this.functionScriptMap;

        }

        return cacheFunctionScriptMap();
    }

    /**
     * This caches all the global variables declared
     * @return
     * @throws org.hawk.exception.HawkException
     */
    public boolean cacheGlobalVariable() throws HawkException{

        boolean status = true;

        for(int i=0;i<this.scriptMap.size();i++){

            String scriptStr = this.scriptMap.get(i);

            if(!this.isInsideMultiLineScript(i)){

                 Map<Integer,String> lineCommentMatcherMap = null;

                 Map<Integer,String> lineAliasMatcherMap = null;

                 Map<Integer,String> lineXMLStructMatcherMap = null;

                 Map<Integer,String> lineGlobalVarMatcherMap = PatternMatcher.match
                                                                            (

                                                                                VariableDeclScript.getPattern(),

                                                                                scriptStr
                                                                            );

                 if(lineGlobalVarMatcherMap != null){

                     String globalVar = lineGlobalVarMatcherMap.get(1);

                     String globalVarExp = lineGlobalVarMatcherMap.get(2);

                     //FIX ME struct can be global vars....

                     Variable variable = new Variable(VarTypeEnum.VAR,null,globalVar);

                     VariableDeclScript globalVarScript = new VariableDeclScript();

                     globalVarScript.setVariable(variable);

                     globalVarScript.setVariableValue(globalVarScript.evaluateGlobalVariable(globalVarExp).getVariableValue());

                     globalVarScript.getVariable().setVariableValue(globalVarScript.getVariableValue().getVariableValue());

                     this.setGlobalValue(variable, globalVarScript);

                 }else {
                
                      lineCommentMatcherMap = PatternMatcher.match
                                                            (

                                                                SingleLineCommentScript.getPattern(),

                                                                scriptStr
                                                            );
                      lineAliasMatcherMap = PatternMatcher.match
                                                            (

                                                                AliasScript.getPattern(),

                                                                scriptStr
                                                            );
                      lineXMLStructMatcherMap = PatternMatcher.match(XMLStructIncludeScript.getPattern(), scriptStr);

                }

                if(
                        (lineGlobalVarMatcherMap== null && lineCommentMatcherMap==null
                            &&
                         lineAliasMatcherMap == null && lineXMLStructMatcherMap == null)
                            &&
                        (scriptStr!=null && !scriptStr.trim().isEmpty())
                  ){
                    throw new HawkException ("unrecognized characters {"+scriptStr+"} at line no {"+(++i)+"}");
                }
            }
            
        }
        return status;
    }

    public boolean isInsideMultiLineScript(int i){

        return this.isInsideFunctionScript(i) || this.isInsideStructureScript(i) || this.isInsideMultiLineCommantScript(i);
    }

    /**
     * This checks whether particular script is inside any function
     * definition block.
     * @param i line no at which script is present
     * @return <tt>true</tt>  if i is inside any function definition block
     *         <tt>false</tt> otherwise
     */
    public boolean isInsideFunctionScript(int i){

        boolean status = false;

        if(this.functionScriptMap != null){

            for(Entry<String,FunctionScript> entry:this.functionScriptMap.entrySet()){

               status = entry.getValue().isInside(i,true);

               if(status){

                   break;

               }

            }

        }

        return status;

    }

    /**
     * This checks whether particular script is inside any structure
     * definition block.
     * @param i line no at which script is present
     * @return <tt>true</tt>  if i is inside any structure definition block
     *         <tt>false</tt> otherwise
     */
    public boolean isInsideStructureScript(int i){

        boolean status = false;

        if(this.structureDefnMap!= null){

            for(Entry<String,StructureDefnScript> entry:this.structureDefnMap.entrySet()){

               status = entry.getValue().isInside(i,true);

               if(status){

                   break;

               }

            }
        }


        return status;

    }

    /**
     * 
     * @param i
     * @return
     */
    public boolean isInsideMultiLineCommantScript(int i){
        boolean status = false;

        if(this.globalMultiLineScriptMap!= null){

            for(Entry<Integer,Integer> entry:this.globalMultiLineScriptMap.entrySet()){

               status = (i>= entry.getKey() && i<=entry.getValue());

               if(status){

                   break;

               }

            }

        }

        return status;
    }

    /**
     * This checks file type extension of the input hawk script file
     * @param scriptFile hawk script file
     * @return <tt>true</tt>  if the hawk script file has .hawk extension
     *         
     * @throws org.hawk.exception.HawkException
     * if the hawk script file does not have .hawk extension
     */
    private  boolean validateHawkScript(String scriptFile) throws HawkException{

        if(!scriptFile.endsWith(".hawk")){

            throw new HawkException("Unrecognized hawk script file");
        }

        return true;
    }

    /**
     * This interprets the hawk script file.
     * @return
     * @throws org.hawk.exception.HawkException
     * if there is no main function defined in hawk script
     * or if the script is not interpretable.
     */
    public  boolean interpret() throws HawkException{

        System.out.println("I am interpreting...");
        boolean status = false;

        if(this.mainFunction == null || !this.iterpretable){

            throw new HawkException("Could not find main function");
        }

        this.mainFunction.executeDefaultForLoopScript(null);

        if(ScriptUsage.getInstance().getBuildMode() == BuildModeEnum.PERF){

            HawkPerfDataCollector.dump(true);

            status = HawkPerfDataCollector.processPerfData();

        }

        return status;

    }

    /**
     * This parses the hawk script file for syntax check.
     * @param scriptFile hawk script file
     * @return <tt>true</tt> if syntax check is successful
     *         <tt>false</tt> otherwise
     * @throws org.hawk.exception.HawkException
     *  if it is not valid hawk script file
     */
    public  boolean compile(String scriptFile) throws HawkException{


        if(this.validateHawkScript(scriptFile)){

            logger.info("Compiling {"+scriptFile+"}");

            this.readScript(scriptFile);

            this.cacheXMLStructs();

            this.cacheStructureDefnMap();

            this.parseFunctionScriptMap();

            this.cacheAliases();

            this.cacheMultiLineCommentBoundaries();

            this.cacheFunctionScriptMap();

            this.cacheGlobalVariable();

        }else{
            throw new HawkException("Unrecognized hawk script file");
        }
        

        return true;
    }

    /**
     * This reads the hawk script file and returns them as a map
     * with line no and script as key value pair respectively.
     * @param scriptFile hawk script file
     * @return a map containing line no and the script
     */
    private  Map<Integer,String> readScript(String scriptFile){

        this.scriptMap = HawkUtil.dumpFileToMap(scriptFile);

        return this.scriptMap;
    }



    /**
     * Entry point of hawk script interpretation
     * @param args command line arguments as per java standards
     */
    public static void main(String args[]){
        
        Runtime.getRuntime().addShutdownHook(new Thread(new HawkShutdown()));
        
        boolean status = false;

        try{

            status = ScriptUsage.getInstance().parseArguments(args);

        }catch(Throwable ex){

            logger.info(ex.getMessage());

            return ;

        }

        if(!status){

            logger.info("Invalid option");

            ScriptUsage.getInstance().helpUser();

            return ;

        }

        BuildModeEnum buildMode = ScriptUsage.getInstance().getBuildMode();

        HawkStartup.getInstance().startUp();

        if(buildMode == BuildModeEnum.PERF){


        }else if(buildMode == BuildModeEnum.TRAINING){

            ScriptUsage.getInstance().showTasks();

            return ;

        }else if(buildMode == null ||buildMode == BuildModeEnum.HELP){

            ScriptUsage.getInstance().helpUser();

            return ;

        }else if(buildMode == BuildModeEnum.VERSION){

            System.out.println("hawk version "+VERSION);

            System.out.println("hawk svn revision no "+SVN_REVN_NO);

            return ;

        }


        ScriptInterpreter si = ScriptInterpreter.getInstance();

        boolean compilationStatus = false;

        try{

            String hawkScript = ScriptUsage.getInstance().getHawkScript();

            compilationStatus = si.compile(hawkScript);

        }catch(HawkException ex){

            //ex.printStackTrace();

            System.out.println("failed to compile ..."+ex.getMessage());

            logger.severe("failed to compile ..."+ex.getMessage());

            return ;
        }

        if(buildMode == BuildModeEnum.COMPILE){
            
            logger.info("successfully compiled !!!");

            return;
        }else if(compilationStatus){

            try {

                status = si.interpret();
                 
            } catch (Throwable th) {
                
                System.out.println(th.getMessage());

                logger.severe("failed during hawk script interpretation..."+th.getMessage());
                
            }

            System.exit(status?0:1);

        }else{

            logger.info("hawk script compilation failed....");
        }
        

    }

    
}




