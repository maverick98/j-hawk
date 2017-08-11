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

package org.hawk.lang.type;


import org.hawk.util.HawkUtil;

/**
 *
 * @version 1.0 20 Jul, 2010
 * @author msahu
 */
public class StringDataType extends AbstractDataType{

    private String data;
    
    public StringDataType(String data){
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
     @Override
    public String value() {
        return String.valueOf(this.getData());
    }
    @Override
    public String toString(){
        return HawkUtil.convertHawkStringToJavaString(this.data);
    }

    

    
    @Override
    public IDataType copy() throws Exception {
        IDataType clonedCopy = new StringDataType(this.data);
        return clonedCopy;
    }

   
    @Override
    public String toJson(){
        
        StringBuilder json = new StringBuilder();
        json.append("\"");
        json.append(this.toString());
        json.append("\"");
        return json.toString();
    }
    

}




