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
package org.hawk.executor.cache.singleline.xmlstruct;

import org.common.di.AppContainer;

import org.hawk.executor.cache.singleline.SingleLineScriptCache;
import org.hawk.lang.object.XMLStructIncludeScript;
 import org.commons.reflection.Create;
import org.common.di.ScanMe;

/**
 *
 * @author Manoranjan Sahu
 */
@ScanMe(true)
   
   
public class XMLStructScriptCache extends SingleLineScriptCache implements IXMLStructScriptCache{

    @Override
    public boolean cache() throws Exception {
        return XMLStructIncludeScript.getInstance().parseXMLStructs(this.getHawkInput().getScriptMap());
    }

   
    @Override
    @Create
    public SingleLineScriptCache create() {
        return AppContainer.getInstance().getBean( XMLStructScriptCache.class);
    }

    @Override
    public boolean reset() {
        
        return XMLStructIncludeScript.getInstance().reset();
    }
    
}
