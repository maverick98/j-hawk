/*
 * This file is part of j-hawk
 * CopyLeft (C) BigBang<->BigCrunch Manoranjan Sahu, All Rights are left.
 * 
 */

package org.hawk.lang.type;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @version 1.0 20 Jul, 2010
 * @author msahu
 */
public class DoubleDataType extends AbstractDataType{

    private static final Pattern INTEGERPATTERN=Pattern.compile("(\\-?\\d*)\\.?0*");
    private double data;

    public DoubleDataType(double data){
        this.data = data;
    }

    public double getData() {
        return data;
    }

    public void setData(double data) {
        this.data = data;
    }
     @Override
    public String value() {
        return String.valueOf(this.getData());
    }
    public boolean isPositiveInteger(){
        boolean status = false;
        Matcher m = INTEGERPATTERN.matcher(this.data+"");
        String tmp = null;
        if(m.matches()){
            tmp = m.group(1);
            int i = Integer.parseInt(tmp);
            status = i>0;
        }else{
            status = false;
        }
        
        return status;
    }

    private static final Map<Double,String> toStringCache = new HashMap<>();
    @Override
    public String toString(){
        if(toStringCache.containsKey(this.data)){
            
            return toStringCache.get(this.data);
        }
        Matcher m = INTEGERPATTERN.matcher(this.data+"");
        if(m.matches()){
            return m.group(1);
        }
        String result =  this.data+"";
        toStringCache.put(this.data, result);
        return result;
    }
    

    

    @Override
    public IDataType copy() throws Exception {
        IDataType clonedCopy = new DoubleDataType(this.data);
        return clonedCopy;
    }

    
    

}




