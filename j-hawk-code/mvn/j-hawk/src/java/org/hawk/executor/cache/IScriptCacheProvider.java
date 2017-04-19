/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.hawk.executor.cache;



/**
 *
 * @author manosahu
 */
public interface IScriptCacheProvider {
    
    /**
     * Returns true if scripts are not cached.
     * @return 
     */
    public boolean isEmpty();
    
    /**
     * 
     * @return
     * @throws Exception 
     */
    public boolean cache() throws Exception;
    
    public boolean visitScriptCache(IScriptCacheVisitor scriptCacheVisitor) throws Exception;
}
