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

package org.hawk.lang.console;


import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import org.common.di.AppContainer;

import org.hawk.lang.IScript;
import static org.hawk.lang.constant.HawkLanguageKeyWord.echon;
import org.hawk.lang.grammar.SingleLineGrammar;
import org.hawk.lang.object.LocalVarDeclScript;
import org.hawk.lang.singleline.pattern.LinePattern;
import org.hawk.logger.HawkLogger;
import org.hawk.output.HawkOutput;
import org.common.di.ScanMe;
import org.hawk.lang.object.IObjectScript;
/**
 * <p>This echoes message in the console 
 * e.g.<br>
 *
 *
 * for(var i = 1 ; i <= 100 ; i = i +1)<br>
 * {<br>
 *          if ( i == 50 )<br>
 *              {<br>
 *                  <h3>echon "Stop here"<br></h3>
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
public class EchoNScript extends EchoScript{

    private static final HawkLogger logger = HawkLogger.getLogger(EchoNScript.class.getName());

    /**
     * Default CTOR
     */
    public EchoNScript(){
        
    }

     public static class EchoN extends SingleLineGrammar {

        @Override
        public String toString() {
            return "EchoN{" + "parsingSequence=" + this.getParsingSequence() + ", linePattern=" + this.getLinePattern() + '}';
        }

    }
        

    @Override
    public Integer getVerticalParserSequence() {

        return this.getGrammar().getSingleLine().getEchoN().getParsingSequence();
    }

    @Override
    public Set<LinePattern> getPatterns() {
       
        return this.getGrammar().getSingleLine().getEchoN().getLinePattern();
    } 

    /*
    @Override
    public  Set<LinePattern> getPatterns() {
        Set<LinePattern> patterns = new TreeSet<LinePattern>();
        LinePattern linePattern = new LinePattern();
        linePattern.setSequence(1);
        linePattern.setPattern(ECHON_PATTERN);
        patterns.add(linePattern);
        return patterns;
    }
    */
    @Override
    public IScript execute() throws Exception {
        String echoOutput="";
        if(this.isCandidateForEvaluation()){
            IObjectScript result = this.evaluateLocalVariable(this.getMessage());
            if(result != null){
                echoOutput = result.toUI();
            }
        }else{
            echoOutput = this.getMessage();
        }
        HawkOutput hawkoutput = AppContainer.getInstance().getBean( HawkOutput.class);
        hawkoutput.writeEchoOutput(echoOutput);
        return  LocalVarDeclScript.createDummyBooleanScript();
    }

   

    /**
     * This returns <tt>EchoNScript</tt> from echo matcher map.
     * @param lineEchoNMatcherMap
     * @return
     */
    
    @Override
    public  EchoNScript createScript(Map<Integer,String> lineEchoNMatcherMap){

        if(lineEchoNMatcherMap == null){
            return null;
        }
        EchoNScript echoNScript = new EchoNScript();
        echoNScript.setMessage(lineEchoNMatcherMap.get(1));
        echoNScript.setCandidateForEvaluation(true);
        
        return echoNScript;
    }

    /**
     * The primary echo RegEx pattern to parse echo script
     */
    private  static final Pattern ECHON_PATTERN=Pattern.compile("\\s*"+echon+"\\s*(.*)\\s*");

}




