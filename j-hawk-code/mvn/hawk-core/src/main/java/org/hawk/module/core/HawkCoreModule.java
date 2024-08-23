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


package org.hawk.module.core;

import org.hawk.module.AbstractModule;
import org.hawk.module.IModule;

/**
 * Placeholder for all the hawk core modules that hawk supports.
 * @version 1.0 11 Jul, 2010
 * @author msahu
 * @see IModule
 * @see SystemModule
 * @see HttpModule
 * @see MathModule
 */


public class HawkCoreModule extends AbstractModule implements ICoreModule{

    public HawkCoreModule(){
        
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
}




