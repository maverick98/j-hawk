/*
 * @(#)AbstractDataType.java        1.0 16 Jan, 2011
 *
 * Copyright (c) 2008 XProtean, Inc.
 * 461 S. Milpitas Blvd, Milpitas, CA, 95035, U.S.A.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * XProtean, Inc. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with XProtean Inc.
 *
 * msahu  1.0  16 Jan, 2011 Initial code
 */


package org.hawk.module.script.type;

import org.hawk.exception.HawkException;

/**
 *
 * @version 1.0 16 Jan, 2011
 * @author msahu
 */
public abstract class AbstractDataType implements IDataType{

    public IDataType add(IDataType dataType) throws HawkException {
        if(dataType == null ){
            throw new HawkException("invalid datatype");
        }
        double thisData = 0;
        double thatData = 0;
        try{
            thisData = Double.parseDouble(this.toString());
            thatData = Double.parseDouble(dataType.toString());
        }catch(NumberFormatException nfe){
            StringBuilder sb = new StringBuilder();
            sb.append(this.toString());
            sb.append(dataType.toString());
            return new StringDataType(sb.toString());

        }
        double sum = thisData+thatData;
        DoubleDataType result = new DoubleDataType(sum);
        result.setData(sum);
        return result;
    }

    public DoubleDataType subtract(IDataType dataType) throws HawkException {
        if(dataType == null ){
            throw new HawkException("invalid datatype");
        }
        double thisData = Double.parseDouble(this.toString());
        double thatData = Double.parseDouble(dataType.toString());
        double diff = thisData-thatData;
        DoubleDataType result = new DoubleDataType(diff);
        result.setData(diff);
        return result;
    }

    public DoubleDataType multiply(IDataType dataType) throws HawkException {
        if(dataType == null ){
            throw new HawkException("invalid datatype");
        }
        double thisData = Double.parseDouble(this.toString());
        double thatData = Double.parseDouble(dataType.toString());
        double mult = thisData*thatData;
        DoubleDataType result = new DoubleDataType(mult);
        result.setData(mult);
        return result;
    }

    public DoubleDataType divide(IDataType dataType) throws HawkException {
        if(dataType == null ){
            throw new HawkException("invalid datatype");
        }
        double thisData = Double.parseDouble(this.toString());
        double thatData = Double.parseDouble(dataType.toString());
        double div = thisData/thatData;
        DoubleDataType result = new DoubleDataType(div);
        result.setData(div);
        return result;
    }

    public DoubleDataType modulus(IDataType dataType) throws HawkException {
        if(dataType == null ){
            throw new HawkException("invalid datatype");
        }
        double thisData = Double.parseDouble(this.toString());
        double thatData = Double.parseDouble(dataType.toString());
        double modulus = thisData%thatData;
        DoubleDataType result = new DoubleDataType(modulus);
        result.setData(modulus);
        return result;
    }

    public BooleanDataType equalTo(IDataType dataType) throws HawkException {
        if(dataType == null ){
            throw new HawkException("invalid datatype");
        }
         
        double thisData = 0;
        double thatData = 0;
        boolean result = false;
        try{
            thisData = Double.parseDouble(this.toString());
            thatData = Double.parseDouble(dataType.toString());
        }catch(NumberFormatException nfe){
            result =  this.toString().equals(dataType.toString());
            return new BooleanDataType(result);
        }
        
        result = (thisData == thatData);
        
        return new BooleanDataType(result);
    }

    public BooleanDataType greaterThan(IDataType dataType) throws HawkException {
         if(dataType == null ){
            throw new HawkException("invalid datatype");
        }

        double thisData = 0;
        double thatData = 0;
        boolean result = false;
        try{
            thisData = Double.parseDouble(this.toString());
            thatData = Double.parseDouble(dataType.toString());
        }catch(NumberFormatException nfe){
            int rtn =  this.toString().compareTo(dataType.toString());
            return new BooleanDataType(rtn >0);
        }

        result = (thisData > thatData);

        return new BooleanDataType(result);
    }

    public BooleanDataType lessThan(IDataType dataType) throws HawkException {
         if(dataType == null ){
            throw new HawkException("invalid datatype");
        }

        double thisData = 0;
        double thatData = 0;
        boolean result = false;
        try{
            thisData = Double.parseDouble(this.toString());
            thatData = Double.parseDouble(dataType.toString());
        }catch(NumberFormatException nfe){
            int rtn =  this.toString().compareTo(dataType.toString());
            return new BooleanDataType(rtn < 0);
        }

        result = (thisData < thatData);

        return new BooleanDataType(result);
    }

    public BooleanDataType greaterThanEqualTo(IDataType dataType) throws HawkException {
        if(dataType == null ){
            throw new HawkException("invalid datatype");
        }

        double thisData = 0;
        double thatData = 0;
        boolean result = false;
        try{
            thisData = Double.parseDouble(this.toString());
            thatData = Double.parseDouble(dataType.toString());
        }catch(NumberFormatException nfe){
            int rtn =  this.toString().compareTo(dataType.toString());
            return new BooleanDataType(rtn >= 0);
        }

        result = (thisData >= thatData);

        return new BooleanDataType(result);
    }

    public BooleanDataType lessThanEqualTo(IDataType dataType) throws HawkException {
        if(dataType == null ){
            throw new HawkException("invalid datatype");
        }

        double thisData = 0;
        double thatData = 0;
        boolean result = false;
        try{
            thisData = Double.parseDouble(this.toString());
            thatData = Double.parseDouble(dataType.toString());
        }catch(NumberFormatException nfe){
            int rtn =  this.toString().compareTo(dataType.toString());
            return new BooleanDataType(rtn <= 0);
        }

        result = (thisData <= thatData);

        return new BooleanDataType(result);
    }

    public BooleanDataType and(IDataType dataType) throws HawkException {
        if(dataType == null ){
            throw new HawkException("invalid datatype");
        }
        BooleanDataType thisData = new BooleanDataType( Double.parseDouble(this.toString())!=0);
        BooleanDataType thatData = new BooleanDataType( Double.parseDouble(dataType.toString())!=0);

        return thisData.and(thatData);
    }

    public BooleanDataType or(IDataType dataType) throws HawkException {
       if(dataType == null ){
            throw new HawkException("invalid datatype");
        }
        BooleanDataType thisData = new BooleanDataType( Double.parseDouble(this.toString())!=0);
        BooleanDataType thatData = new BooleanDataType( Double.parseDouble(dataType.toString())!=0);

        return thisData.or(thatData);
    }

    public abstract IDataType copy() throws HawkException;
}