/*
 * This file is part of j-hawk
 * CopyLeft (C) 2012-2013 Manoranjan Sahu, All Rights are left.
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
        Set<VerticalStrip> all = new TreeSet<VerticalStrip>();
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
