


package org.hawk.module.lib;

import org.hawk.ds.Stack;
import org.hawk.module.annotation.Module;
import org.hawk.module.annotation.SubTask;
import org.hawk.module.script.ArrayDeclScript;
import org.hawk.module.script.FunctionScript.FunctionInvocationInfo;
import org.hawk.module.script.IScript;
import org.hawk.util.HawkUtil;
import java.util.Map;
import java.util.Collections;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import org.hawk.module.AbstractModule;
/**
 *
 * @version 1.0 10 Jul, 2010
 * @author msahu
 */
@Module(name="SystemModule")
public class SystemModule extends AbstractModule{

    private static final ThreadLocal<Stack<FunctionInvocationInfo>> functionStack = new ThreadLocal<Stack<FunctionInvocationInfo>>();

    static{
        Stack<FunctionInvocationInfo> functionInvocationStack = new Stack<FunctionInvocationInfo>();
        functionStack.set(functionInvocationStack);
    }
    public static ThreadLocal<Stack<FunctionInvocationInfo>> getFunctionStack() {
        return functionStack;
    }

    @Override
    public String toString() {
        return this.getName();
    }
    
    public String getName() {
        return "SystemModule";
    }
    public boolean startUp() {
        throw new UnsupportedOperationException("Not supported yet.");
    }



    public boolean reset() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @SubTask(name="execute",sequence = 1,ignoreException=false,hawkParam="var command")
    public String execute(Object ... args) throws Exception{

        return HawkUtil.executeProcess((String)args[0])+"";

    }

    @SubTask(name="dumpStack",sequence = 1,ignoreException=false,hawkParam="")
    public String dumpFunctionStack(Object ... args) throws Exception{

        String stackDump = functionStack.get().dump();
        System.out.println(stackDump);
        return stackDump;

    }

    @SubTask(name="currentTime",sequence = 1,ignoreException=false,hawkParam="")
    public long currentTime(Object ... args) throws Exception{

       return System.currentTimeMillis();

    }

    @SubTask(name="length",sequence = 1,ignoreException=false,hawkParam="var array[] | arg")
    public int length(Object ... args) throws Exception{

       IScript script = (IScript)args[0];
       return script.length();

    }
    @SubTask(name="sort",sequence = 1,ignoreException=false,hawkParam="var array")
    public boolean sort(Object ... args) throws Exception{

       ArrayDeclScript arrayDeclScript = (ArrayDeclScript)args[0];
       Map<Integer,IScript>  map = arrayDeclScript.getMembers();
       Collection<IScript> memberColl = map.values();
       List<IScript> members = new ArrayList<IScript>();
       for(IScript script:memberColl){
            members.add(script);
       }
       Collections.sort(members, new Comparator<IScript>(){

            public int compare(IScript myScript, IScript herScript) {
                return myScript.toUI().compareTo(herScript.toUI());
            }

       });
       for(int i =0;i<map.size();i++){
           map.put(i+1, members.get(i));
       }
       arrayDeclScript.setMembers(map);
       return true;
    }
}




