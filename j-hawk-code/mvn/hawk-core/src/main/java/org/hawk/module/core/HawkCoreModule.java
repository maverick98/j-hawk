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




