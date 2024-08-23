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
    private Map<Integer, IScriptCache> cachedScriptCache = new TreeMap<>();

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
