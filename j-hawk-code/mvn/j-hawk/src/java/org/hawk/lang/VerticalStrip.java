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

import org.commons.exception.CommonUtil;
import org.hawk.util.HawkUtil;

/**
 *
 * @author manosahu
 */
public class VerticalStrip implements Comparable<VerticalStrip>{

    private Integer start;
    
    private Integer end;

    public VerticalStrip(Integer start, Integer end) {
        this.start = start;
        this.end = end;
    }
    
    

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    @Override
    public int compareTo(VerticalStrip other) {
        
        return this.getStart().compareTo(other.getStart());
        
    }
    
    public boolean overlapping(VerticalStrip other){
        
        return CommonUtil.greaterThan(this.getEnd(), other.getStart()) 
                || 
               CommonUtil.greaterThan(other.getEnd(), this.getStart());
    }
   
    
    
}
