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
package org.hawk.module.plugin;

import org.hawk.module.AbstractModule;

/**
 *
 * @author Manoranjan Sahu
 */
public abstract class HawkPluginModule extends AbstractModule implements IPluginModule {

    public HawkPluginModule() {
        
    }

    @Override
    public boolean startUp() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean reset() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    @Override
    public abstract String getPluginName();

   
}
