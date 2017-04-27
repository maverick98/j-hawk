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


package org.hawk.lang.util;

import java.util.Map;
import java.util.Set;
import org.commons.io.IOUtil;

import org.hawk.lang.IScript;
import org.hawk.lang.grammar.SingleLineGrammar;
import org.hawk.lang.object.LocalVarDeclScript;
import org.hawk.lang.singleline.SingleLineScript;
import org.hawk.lang.singleline.pattern.LinePattern;
import org.hawk.logger.HawkLogger;
import org.common.di.ScanMe;
/**
 *
 * @version 1.0 8 Apr, 2010
 * @author msahu
 */
@ScanMe(true)
public class ThinkScript extends SingleLineScript{

    private static final HawkLogger logger = HawkLogger.getLogger(ThinkScript.class.getName());
    
    private int time;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public ThinkScript(){
    }

     public static class Think extends SingleLineGrammar {

        @Override
        public String toString() {
            return "Think{" + "parsingSequence=" + this.getParsingSequence() + ", linePattern=" + this.getLinePattern() + '}';
        }

    }
        

    @Override
    public Integer getVerticalParserSequence() {

        return this.getGrammar().getSingleLine().getThink().getParsingSequence();
    }

    @Override
    public Set<LinePattern> getPatterns() {
       
        return this.getGrammar().getSingleLine().getThink().getLinePattern();
    } 
   

    
    @Override
    public  ThinkScript createScript(Map<Integer,String> lineThinkMatcherMap) throws Exception{

        if(lineThinkMatcherMap== null){
            return null;
        }
        ThinkScript thinkScript = new ThinkScript();
        thinkScript.setTime(Integer.parseInt(lineThinkMatcherMap.get(1)));
        return thinkScript;
    }

    @Override
    public IScript execute() throws Exception {

        logger.info("executing thinktime "+this.time);
        IOUtil.sleep(this.time);
        
        return LocalVarDeclScript.createDummyBooleanScript();
    }
    /*
    @Override
    public boolean isVariable() {
        return false;
    }
    */ 
   

}




