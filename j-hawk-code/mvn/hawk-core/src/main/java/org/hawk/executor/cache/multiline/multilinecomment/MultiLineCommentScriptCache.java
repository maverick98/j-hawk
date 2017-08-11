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
package org.hawk.executor.cache.multiline.multilinecomment;

import java.util.Map;
import org.common.di.AppContainer;

import org.hawk.executor.cache.multiline.MultiLineScriptCache;
import org.hawk.lang.comment.MultiLineCommentScript;
 import org.commons.reflection.Create;
import org.common.di.ScanMe;

/**
 *
 * @author Manoranjan Sahu
 */
@ScanMe(true)
   
   
public class MultiLineCommentScriptCache extends  MultiLineScriptCache implements IMultiLineCommentScriptCache{

    /**
     * map containing multiline globals
     */
    protected Map<Integer, Integer> globalMultiLineScriptMap = null;

    @Override
    public boolean cache() throws Exception {
        this.globalMultiLineScriptMap = MultiLineCommentScript.cacheGlobalMultiLineScripts();

        return true;
    }

    @Override
    public boolean isInsideMultiLineScript(int i) {
        boolean status = false;

        if (this.globalMultiLineScriptMap != null) {

            for (Map.Entry<Integer, Integer> entry : this.globalMultiLineScriptMap.entrySet()) {

                status = (i >= entry.getKey() && i <= entry.getValue());

                if (status) {

                    break;

                }

            }

        }

        return status;
    }

    

    @Override
    public boolean reset() {
        globalMultiLineScriptMap = null;
        return true;
    }
    @Override
    @Create
    public MultiLineScriptCache create() {
        return AppContainer.getInstance().getBean(MultiLineCommentScriptCache.class);
    }
    
}
