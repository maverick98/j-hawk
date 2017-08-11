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

package org.hawk.lang.loop;


import java.util.Map;
import java.util.Set;

import org.hawk.lang.IScript;
import org.hawk.lang.condition.IfScript;
import org.hawk.lang.grammar.SingleLineGrammar;
import org.hawk.lang.object.LocalVarDeclScript;
import org.hawk.lang.singleline.SingleLineScript;
import org.hawk.lang.singleline.pattern.LinePattern;
import org.common.di.ScanMe;
import org.hawk.lang.multiline.MultiLineScript;
/**
 * <p>This breaks out a for loop in hawk script.
 * e.g.<br>
 * 
 * 
 * for(var i = 1 ; i <= 100 ; i = i +1)<br>
 * {<br>
 *          if ( i == 50 )<br>
 *              {<br>
 *                  echo "Stop here"<br>
 *                  break<br>
 *              }<br>
 * 
 * }<br>
 * 
 * @version 1.0 1 May, 2010
 * @author msahu
 * @see IfScript
 */
@ScanMe(true)
public class BreakScript extends SingleLineScript{

   

    /**
     * Default CTOR
     */
    public BreakScript(){

    }
    public static class Break extends SingleLineGrammar {

        @Override
        public String toString() {
            return "Break" + "parsingSequence=" + this.getParsingSequence() + ", linePattern=" + this.getLinePattern() + '}';
        }

    }
        

    @Override
    public Integer getVerticalParserSequence() {

        return this.getGrammar().getSingleLine().getBrek().getParsingSequence();
    }

    @Override
    public Set<LinePattern> getPatterns() {
       
        return this.getGrammar().getSingleLine().getBrek().getLinePattern();
    } 

   
    /**
     * There is nothing to be done except
     * returning <tt>true</tt> as <tt>break</tt> does not have anything to execute.
     * @return
     * @throws org.hawk.exception.Exception
     */
    @Override
    public IScript execute() throws Exception {
        boolean loopNotFound = true;
        MultiLineScript outerMLS = this.getOuterMultiLineScript();
        do{
             
            if(outerMLS!= null){
                
                outerMLS.applyBreak();
                if(outerMLS.isLoop()){
                    loopNotFound = false;
                }
                outerMLS= outerMLS.getOuterMultiLineScript();
            }
        }while(loopNotFound);
        return LocalVarDeclScript.createDummyBooleanScript();
    }

    /**
     * This creates the BreakScript from the break matcher map.
     * @param lineBreakMatcherMap
     * @return
     * @see CreateScript
     */
    
    public  BreakScript createScript(Map<Integer,String> lineBreakMatcherMap){

        if(lineBreakMatcherMap == null){
            return null;
        }
        BreakScript breakScript = new BreakScript();
        return breakScript;
    }
    /*
     @Override
    public boolean isVariable() {
        return false;
    }
    */ 
    
}




