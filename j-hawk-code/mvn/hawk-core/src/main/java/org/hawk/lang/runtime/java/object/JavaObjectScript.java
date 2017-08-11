/*
 * This file is part of j-hawk
 * CopyLeft (C) BigBang<->BigCrunch Manoranjan Sahu, All Rights are left.
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
package org.hawk.lang.runtime.java.object;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.commons.ds.exp.IObject;
import org.hawk.ds.exp.IHawkObject;

import org.hawk.lang.IScript;
import org.hawk.lang.object.IObjectScript;
import org.hawk.lang.object.LocalVarDeclScript;
import org.hawk.lang.singleline.SingleLineScript;
import org.hawk.lang.type.Variable;

/**
 *
 * @author manoranjan
 */
public class JavaObjectScript extends SingleLineScript implements IObjectScript{


    private Object javaObject ;

    public Object getJavaObject() {
        return javaObject;
    }

    public void setJavaObject(Object javaObject) {
        this.javaObject = javaObject;
    }

    
    private Map<String, IObjectScript> members = new HashMap<>();

    private static IObjectScript createScript(Field field, Object inputJavaObject) throws Exception {
        IObjectScript resultScript = null;
        Object obj = null;

        try {
            Method method = inputJavaObject.getClass().getMethod(getterMethod(field.getName()), null);
            
            obj = method.invoke(inputJavaObject, null);

            
            
        } catch (Throwable ex) {
            return null;
            //throw new Exception("error while createing local script", ex);
        }
        
            
            if(field.getType().equals(String.class)){
                resultScript = LocalVarDeclScript.createDummyStringScript(obj.toString());
            }else if(field.getType().equals(int.class) || field.getType().equals(Integer.class)){
                    resultScript = LocalVarDeclScript.createDummyIntScript(Integer.parseInt(obj.toString()));
            }else if(field.getType().equals(double.class) || field.getType().equals(Double.class)){
                     System.out.println("got data " + obj);
                     resultScript = LocalVarDeclScript.createDummyDoubleScript(Double.parseDouble(obj.toString()));
            }else if(field.getType().equals(float.class) || field.getType().equals(Float.class)){
                     resultScript = LocalVarDeclScript.createDummyDoubleScript(Double.parseDouble(obj.toString()));
            }else if(field.getType().equals(boolean.class) || field.getType().equals(Boolean.class)){
                     resultScript = LocalVarDeclScript.createDummyBooleanScript(Boolean.parseBoolean(obj.toString()));
            }else if(field.getType().equals(List.class) || field.getType().equals(Set.class) || (obj != null && obj.getClass().isArray())){
            resultScript = JavaArrayScript.createScript(obj);

        }
        return resultScript;
    }

    private static String getterMethod(String methodStr) {

        StringBuilder sb = new StringBuilder("get");
        sb.append(methodStr.substring(0, 1).toUpperCase(Locale.ENGLISH));
        sb.append(methodStr.substring(1));
        return sb.toString();
    }

    private static String setterMethod(String methodStr) {

        StringBuilder sb = new StringBuilder("set");
        sb.append(methodStr.substring(0, 1).toUpperCase(Locale.ENGLISH));
        sb.append(methodStr.substring(1));
        return sb.toString();
    }

    public static JavaObjectScript createScript(Object javaObject) throws Exception {
        if(javaObject == null){
            return null;
        }
        
        JavaObjectScript javaObjectScript = new JavaObjectScript();
        javaObjectScript.setJavaObject(javaObject);
           

        setMember(javaObject, javaObjectScript);
       
        return javaObjectScript;
    }

    private static void setMember(Object javaObject ,JavaObjectScript javaObjectScript ) throws Exception{


        for (Field field : javaObject.getClass().getFields()) {

                IObjectScript tmp = createScript(field, javaObject);
                if(tmp != null){
                    javaObjectScript.setMember(field.getName(), tmp);
                }
            }
    }

    public boolean setMember(String name, IObjectScript script) {

        this.getMembers().put(name, script);

        try {

            Class paramClazz =  this.getJavaObject()
                                                .getClass()
                                                .getField(name)
                                                .getType();
            Method method = this.getJavaObject()
                                .getClass()
                                .getDeclaredMethod
                                    (
                                        setterMethod(name),
                                        new Class[]{
                                            paramClazz
                                        }
                                    );
            
            Object input = script.toJava();

            method.invoke(this.getJavaObject(), input);

        } catch (Throwable ex) {
            
            return false;
        }
        return true;
    }

    public IObjectScript getMember(String member) {
        IObjectScript result = this.getMembers().get(member);

        return result;
    }

    public Map<String, IObjectScript> getMembers() {
        return members;
    }

    @Override
    public IScript copy() {
        JavaObjectScript clonedCopy = new JavaObjectScript();

        for (Entry<String, IObjectScript> entry : this.getMembers().entrySet()) {

            clonedCopy.setMember(entry.getKey(), (IObjectScript)entry.getValue().copy());
        }
        return clonedCopy;
    }

    @Override
    public IScript execute() throws Exception {
        return null;
    }

    @Override
    public Object toJava() throws Exception {
        return this.getJavaObject();
    }


    @Override
    public String toUI() {
        return this.toString();
    }

    
    public IObject refer(IObjectScript other) throws Exception {
        return this.getMember(other.getVariable().getName());
    }

    @Override
    public String toString() {
        return this.members.toString();
    }

    /* @Override
     * public boolean isVariable() {
     * return true;
     * }*/

    @Override
    public Variable getVariableValue() {
        if (this.variableValue == null) {

            this.variableValue = Variable.createDummyStringVariable(this.toString());
        }
        return this.variableValue;
    }

    @Override
    public Variable getVariable() {
        return this.getVariableValue();
    }
    private Variable variableValue;

    @Override
    public void setVariable(Variable value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setVariableValue(Variable value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String mangle() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int length() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    

    @Override
    public Map<Object, Object> toJavaMap() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isProxy() {
        return false;
    }

    @Override
    public IObjectScript getActualObjectScript() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject add(IObject otherObject) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject subtract(IObject otherObject) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject multiply(IObject otherObject) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject divide(IObject otherObject) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject modulus(IObject otherObject) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject equalTo(IObject otherObject) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject greaterThan(IObject otherObject) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject lessThan(IObject otherObject) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject greaterThanEqualTo(IObject otherObject) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject lessThanEqualTo(IObject otherObject) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject and(IObject otherObject) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject or(IObject otherObject) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean passByReference() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IHawkObject refer(IHawkObject otherScript) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IHawkObject assign(IHawkObject otherScript) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IHawkObject arrayBracket(IHawkObject otherScript) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
