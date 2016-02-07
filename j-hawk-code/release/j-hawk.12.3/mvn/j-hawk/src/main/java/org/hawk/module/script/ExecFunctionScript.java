

package org.hawk.module.script;


import org.hawk.module.script.FunctionScript.FunctionInvocationInfo;
import java.util.Map.Entry;
import org.hawk.ds.Stack;
import org.hawk.exception.HawkException;
import org.hawk.module.annotation.CreateScript;
import org.hawk.module.annotation.ScriptPattern;
import java.util.Map;
import org.hawk.logger.HawkLogger;
import java.util.regex.Pattern;
import org.hawk.module.lib.SystemModule;
import org.hawk.module.script.type.BooleanDataType;
import static org.hawk.constant.HawkLanguageKeyWord.*;

/**
 *
 * @version 1.0 11 Apr, 2010
 * @author msahu
 */
public class ExecFunctionScript extends SingleLineScript{

    private static final HawkLogger logger = HawkLogger.getLogger(ExecFunctionScript.class.getName());
    private  static final Pattern EXEC_FUNCTION_PATTERN=
    Pattern.compile("\\s*"+exec+"\\s*([a-z|A-Z]{1,}[a-z|A-Z|\\d]*)\\s*\\(\\s*(.*)\\s*\\)\\s*");

    /**
     * ExecFunctionScript pattern.
     * @return returns ExecFunctionScript
     * @see ScriptPattern
     */
    @ScriptPattern
    public static Pattern getPattern(){
        return EXEC_FUNCTION_PATTERN;
    }

    @CreateScript
    public static ExecFunctionScript createScript(Map<Integer,String> lineFunctionMatcherMap) throws HawkException{

        if(lineFunctionMatcherMap == null){
            return null;
        }
        ExecFunctionScript functionScript = new ExecFunctionScript();
        functionScript.setFunctionName(lineFunctionMatcherMap.get(1));
        ExecParameterScript execParameterScript = (ExecParameterScript)ExecParameterScript.createScript(lineFunctionMatcherMap.get(2));
        functionScript.setExecParameterScript(execParameterScript);
        return functionScript;
    }

   

    @Override
    public void setOuterMultiLineScript(MultiLineScript outerMultiLineScript) {
        super.setOuterMultiLineScript(outerMultiLineScript);
        this.getExecParameterScript().setOuterMultiLineScript(this.getOuterMultiLineScript());
    }


    private String functionName;


    private ExecParameterScript execParameterScript = null;

    public ExecParameterScript getExecParameterScript() {
        return execParameterScript;
    }

    public void setExecParameterScript(ExecParameterScript execParameterScript) {
        this.execParameterScript = execParameterScript;
    }


    public Map<Integer,IScript> getParamMap(){
        return this.getExecParameterScript().getParamMap();
    }
    public Map<Integer, IScript> cloneParamMap() {
        
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

    protected String getMangledParams(){
        StringBuilder sb = new StringBuilder();
        for(Entry<Integer,IScript> entry:this.getParamMap().entrySet()){
            sb.append(entry.getValue().mangle());
        }
        return sb.toString();
    }

    @Override
    public IScript execute() throws HawkException {

        this.getExecParameterScript().execute();
        
        String mangledParams = this.getMangledParams();
        String mangledFunctionName = this.functionName+mangledParams;
        IScript rtnVar =  execute
                                (
                                    this.findEncapsulatingFunctionScript()
                                        .getOriginalFunctionName(),
                                    this.getLineNumber(),
                                    mangledFunctionName,
                                    this.getParamMap()
                                );
        if(!this.isLastScript()){
            rtnVar.getVariableValue().setRtrn(false);
        }
        return rtnVar;

    }
    public static IScript execute
            (
                String functionName,
                int lineNumber,
                String mangledFunctionName,
                Map<Integer,IScript> paramMap
            ) throws HawkException{

        FunctionScript functionScript = ScriptInterpreter.getInstance()
                                                         .findFunctionScript(mangledFunctionName);

        FunctionInvocationInfo functionInvocationInfo =new FunctionInvocationInfo(functionName,lineNumber);

                
        Stack<FunctionScript.FunctionInvocationInfo> functionStack = SystemModule.getFunctionStack().get();
        if(functionStack == null){
            functionStack = new Stack<FunctionScript.FunctionInvocationInfo>();
        }
        functionStack.push(functionInvocationInfo);
        SystemModule.getFunctionStack().set(functionStack);

        IScript rtn = LocalVarDeclScript.createDummyScript();
        rtn.getVariableValue().setVariableValue(new BooleanDataType(false));
        rtn = functionScript!= null
                    ?
                            functionScript.executeDefaultForLoopScript(paramMap)
                        :
                            rtn
                        ;
        functionStack.pop();
        
        return rtn;
    }

    @Override
    public boolean isVariable() {
        return false;
    }
}




