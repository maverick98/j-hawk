

package org.hawk.module.script;

import org.hawk.util.HawkUtil;
import org.hawk.pattern.PatternMatcher;
import java.util.Map;
import org.hawk.logger.HawkLogger;
import java.util.regex.Pattern;
import org.hawk.exception.HawkException;
import org.hawk.module.annotation.CreateScript;
import org.hawk.module.annotation.ScriptPattern;
import org.hawk.module.script.enumeration.VarTypeEnum;
import org.hawk.module.script.type.BooleanDataType;
import org.hawk.module.script.type.StringDataType;
import org.hawk.module.script.type.Variable;
import static org.hawk.constant.HawkLanguageKeyWord.*;

/**
 *
 * @author msahu
 */
public class ReadLineScript extends SingleLineScript{

    private static final HawkLogger logger = HawkLogger.getLogger(ReadLineScript.class.getName());

    /**
     * Default CTOR
     */
    public ReadLineScript(){

    }




    /**
     * list of messages of  <tt>ReadLine</tt> script.
     */
    private String inputStream;

    private String buffer;

    public String getBuffer() {
        return buffer;
    }

    public void setBuffer(String buffer) {
        this.buffer = buffer;
    }

    

    public String getInputStream() {
        return inputStream;
    }

    public void setInputStream(String inputStream) {
        this.inputStream = inputStream;
    }

    



    /**
     * Echo script pattern.
     * @return returns echo script pattern
     * @see ScriptPattern
     */
    @ScriptPattern
    public static Pattern getPattern() {
        return READPATTERN;
    }
    @Override
    public IScript execute() throws HawkException {


        IScript rtnValue = LocalVarDeclScript.createDummyScript();
        Variable var = new Variable(VarTypeEnum.VAR,null,this.buffer);
        BooleanDataType rtn = new BooleanDataType(true);
        var.setVariableValue(rtn);
        rtnValue.setVariable(var);
        rtnValue.setVariableValue(var);

        VariableDeclScript variableDeclScript = (VariableDeclScript)this.evaluateLocalVariable(this.buffer);
        if(variableDeclScript == null){
            throw new HawkException("Could not recognize type of  {"+this.buffer+"}");
        }

        String data = HawkUtil.readLineFromConsole();
        Variable value = new Variable(VarTypeEnum.VAR, null, this.buffer);
        StringDataType sdt = new StringDataType(data);
        value.setVariableValue(sdt);
        
        ScriptInterpreter si = ScriptInterpreter.getInstance();
        if(si.isGlobalVarDeclared(var)){
            VariableDeclScript script = new VariableDeclScript();
            script.setVariable(var);
            script.setVariableValue(value);
            si.setGlobalValue(var, script);
            return rtnValue;
        }
        variableDeclScript.setVariableValue(value);

        return rtnValue;
    }

    



    /**
     * This returns <tt>ReadLineScript</tt> from echo matcher map.
     * @param lineEchoMatcherMap
     * @return
     */
    @CreateScript
    public static ReadLineScript createScript(Map<Integer,String> lineReadLineMatcherMap){

        if(lineReadLineMatcherMap == null){
            return null;
        }
        ReadLineScript readLineScript =null;
        String grp1 = lineReadLineMatcherMap.get(1);
        String grp2 = lineReadLineMatcherMap.get(2);
        if(grp2!= null && !grp2.isEmpty()){

            Map<Integer,String> bufferMap = PatternMatcher.match(VARIABLEPATTERN, grp2);
            if(bufferMap == null){
                return null;
            }
            readLineScript = new ReadLineScript();
            readLineScript.setInputStream(grp1);
            readLineScript.setBuffer(grp2);
        }else{
            readLineScript = new ReadLineScript();
            readLineScript.setInputStream(null);
            readLineScript.setBuffer(grp1);
        }
        return readLineScript;
    }




    @Override
    public boolean isVariable() {
        return false;
    }

    /**
     * The primary echo RegEx pattern to parse echo script
     */
     private static final Pattern READPATTERN=Pattern.compile("\\s*"+readLine+"\\s*([a-z|A-Z]{1,}[a-z|A-Z|\\d]*)\\s*(.*)\\s*");

     private static final Pattern VARIABLEPATTERN=Pattern.compile("([a-z|A-Z]{1,}[a-z|A-Z|\\d]*)");


}
