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

package org.hawk.lang.console;


import java.util.Map;
import java.util.Set;
import org.common.di.AppContainer;

import org.hawk.lang.IScript;
import org.hawk.lang.grammar.SingleLineGrammar;
import org.hawk.lang.object.LocalVarDeclScript;
import org.hawk.lang.singleline.SingleLineScript;
import org.hawk.lang.singleline.pattern.LinePattern;
import org.hawk.logger.HawkLogger;
import org.hawk.output.HawkOutput;
import org.common.di.ScanMe;
/**
 * <p>This echoes message in the console 
 * e.g.<br>
 *
 *
 * for(var i = 1 ; i <= 100 ; i = i +1)<br>
 * {<br>
 *          if ( i == 50 )<br>
 *              {<br>
 *                  <h3>echo "Stop here"<br></h3>
 *                  break<br>
 *              }<br>
 *
 * }<br>
 *
 * @version 1.0 1 May, 2010
 * @author msahu
 * 
 */
@ScanMe(true)
public class EchoScript extends SingleLineScript{

    private static final HawkLogger logger = HawkLogger.getLogger(EchoScript.class.getName());

    /**
     * Default CTOR
     */
    public EchoScript(){
        
    }


    private boolean candidateForEvaluation = true;

    /**
     * list of messages of  <tt>echo</tt> script.
     */
    private String message;

    public boolean isCandidateForEvaluation() {
        return candidateForEvaluation;
    }

    public void setCandidateForEvaluation(boolean candidateForEvaluation) {
        this.candidateForEvaluation = candidateForEvaluation;
    }

    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

     public static class Echo extends SingleLineGrammar {

        @Override
        public String toString() {
            return "Echo{" + "parsingSequence=" + this.getParsingSequence() + ", linePattern=" + this.getLinePattern() + '}';
        }

    }
        

    @Override
    public Integer getVerticalParserSequence() {

        return this.getGrammar().getSingleLine().getEcho().getParsingSequence();
    }

    @Override
    public Set<LinePattern> getPatterns() {
       
        return this.getGrammar().getSingleLine().getEcho().getLinePattern();
    } 

      @Override
    public IScript execute() throws Exception {
        String echoOutput;
        if(this.isCandidateForEvaluation()){
            if(this.message.contains("debug")){
                System.out.println("debug reached");
            }
            echoOutput = this.evaluateLocalVariable(this.message).toUI();
        }else{
            echoOutput = this.getMessage();
        }
        HawkOutput hawkoutput = AppContainer.getInstance().getBean(HawkOutput.class);
        hawkoutput.writeEchoOutput(echoOutput);
        hawkoutput.writeEchoOutput("\n");
        return  LocalVarDeclScript.createDummyBooleanScript();
    }

   

    /**
     * This returns <tt>EchoScript</tt> from echo matcher map.
     * @param lineEchoMatcherMap
     * @return
     */
    
    @Override
    public  EchoScript createScript(Map<Integer,String> lineEchoMatcherMap){

        if(lineEchoMatcherMap == null){
            return null;
        }
        EchoScript echoScript = new EchoScript();
        echoScript.setMessage(lineEchoMatcherMap.get(1));
        echoScript.setCandidateForEvaluation(true);
        
        return echoScript;
    }

   
}




