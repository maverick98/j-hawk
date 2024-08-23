/*
 * This file is part of j-hawk
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
    
    String toJson();
}
