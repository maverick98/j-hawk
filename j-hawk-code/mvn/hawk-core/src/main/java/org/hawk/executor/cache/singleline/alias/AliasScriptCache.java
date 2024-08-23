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

package org.hawk.executor.cache.singleline.alias;

import org.common.di.AppContainer;

import org.hawk.executor.cache.singleline.SingleLineScriptCache;
import org.hawk.lang.util.AliasScript;
 import org.commons.reflection.Create;
import org.common.di.ScanMe;

/**
 *
 * @author Manoranjan Sahu
 */
@ScanMe(true)
   
   
public class AliasScriptCache extends SingleLineScriptCache implements IAliasScriptCache{

    @Override
    public boolean cache() throws Exception {
        return AliasScript.getInstance().parseAliases(this.getHawkInput().getScriptMap());
    }

   

    @Override
    public boolean reset() {
         AliasScript.getInstance().reset();
         return true;
    }
    @Override
    @Create
    public SingleLineScriptCache create() {
        return AppContainer.getInstance().getBean( AliasScriptCache.class);
    }

}
