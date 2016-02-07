

package org.hawk.module.script;

import org.hawk.exception.HawkException;
import java.util.Map;
import org.hawk.module.script.type.IDataType;
import org.hawk.module.script.type.Variable;

/**
 * The interface for scripts in Hawk.
 * @author msahu
 */
public interface IScript {

    /**
     * This executes the contents of a script.
     * This returns either a boolean or double
     * depending upon how a method is invoked.
     * @return the returned data is being used to test whether
     * a function returns or not. Currently Hawk support two types return
     * Double and Boolean.The implementation should look at the return type
     * and script type to determine what action to be taken.
     * @throws org.hawk.exception.HawkException
     */
    IScript execute() throws HawkException;

    /**
     * This pushes local variables into a stack.
     * This is useful in case of function invocation.
     */
    void pushLocalVars();

    /**
     * This pops the local variable map out  from the stack.
     * @return a map containing local var and its value.
     */
    Map<String,IDataType> popLocalVars();

    /**
     * 
     * @return
     */
    boolean isVariable();

    Variable getVariable();

    void setVariable(Variable value);

    void setVariableValue(Variable value);

    Variable getVariableValue();

    String mangle();

    int length();

    IScript copy();

    int getLineNumber();

    IScript add(IScript otherScript) throws HawkException;

    IScript subtract(IScript otherScript) throws HawkException;

    IScript multiply(IScript otherScript) throws HawkException;


    IScript divide(IScript otherScript) throws HawkException;

    IScript modulus(IScript otherScript) throws HawkException;

    IScript equalTo(IScript otherScript) throws HawkException;



    IScript greaterThan(IScript otherScript) throws HawkException;

    IScript lessThan(IScript otherScript) throws HawkException;

    IScript greaterThanEqualTo(IScript otherScript) throws HawkException;

    IScript lessThanEqualTo(IScript otherScript) throws HawkException;

    IScript and(IScript otherScript) throws HawkException;

    IScript or(IScript otherScript) throws HawkException;

    IScript refer(IScript otherScript) throws HawkException;

    IScript assign(IScript otherScript) throws HawkException;

    IScript arrayBracket(IScript otherScript) throws HawkException;

    String toUI();

    /**
     * This converts the hawk object into java map..
     * @return
     * @throws org.hawk.exception.HawkException
     */
    Map<Object,Object> toJavaMap() throws HawkException;

    /**
     * This converts the hawk object into java object
     * @return
     * @throws org.hawk.exception.HawkException
     */
    Object toJava() throws HawkException;

}
