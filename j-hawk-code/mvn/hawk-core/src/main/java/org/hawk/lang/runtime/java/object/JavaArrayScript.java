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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import org.commons.ds.operator.OperatorEnum;
import org.hawk.ds.exp.IHawkObject;
import static org.hawk.lang.constant.HawkLanguageKeyWord.var;
import org.hawk.lang.enumeration.ArrayTypeEnum;
import org.hawk.lang.enumeration.VarTypeEnum;
import org.hawk.lang.object.ArrayDeclScript;
import org.hawk.lang.object.IObjectScript;
import org.hawk.lang.object.LocalVarDeclScript;
import org.hawk.lang.type.Variable;

/**
 *
 * @author manoranjan
 */
public class JavaArrayScript extends ArrayDeclScript  {

    private Object javaObject ;

    public Object getJavaObject() {
        return javaObject;
    }

    public void setJavaObject(Object javaObject) {
        this.javaObject = javaObject;
    }
    @Override
    public Map<Integer, IObjectScript> getMembers() {
        return this.members;
    }

    @Override
    public IObjectScript getMember(Integer index) {
        IObjectScript result = null;

        if (this.getMembers().containsKey(index)) {
            result = this.members.get(index);
        }

        return result;

    }

    @Override
    public void setMembers(Map<Integer, IObjectScript> members) {

        this.members = members;

    }

    @Override
    public int length() {
        int result = -1;

        result = this.members.size();
        return result;
    }

    @Override
    public boolean setMember(Integer i, IObjectScript data) throws Exception {

        if (i == null || i < 1) {
            throw new Exception("Invalid index access of array {" + this.getArrayVariable().getName() + "}");
        }
        if (data == null) {
            throw new Exception("Can't set null at index {" + i + "of array {" + this.getArrayVariable().getName() + "}");
        }
        this.getMembers().put(i, data);
        return true;
    }
    

    @Override
    public Variable getVariable() {
        return this.arrayVariable;
    }

    @Override
    public void setVariable(Variable value) {
        this.arrayVariable = value;
    }

    @Override
    public void setVariableValue(Variable value) {

    }

    @Override
    public Variable getVariableValue() {
        if (this.arrayVariableValue == null) {
            this.arrayVariableValue = new Variable(
                    VarTypeEnum.VAR,
                    null,
                    this.toString(),
                    ArrayTypeEnum.VAR);
            this.arrayVariable = this.arrayVariableValue;
        }
        return arrayVariableValue;
    }

    @Override
    public Map<Integer, IObjectScript> copyMembers() {

        Map<Integer, IObjectScript> copyMembers = new TreeMap<>();

        this.members.entrySet().stream().forEach((entry) -> {
            copyMembers.put(entry.getKey(), (IObjectScript)entry.getValue().copy());
        });

        return copyMembers;
    }

    @Override
    public JavaArrayScript copy() {

        JavaArrayScript clonedArrayScript = new JavaArrayScript();
        clonedArrayScript.setArrayType(this.getArrayType());
        clonedArrayScript.setArrayVariable(this.getArrayVariable().copy());


        clonedArrayScript.setMembers(this.copyMembers());


        return clonedArrayScript;
    }

    @Override
    public String mangle() {
        return "_" + var + OperatorEnum.ARRAYBRACKET.getOperator() + "_";
    }

    @Override
    public IHawkObject assign(IHawkObject otherScript) throws Exception {
        if (otherScript == null) {
            throw new Exception("Can not assign null...");
        }
        if (!(otherScript instanceof JavaArrayScript)) {
            throw new Exception("Type mismatch");
        }
        JavaArrayScript otherArrayDeclScript = (JavaArrayScript) otherScript;
        this.setMembers(otherArrayDeclScript.getMembers());
        this.setVariable(otherArrayDeclScript.getVariable());
        this.setVariableValue(otherArrayDeclScript.getVariableValue());

        return this;
    }

    @Override
    public String toUI() {
        List<String> result = new ArrayList<>();
        Map<Integer, IObjectScript> map = this.members;

        map.entrySet().stream().forEach((entry) -> {
            result.add(entry.getValue().toUI());
        });
        return result.toString();
    }

    @Override
    public Map<Object, Object> toJavaMap() throws Exception {

        Map<Object, Object> javaMap = new LinkedHashMap<>();
        Map<Object, Object> memberMap = new LinkedHashMap<>();

        Map<Integer, IObjectScript> map = this.members;


        for (int i = 1; i <= map.size(); i++) {
            Map<Object, Object> innerJavaMap = map.get(i).toJavaMap();
            memberMap.put(i, innerJavaMap);
        }
        javaMap.put(this.getVariable().getName(), memberMap);
        return javaMap;
    }

    @Override
    public Object toJava() throws Exception {
        return this.getJavaObject();
    }

    public static boolean isArray(Object javaObject) {
        if(!(javaObject instanceof List || javaObject instanceof Set || javaObject instanceof  Object[] )){
            return false;
        }
        
        return true;
    }
    public static JavaArrayScript createScript(Object javaObject) throws Exception {
        if(!JavaArrayScript.isArray(javaObject)){
            return null;
        }
        JavaArrayScript javaArrayScript = new JavaArrayScript();

        javaArrayScript.setJavaObject(javaObject);

        if(javaObject instanceof List){
            List l = (List)javaObject;
            int i=1;
            for(Object obj:l){

                javaArrayScript.setMember(i, JavaObjectScript.createScript(obj));
                i++;

            }
        }else if(javaObject instanceof Set){
            Set s = (Set)javaObject;
            int i=1;
            for(Object obj:s){

                javaArrayScript.setMember(i, JavaObjectScript.createScript(obj));
                i++;
            }
        }else if(javaObject instanceof Object[]){
            Object[] arr = (Object [])javaObject;
            int i=1;
            for(Object obj:arr){
                javaArrayScript.setMember(i, JavaObjectScript.createScript(obj));
                i++;
            }
        }

        return javaArrayScript;
    }
    @Override
    public IHawkObject refer(IHawkObject other) throws Exception {
       IObjectScript otherScript = (IObjectScript)other;
       String lengthStr = otherScript.getVariable().getName();
       if("length".equals(lengthStr)){
           return LocalVarDeclScript.createDummyIntScript(this.length());
       }else{
           return null;
       }
    }
}
