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

   
    IScript copy();
    
    int getLineNumber();

    
   
    
    /**
     * 
     * @return 
     */
    Integer getVerticalParserSequence();
    
    
    
    
}
