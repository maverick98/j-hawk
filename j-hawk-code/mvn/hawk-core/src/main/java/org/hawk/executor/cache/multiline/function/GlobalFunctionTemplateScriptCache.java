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
package org.hawk.executor.cache.multiline.function;

import java.util.Set;
import java.util.TreeSet;
import org.common.di.AppContainer;

import org.hawk.executor.cache.multiline.MultiLineScriptCache;
import org.hawk.lang.VerticalStrip;
import org.hawk.lang.function.FunctionNode;
import org.hawk.logger.HawkLogger;
 import org.commons.reflection.Create;
import org.common.di.ScanMe;

/**
 *
 * @author Manoranjan Sahu
 */
@ScanMe(true)
   
   
public class GlobalFunctionTemplateScriptCache extends AbstractFunctionTemplateScriptCache {

    private static final HawkLogger logger = HawkLogger.getLogger(GlobalFunctionTemplateScriptCache.class.getName());
   

    @Override
    public boolean cache() throws Exception {
        Set<VerticalStrip> all = new TreeSet<>();
        VerticalStrip theWholeStrip = new VerticalStrip(0, this.getHawkInput().getScriptMap().size());
        all.add(theWholeStrip);
        FunctionNode createdRootFunctionNode = this.createRootFunctionNode(all);
        this.setRootFunctionNode(createdRootFunctionNode);
        return true;
    }

   

    

    @Override
    @Create
    public MultiLineScriptCache create() {
        return AppContainer.getInstance().getBean(GlobalFunctionTemplateScriptCache.class);
    }

    

}
