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

package org.hawk.html.java;

import java.lang.reflect.Method;

/**
 *
 * @author Manoranjan Sahu
 */
public class HTMLTableColumnContainer implements Comparable<HTMLTableColumnContainer>{

    
    
    private Method method;
    
    private Integer sequence;
    
    private String header;
    
    private String setter;
    
    private Method preData;
    
    private Method postData;
    
    private Method preDataMethod;
    
    private Method postDataMethod;

    public String getSetter() {
        return setter;
    }

    public void setSetter(String setter) {
        this.setter = setter;
    }
    
    
    

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    
    public Method getPreData() {
        return preData;
    }

    public void setPreData(Method preData) {
        this.preData = preData;
    }

    public Method getPostData() {
        return postData;
    }

    public void setPostData(Method postData) {
        this.postData = postData;
    }

    

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Method getPreDataMethod() {
        return preDataMethod;
    }

    public void setPreDataMethod(Method preDataMethod) {
        this.preDataMethod = preDataMethod;
    }

    public Method getPostDataMethod() {
        return postDataMethod;
    }

    public void setPostDataMethod(Method postDataMethod) {
        this.postDataMethod = postDataMethod;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.sequence != null ? this.sequence.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final HTMLTableColumnContainer other = (HTMLTableColumnContainer) obj;
        if (this.sequence != other.sequence && (this.sequence == null || !this.sequence.equals(other.sequence))) {
            return false;
        }
        return true;
    }

   

    @Override
    public int compareTo(HTMLTableColumnContainer thatObject) {
        
        return this.getSequence().compareTo(thatObject.getSequence());
    }

    @Override
    public String toString() {
        return "header= {" + header + '}';
    }
    
    
    
    
}
