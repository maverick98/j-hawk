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
 * @author msahu
 */
public interface IDataType {
    
    String value();

    IDataType add(IDataType dataType) throws Exception;
    
    IDataType subtract(IDataType dataType) throws Exception;
    
    IDataType multiply(IDataType dataType) throws Exception;
    
    DoubleDataType divide(IDataType dataType) throws Exception;
    
    DoubleDataType modulus(IDataType dataType) throws Exception;
    
    BooleanDataType equalTo(IDataType dataType) throws Exception;

    BooleanDataType greaterThan(IDataType dataType) throws Exception;
    
    BooleanDataType lessThan(IDataType dataType) throws Exception;
    
    BooleanDataType greaterThanEqualTo(IDataType dataType) throws Exception;
    
    BooleanDataType lessThanEqualTo(IDataType dataType) throws Exception;
    
    BooleanDataType and(IDataType dataType) throws Exception;
    
    BooleanDataType or(IDataType dataType) throws Exception;
    
    IDataType copy() throws Exception;
}
