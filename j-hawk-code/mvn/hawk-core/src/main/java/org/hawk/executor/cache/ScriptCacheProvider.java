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
package org.hawk.executor.cache;

import java.util.Map;
import java.util.TreeMap;

import org.hawk.executor.cache.multiline.MultiLineScriptCacheFactory;
import org.hawk.executor.cache.singleline.SingleLineScriptCacheFactory;
import org.hawk.logger.HawkLogger;

/**
 *
 * @author Manoranjan Sahu
 */
   
   
public class ScriptCacheProvider  implements IScriptCacheProvider{

    private static final HawkLogger logger = HawkLogger.getLogger(ScriptCacheProvider.class.getName());
    private Map<Integer, IScriptCache> cachedScriptCache = new TreeMap<Integer, IScriptCache>();

    public ScriptCacheProvider(){
        
    }
    @Override
    public boolean isEmpty() {

        return cachedScriptCache.isEmpty();
    }
    
    @Override
    public boolean cache() throws Exception {
        if (this.isEmpty()) {
            cachedScriptCache.putAll(MultiLineScriptCacheFactory.getMultiLineScriptCache());
            cachedScriptCache.putAll(SingleLineScriptCacheFactory.getSingleLineScriptCache());
        }
        
        return !this.isEmpty();
    }

    public Map<Integer, IScriptCache> getScriptCache() throws Exception {
        this.cache();
        return cachedScriptCache;
    }
   
    @Override
    public boolean visitScriptCache(IScriptCacheVisitor scriptCacheVisitor) throws Exception {

        boolean status;
        if (!this.isEmpty()) {
            for (Map.Entry<Integer, IScriptCache> entry : cachedScriptCache.entrySet()) {
                scriptCacheVisitor.visit(entry.getValue());
            }
            status = true;
        } else {
            status = false;
        }

        return status;
    }

  

}
