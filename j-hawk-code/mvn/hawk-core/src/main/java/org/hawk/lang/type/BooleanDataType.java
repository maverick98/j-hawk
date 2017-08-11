/*
 * This file is part of j-hawk
 * CopyLeft (C) BigBang<->BigCrunch Manoranjan Sahu, All Rights are left.
 * 
 */
package org.hawk.lang.type;

/**
 *
 * @version 1.0 20 Jul, 2010
 * @author msahu
 */
public class BooleanDataType extends AbstractDataType {

    private boolean data;

    public BooleanDataType(boolean data) {
        this.data = data;
    }

    public boolean isData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return (this.data ? 1 : 0) + "";
    }

    @Override
    public String value() {
        return String.valueOf(this.isData());
    }

    @Override
    public IDataType copy() throws Exception {
        IDataType clonedCopy = new BooleanDataType(this.data);
        return clonedCopy;
    }
    
     @Override
    public BooleanDataType and(IDataType dataType) throws Exception {
        if(dataType == null ){
            throw new Exception("invalid datatype");
        }
       
        BooleanDataType thatData =  (BooleanDataType) dataType;
        BooleanDataType result = new BooleanDataType(this.isData() && thatData.isData());
        return result;
    }

    @Override
    public BooleanDataType or(IDataType dataType) throws Exception {
        if(dataType == null ){
            throw new Exception("invalid datatype");
        }
       
        BooleanDataType thatData =  (BooleanDataType) dataType;
        BooleanDataType result = new BooleanDataType(this.isData() || thatData.isData());
        return result;
    }
    @Override
    public String toJson(){
        
        return this.value();
    }
    
}
