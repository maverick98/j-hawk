/*
 * This file is part of j-hawk
 *  
 *
 * 
 *
 *  
 *  
 *  
 *  
 *
 * 
 * 
 *
 * 
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.hawk.executor.cache.multiline.function;

import java.util.Set;
import org.common.di.ScanMe;
import org.commons.reflection.Create;

import org.hawk.executor.cache.multiline.MultiLineScriptCache;
import org.hawk.lang.VerticalStrip;
import org.hawk.lang.function.FunctionNode;
import org.hawk.lang.function.FunctionScript;
import org.hawk.logger.HawkLogger;


/**
 *
 * @author manosahu
 */
@ScanMe(false)
public abstract class AbstractFunctionTemplateScriptCache extends MultiLineScriptCache implements IFunctionTemplateScriptCache {

    private static final HawkLogger logger = HawkLogger.getLogger(AbstractFunctionTemplateScriptCache.class.getName());
    /**
     * A map containing key as function name and value as
     * <tt>FunctionScript</tt>
     *
     * @see FunctionScript
     */

    private FunctionNode rootFunctionNode = null;

    @Override
    public FunctionNode getRootFunctionNode() {
        return rootFunctionNode;
    }

    public void setRootFunctionNode(FunctionNode rootFunctionNode) {
        this.rootFunctionNode = rootFunctionNode;
    }

    @Override
    public abstract boolean cache() throws Exception ;

    @Override
    public FunctionNode createRootFunctionNode(Set<VerticalStrip> all) throws Exception {

        FunctionNode result = FunctionScript.parseFunctions(this.getHawkInput().getScriptMap(), all);

        return result;
    }

    @Override
    public boolean reset() {

        this.setRootFunctionNode(null);
        return true;
    }

    @Override
    @Create
    public abstract MultiLineScriptCache create();
    @Override
    public boolean isInsideMultiLineScript(int i) {
        boolean status = false;

        if (this.getRootFunctionNode() != null) {

            status = this.getRootFunctionNode().isInsideFunctionScript(this.getRootFunctionNode(), i);

        }

        return status;
    }

}
