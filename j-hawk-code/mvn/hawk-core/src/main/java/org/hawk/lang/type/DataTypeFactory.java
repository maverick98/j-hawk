/*
 * This file is part of j-hawk
 * CopyLeft (C) BigBang<->BigCrunch Manoranjan Sahu, All Rights are left.
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
