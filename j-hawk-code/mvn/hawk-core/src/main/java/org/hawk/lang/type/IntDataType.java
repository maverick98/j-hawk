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

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.common.di.AppContainer;
import org.commons.ds.operator.OperatorEnum;


/**
 *
 * @version 1.0 20 Jul, 2010
 * @author msahu
 */
public class IntDataType extends AbstractDataType {

    private static final Map<Integer, String> toStringCache = new HashMap<>();
    private static final Pattern INTEGERPATTERN = Pattern.compile("(\\-?\\d*)");
    private int data;

    public IntDataType(int data) {
        this.data = data;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    @Override
    public String value() {
        return String.valueOf(this.getData());
    }

    
    public boolean isPositiveInteger() {
        boolean status = false;
        Matcher m = INTEGERPATTERN.matcher(this.data + "");
        String tmp = null;
        if (m.matches()) {
            tmp = m.group(1);
            int i = Integer.parseInt(tmp);
            status = i >= 0;
        } else {
            status = false;
        }

        return status;
    }

    @Override
    public String toString() {
        if (toStringCache.containsKey(this.data)) {

            return toStringCache.get(this.data);
        }
        Matcher m = INTEGERPATTERN.matcher(this.data + "");
        if (m.matches()) {
            return m.group(1);
        }
        String result = this.data + "";
        toStringCache.put(this.data, result);
        return result;
    }

    @Override
    public IDataType copy() throws Exception {
        IDataType clonedCopy = new IntDataType(this.data);
        return clonedCopy;
    }

    @Override
    public IDataType add(IDataType dataType) throws Exception {
        if (dataType == null) {
            throw new Exception("invalid datatype");
        }
        if (!(dataType instanceof IntDataType)) {
            return super.add(dataType);
        }
        int thisData = 0;
        int thatData = 0;
        try {
            thisData = Integer.parseInt(this.toString());
            thatData = Integer.parseInt(dataType.toString());
        } catch (NumberFormatException nfe) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.toString());
            sb.append(dataType.toString());
            return new StringDataType(sb.toString());

        }
        IDataType rtn = null;
        IntegerOverflow integerOverflow = AppContainer.getInstance().getBean( IntegerOverflow.class);
        if (integerOverflow.detectOverflow(thisData, thatData, OperatorEnum.MINUS)) {

            double a = thisData;
            double b = thatData;
            double c = a + b;
            DoubleDataType result = new DoubleDataType(c);
            result.setData(c);
            rtn = result;
        } else {
            int sum = thisData + thatData;
            IntDataType result = new IntDataType(sum);
            result.setData(sum);
            rtn = result;
        }
        return rtn;
    }

    @Override
    public IDataType subtract(IDataType dataType) throws Exception {
        if (dataType == null) {
            throw new Exception("invalid datatype");
        }
        if (!(dataType instanceof IntDataType)) {
            return super.subtract(dataType);
        }
        IDataType rtn = null;
        int thisData = Integer.parseInt(this.toString());
        int thatData = Integer.parseInt(dataType.toString());

        IntegerOverflow integerOverflow = AppContainer.getInstance().getBean(IntegerOverflow.class);
        if (integerOverflow.detectOverflow(thisData, thatData, OperatorEnum.MINUS)) {

            double a = thisData;
            double b = thatData;
            double c = a - b;
            DoubleDataType result = new DoubleDataType(c);
            result.setData(c);
            rtn = result;
        } else {
            int diff = thisData - thatData;
            IntDataType result = new IntDataType(diff);
            result.setData(diff);
            rtn = result;
        }
        return rtn;
    }

    @Override
    public IDataType multiply(IDataType dataType) throws Exception {
        if (dataType == null) {
            throw new Exception("invalid datatype");
        }
        if (!(dataType instanceof IntDataType)) {
            return super.multiply(dataType);
        }
        IDataType rtn = null;
        int thisData = Integer.parseInt(this.toString());
        int thatData = Integer.parseInt(dataType.toString());

        IntegerOverflow integerOverflow = AppContainer.getInstance().getBean( IntegerOverflow.class);
        if (integerOverflow.detectOverflow(thisData, thatData, OperatorEnum.MULTIPLY)) {
           
            double a = thisData;
            double b = thatData;
            double c = a * b;
            DoubleDataType result = new DoubleDataType(c);
            result.setData(c);
            rtn = result;
        } else {
            int mult = thisData * thatData;
            IntDataType result = new IntDataType(mult);
            result.setData(mult);
            rtn = result;
        }
        return rtn;
    }
}
