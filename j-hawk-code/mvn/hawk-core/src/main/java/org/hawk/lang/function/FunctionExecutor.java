/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hawk.lang.function;

import java.util.Map;

import org.hawk.executor.cache.multiline.function.IFunctionScriptCache;
import org.hawk.lang.IScript;
import org.hawk.lang.object.IObjectScript;
import org.hawk.module.cache.IAllModuleCache;
import org.hawk.module.task.SubTaskContainer;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author manosahu
 */
//@Component(FUNCTIONEXECUTOR)
//@Qualifier(DEFAULTQUALIFIER)
public class FunctionExecutor implements IFunctionExecutor {

    @Autowired(required = true)
    //@Qualifier(FUNCTIONSCRIPTCACHE)
    private IFunctionScriptCache functionScriptCache;

    @Autowired(required = true)
    //@Qualifier(MODULECACHE)
    private IAllModuleCache moduleCache;

    @Override
    public IObjectScript execute(final FunctionInvocationInfo functionInvocationInfo) throws Exception {
        String functionName = functionInvocationInfo.getFunctionName();
        
        if (functionInvocationInfo.getFunctionScript() == null) {

            functionInvocationInfo.setFunctionScript(functionScriptCache.findFunctionScript(functionName, functionInvocationInfo.getParamMap()));

        }
        IObjectScript status;
        final FunctionScript functionScript = functionInvocationInfo.getFunctionScript();
       
        AbstractFunctionStackUtility functionStackUtility;
        final Map<Integer, IObjectScript> paramMap = functionInvocationInfo.getParamMap();
        if (functionScript != null) {
            functionStackUtility = new AbstractFunctionStackUtility() {

                @Override
                public IScript execute() throws Exception {

                    return functionScript.executeDefaultForLoopScript(paramMap);
                }
            };
        } else {
            //make an attempt to find it modules subtask

            final SubTaskContainer subTaskContainer = moduleCache.lookUpSubTask(functionName);

            subTaskContainer.parseModuleParameters(paramMap);
            functionStackUtility = new AbstractFunctionStackUtility() {

                @Override
                public IScript execute() throws Exception {

                    return subTaskContainer.execute();
                }
            };

        }

        status = functionStackUtility.pushExecutePop(functionInvocationInfo);

        return status;
    }

}
