/*
 * This file is part of j-hawk
 * Copyright (C) 2012-2013 Manoranjan Sahu, All Rights are left.
 *
 * 
 *
 * 1) If you modify a source file, make a comment in it containing your name and the date.
 * 2) If you distribute a modified version, you must do it under the GPL 2.
 * 3) Developers are encouraged but not required to notify the j-hawk maintainers at abeautifulmind98@gmail.com
 * when they make a useful addition. It would be nice if significant contributions could be merged into the main distribution.
 *
 * A full copy of the license can be found in gpl.txt or online at
 * http://www.gnu.org/licenses/gpl.txt
 *
 * END_HEADER
 */



package org.commons.ds.stack;

import java.util.ArrayList;
import java.util.List;

/**
 * Not to be confused with java's stack...
 * Their's is a implemenation with fundamental mistake.
 * @version 1.0 11 Apr, 2010
 * @author msahu
 */
public class Stack<T> {

    /**
     * Internal data are storred in an arrayList
     */
    private final List<T> data = new ArrayList<T>();

    /**
     * Current size of stack.
     */
    private int size = 0;
    /**
     * This pushes object to the stack.
     * @param obj
     * @return returns <tt>true</tt> on successful addition to the stack.
     */
    public boolean push(T obj){
        if(obj == null){
            return false;
        }
        boolean status;
        status = data.add(obj);
        size++;
        return status;
    }

    /**
     * This pops out the top object present in the stack.
     * @param <T>
     * @return returns the top object present in the stack.
     */
    public  <T> T pop(){

        T topObj = null;
        if(size > 0){
            topObj = (T) data.get(size-1);
            data.remove(topObj);
            size--;
        }
        return topObj;
    }

    /**
     * This returns the top object present in the stack.
     *
     * @param <T>
     * @return returns the top object present in the stack.
     */
    public  <T> T top(){




        T topObj = null;
        if(size>0){
            topObj = (T) data.get(size-1);
        }
        return topObj;
    }

    /**
     * Returns the stack dump 
     *
     * @return returns the stack dump.
     */
    public  String dump(){

        if(this.isEmpty()){
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for(int i=this.size-1;i>=0;i--){
            sb.append(this.data.get(i).toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * This deontes whether a stack is empty or not.
     * @return <tt>true</tt> if the stack is empty otherwise <tt>false</tt>
     */
    public boolean isEmpty(){
        return this.size == 0;
    }

    @Override
    public String toString(){
        return this.isEmpty()?"":data.toString();
    }
}




