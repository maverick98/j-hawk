/*
 * This file is part of j-hawk
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




