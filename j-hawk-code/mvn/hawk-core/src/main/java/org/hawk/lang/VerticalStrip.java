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
