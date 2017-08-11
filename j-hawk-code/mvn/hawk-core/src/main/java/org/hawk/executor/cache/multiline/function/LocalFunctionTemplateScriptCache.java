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
