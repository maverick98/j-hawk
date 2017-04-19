/*
 * This file is part of hawkeye
 * CopyLeft (C) 2012-2013 Manoranjan Sahu, All Rights are left.
 *
 * 
 *
 * 1) If you modify a source file, make a comment in it containing your name and the date.
 * 2) If you distribute a modified version, you must do it under the GPL 2.
 * 3) Developers are encouraged but not required to notify the hawkeye maintainers at abeautifulmind98@gmail.com
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
 * @author manoranjan
 */
public class DataTypeFactory {

    /**
     * Factory method to hawk data types...
     *
     * @return
     */
    public static IDataType createDataType(String data) {

        IDataType dataType;

        if (data.equalsIgnoreCase("true") || data.equalsIgnoreCase("false")) {
            dataType = new BooleanDataType(Boolean.parseBoolean(data));
        } else {

            try {
                dataType = new IntDataType(Integer.parseInt(data));
            } catch (NumberFormatException nfe) {
                try {
                    dataType = new DoubleDataType(Double.parseDouble(data));
                } catch (NumberFormatException nfe1) {

                    dataType = new StringDataType(data);
                }
            }
        }

        return dataType;
    }

    /**
     * Give the java object back to jvm....
     *
     * @return
     */
    public static Object createJavaObject(IDataType dataType) {

        Object javaObject = null;

        if (dataType instanceof BooleanDataType) {

            javaObject = Boolean.parseBoolean(dataType.toString());

        } else if (dataType instanceof IntDataType) {

            javaObject = Integer.parseInt(dataType.toString());
        } else if (dataType instanceof DoubleDataType) {

            javaObject = Double.parseDouble(dataType.toString());
        } else if (dataType instanceof StringDataType) {

            javaObject = dataType.toString();
        }
        return javaObject;
    }
}
