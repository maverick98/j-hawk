/*
 * This file is part of j-hawk
 * CopyLeft (C) 2012-2013 Manoranjan Sahu, All Rights are left.
 * 
 */

package org.hawk.lang;

import java.util.Map;


/**
 * The interface for scripts in Hawk.
 * @author msahu
 */
public interface IScript {

    /**
     * This executes the contents of a script.
     * This returns either a boolean or double
     * depending upon how a method is invoked.
     * @return the returned data is being used to test whether
     * a function returns or not. Currently Hawk support two types return
     * Double and Boolean.The implementation should look at the return type
     * and script type to determine what action to be taken.
     * @throws java.lang.Exception
     */
    IScript execute() throws Exception;

     /**
     * This converts the hawk object into java map..
     * @return
     * @throws java.lang.Exception
     */
    Map<Object,Object> toJavaMap() throws Exception;
    
    
    String toJson();

   
    IScript copy();
    
    int getLineNumber();

    
   
    
    /**
     * 
     * @return 
     */
    Integer getVerticalParserSequence();
    
    
    
    
}
