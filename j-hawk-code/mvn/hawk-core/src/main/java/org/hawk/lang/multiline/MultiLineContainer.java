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

package org.hawk.lang.multiline;

/**
 *
 * @version 1.0 11 Apr, 2010
 * @author msahu
 */
public class MultiLineContainer {

    private int multiLineStart;
    
    private int start;

    private int end;

    public int getMultiLineStart() {
        return multiLineStart;
    }

    public void setMultiLineStart(int multiLineStart) {
        this.multiLineStart = multiLineStart;
    }

    

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

   

    

    
}




