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
import java.util.TreeSet;
import org.common.di.AppContainer;

import org.hawk.executor.cache.multiline.MultiLineScriptCache;
import org.hawk.lang.VerticalStrip;
import org.hawk.lang.function.FunctionNode;
import org.hawk.logger.HawkLogger;
 import org.commons.reflection.Create;

/**
 *
 * @author manosahu
 */
/*@ScanMe(true)
   
   
@Scope(BeanDefinition.SCOPE_PROTOTYPE)*/
public class LocalFunctionTemplateScriptCache extends AbstractFunctionTemplateScriptCache {
   private static final HawkLogger logger = HawkLogger.getLogger(LocalFunctionTemplateScriptCache.class.getName());
   

    @Override
    public boolean cache() throws Exception {
        Set<VerticalStrip> all = new TreeSet<>();
        VerticalStrip theWholeStrip = new VerticalStrip(0, this.getHawkInput().getScriptMap().size());
        all.add(theWholeStrip);
        FunctionNode createdRootFunctionNode = this.createRootFunctionNode(all);
        this.setRootFunctionNode(createdRootFunctionNode);
        return true;
    }

   

    

    //@Override
    //@Create
    public MultiLineScriptCache create() {
        return AppContainer.getInstance().getBean(LocalFunctionTemplateScriptCache.class);
    }

    
}
