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
