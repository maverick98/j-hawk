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

import org.hawk.executor.cache.multiline.function.FunctionScriptCache;
import org.hawk.executor.cache.multiline.function.GlobalFunctionTemplateScriptCache;
import org.hawk.executor.cache.multiline.multilinecomment.MultiLineCommentScriptCache;
import org.hawk.executor.cache.multiline.structure.StructureDefinitionScriptCache;
import org.hawk.executor.cache.singleline.alias.AliasScriptCache;
import org.hawk.executor.cache.singleline.globalvar.GlobalVariableScriptCache;
import org.hawk.executor.cache.singleline.xmlstruct.XMLStructScriptCache;
import org.hawk.sequence.DefaultSequenceProvider;

/**
 *
 * @author Manoranjan Sahu
 */
   
   
public class ScriptCachingSequenceProvider extends DefaultSequenceProvider {

    public ScriptCachingSequenceProvider() {
        this.getSequenceMap().put(StructureDefinitionScriptCache.class, 1000);
        this.getSequenceMap().put(MultiLineCommentScriptCache.class, 2000);
        this.getSequenceMap().put(GlobalFunctionTemplateScriptCache.class, 3000);
        this.getSequenceMap().put(AliasScriptCache.class, 4000);
        this.getSequenceMap().put(FunctionScriptCache.class, 5000);
        this.getSequenceMap().put(XMLStructScriptCache.class, 6000);
        this.getSequenceMap().put(GlobalVariableScriptCache.class, 7000);
        
       // this.getSequenceMap().put(LocalFunctionTemplateScriptCache.class, 8000);

    }
}
