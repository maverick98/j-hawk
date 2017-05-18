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


package org.hawk.lang.singleline;


import java.util.Map;
import java.util.Set;

import org.hawk.lang.AbstractScript;
import org.hawk.lang.IScript;
import org.hawk.lang.multiline.MultiLineScript;
import org.hawk.lang.singleline.pattern.LinePattern;
import org.hawk.logger.HawkLogger;

/**
 *
 * @version 1.0 10 Apr, 2010
 * @author msahu
 */
public  class SingleLineScript extends AbstractScript implements ISingleLineScript{

    private static final HawkLogger logger = HawkLogger.getLogger(SingleLineScript.class.getName());
    
   
    
    @Override
    public IScript execute() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public SingleLineScript(){
        this.outerMultiLineScript = new MultiLineScript();
    }

     

    @Override
    public IScript copy() {
        System.out.println("The class {"+this.getClass()+"} has not implemented public IScript copy() yet !!!");
        throw new UnsupportedOperationException("Not supported yet.");
    }
   
    @Override
    protected MultiLineScript findNearestOuterMLScript() {
        return this.getOuterMultiLineScript();
                
    }
    
  
    @Override
    public Integer getVerticalParserSequence() {

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IScript createScript(Map<Integer, String> lineMatcherMap) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   

    @Override
    public Set<LinePattern> getPatterns() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}




