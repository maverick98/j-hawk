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

}
