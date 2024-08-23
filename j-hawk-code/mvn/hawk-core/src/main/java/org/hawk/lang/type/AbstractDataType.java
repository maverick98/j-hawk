/*
 * This file is part of j-hawk
 *  
 * 
 */

package org.hawk.lang.type;



/**
 *
 * @version 1.0 16 Jan, 2011
 * @author msahu
 */
public abstract class AbstractDataType implements IDataType{

    @Override
    public abstract String value();

    
    @Override
    public IDataType add(IDataType dataType) throws Exception {
        if(dataType == null ){
            throw new Exception("invalid datatype");
        }
        double thisData;
        double thatData;
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

    @Override
    public IDataType subtract(IDataType dataType) throws Exception {
        if(dataType == null ){
            throw new Exception("invalid datatype");
        }
        double thisData = Double.parseDouble(this.toString());
        double thatData = Double.parseDouble(dataType.toString());
        double diff = thisData-thatData;
        DoubleDataType result = new DoubleDataType(diff);
        result.setData(diff);
        return result;
    }

    @Override
    public IDataType multiply(IDataType dataType) throws Exception {
        if(dataType == null ){
            throw new Exception("invalid datatype");
        }
        double thisData = Double.parseDouble(this.toString());
        double thatData = Double.parseDouble(dataType.toString());
        double mult = thisData*thatData;
        DoubleDataType result = new DoubleDataType(mult);
        result.setData(mult);
        return result;
    }

    @Override
    public DoubleDataType divide(IDataType dataType) throws Exception {
        if(dataType == null ){
            throw new Exception("invalid datatype");
        }
        double thisData = Double.parseDouble(this.toString());
        double thatData = Double.parseDouble(dataType.toString());
        double div = thisData/thatData;
        DoubleDataType result = new DoubleDataType(div);
        result.setData(div);
        return result;
    }

    @Override
    public DoubleDataType modulus(IDataType dataType) throws Exception {
        if(dataType == null ){
            throw new Exception("invalid datatype");
        }
        double thisData = Double.parseDouble(this.toString());
        double thatData = Double.parseDouble(dataType.toString());
        double modulus = thisData%thatData;
        DoubleDataType result = new DoubleDataType(modulus);
        result.setData(modulus);
        return result;
    }

    @Override
    public BooleanDataType equalTo(IDataType dataType) throws Exception {
        if(dataType == null ){
            throw new Exception("invalid datatype");
        }
         
        double thisData;
        double thatData;
        boolean result;
        try{
            thisData = Double.parseDouble(this.toString());
            thatData = Double.parseDouble(dataType.toString());
        }catch(NumberFormatException nfe){
            result =  this.toString().equals(dataType.toString());
            return new BooleanDataType(result);
        }
        result =  ( Math.abs(thisData - thatData) < .0000001 );
        
        
        return new BooleanDataType(result);
    }

    @Override
    public BooleanDataType greaterThan(IDataType dataType) throws Exception {
         if(dataType == null ){
            throw new Exception("invalid datatype");
        }

        double thisData ;
        double thatData ;
        boolean result ;
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

    @Override
    public BooleanDataType lessThan(IDataType dataType) throws Exception {
         if(dataType == null ){
            throw new Exception("invalid datatype");
        }

        double thisData;
        double thatData;
        boolean result;
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

    @Override
    public BooleanDataType greaterThanEqualTo(IDataType dataType) throws Exception {
        if(dataType == null ){
            throw new Exception("invalid datatype");
        }

        double thisData;
        double thatData;
        boolean result;
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

    @Override
    public BooleanDataType lessThanEqualTo(IDataType dataType) throws Exception {
        if(dataType == null ){
            throw new Exception("invalid datatype");
        }

        double thisData;
        double thatData;
        boolean result ;
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

    @Override
    public BooleanDataType and(IDataType dataType) throws Exception {
        if(dataType == null ){
            throw new Exception("invalid datatype");
        }
        BooleanDataType thisData = new BooleanDataType( Double.parseDouble(this.toString())!=0);
        BooleanDataType thatData = new BooleanDataType( Double.parseDouble(dataType.toString())!=0);

        return thisData.and(thatData);
    }

    @Override
    public BooleanDataType or(IDataType dataType) throws Exception {
       if(dataType == null ){
            throw new Exception("invalid datatype");
        }
        BooleanDataType thisData = new BooleanDataType( Double.parseDouble(this.toString())!=0);
        BooleanDataType thatData = new BooleanDataType( Double.parseDouble(dataType.toString())!=0);

        return thisData.or(thatData);
    }

    @Override
    public abstract IDataType copy() throws Exception;
    
    @Override
    public String toJson(){
        
        return this.toString();
    }
}