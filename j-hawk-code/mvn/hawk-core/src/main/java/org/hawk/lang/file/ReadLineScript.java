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

package org.hawk.lang.file;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.commons.io.IOUtil;
import org.common.di.AppContainer;

import org.hawk.executor.cache.singleline.globalvar.IGlobalVariableScriptCache;
import org.hawk.lang.IScript;
import org.hawk.lang.enumeration.VarTypeEnum;
import org.hawk.lang.grammar.SingleLineGrammar;
import org.hawk.lang.object.IObjectScript;
import org.hawk.lang.object.LocalVarDeclScript;
import org.hawk.lang.object.VariableDeclScript;
import org.hawk.lang.singleline.SingleLineScript;
import org.hawk.lang.singleline.pattern.LinePattern;
import org.hawk.lang.type.BooleanDataType;
import org.hawk.lang.type.StringDataType;
import org.hawk.lang.type.Variable;
import org.hawk.logger.HawkLogger;
import org.hawk.pattern.PatternMatcher;
import org.common.di.ScanMe;
import org.hawk.executor.cache.singleline.globalvar.GlobalVariableScriptCache;

/**
 *
 * @author msahu
 */
@ScanMe(true)
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

    
     public static class ReadLine extends SingleLineGrammar {

        @Override
        public String toString() {
            return "ReadLine" + "parsingSequence=" + this.getParsingSequence() + ", linePattern=" + this.getLinePattern() + '}';
        }

    }
        

    @Override
    public Integer getVerticalParserSequence() {

        return this.getGrammar().getSingleLine().getReadLine().getParsingSequence();
    }

    @Override
    public Set<LinePattern> getPatterns() {
       
        return this.getGrammar().getSingleLine().getReadLine().getLinePattern();
    } 


     
    @Override
    public IScript execute() throws Exception {


        IObjectScript rtnValue = LocalVarDeclScript.createDummyBooleanScript();
        Variable var = new Variable(VarTypeEnum.VAR,null,this.buffer);
        BooleanDataType rtn = new BooleanDataType(true);
        var.setValue(rtn);
        rtnValue.setVariable(var);
        rtnValue.setVariableValue(var);

        VariableDeclScript variableDeclScript = (VariableDeclScript)this.evaluateLocalVariable(this.buffer);
        if(variableDeclScript == null){
            throw new Exception("Could not recognize type of  {"+this.buffer+"}");
        }

        String data;
        try {
            data = IOUtil.readLineFromConsole();
        } catch (IOException ex) {
            Logger.getLogger(ReadLineScript.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception(ex);
        }
        Variable value = new Variable(VarTypeEnum.VAR, null, this.buffer);
        StringDataType sdt = new StringDataType(data);
        value.setValue(sdt);
        
        IGlobalVariableScriptCache si = AppContainer.getInstance().getBean(GlobalVariableScriptCache.class);
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
     * @param lineReadLineMatcherMap
     * @param lineEchoMatcherMap
     * @return
     */
    
    @Override
    public  ReadLineScript createScript(Map<Integer,String> lineReadLineMatcherMap){

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



    /*
    @Override
    public boolean isVariable() {
        return false;
    }
    */ 
    

  
     private static final Pattern VARIABLEPATTERN=Pattern.compile("([a-z|A-Z]{1,}[a-z|A-Z|\\d]*)");


}
