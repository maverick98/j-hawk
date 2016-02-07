
package org.hawk.module.script.type;

import org.hawk.exception.HawkException;

/**
 *
 * @author msahu
 */
public interface IDataType {

    IDataType add(IDataType dataType) throws HawkException;
    DoubleDataType subtract(IDataType dataType) throws HawkException;
    DoubleDataType multiply(IDataType dataType) throws HawkException;
    DoubleDataType divide(IDataType dataType) throws HawkException;
    DoubleDataType modulus(IDataType dataType) throws HawkException;
    BooleanDataType equalTo(IDataType dataType) throws HawkException;

    BooleanDataType greaterThan(IDataType dataType) throws HawkException;
    BooleanDataType lessThan(IDataType dataType) throws HawkException;
    BooleanDataType greaterThanEqualTo(IDataType dataType) throws HawkException;
    BooleanDataType lessThanEqualTo(IDataType dataType) throws HawkException;
    BooleanDataType and(IDataType dataType) throws HawkException;
    BooleanDataType or(IDataType dataType) throws HawkException;
    IDataType copy() throws HawkException;
}
