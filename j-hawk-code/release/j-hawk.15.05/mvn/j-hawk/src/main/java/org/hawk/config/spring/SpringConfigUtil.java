/*
 * This file is part of j-hawk
 * CopyLeft (C) 2012-2013 Manoranjan Sahu, All Rights are left.
 * Use it at your own risk.
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

package org.hawk.config.spring;

import org.common.di.AppContainer;
import org.hawk.di.spring.SpringConfig;

/**
 *
 * @author Manoranjan Sahu
 */

public class SpringConfigUtil {

    private static final SpringConfigUtil theInstance = new SpringConfigUtil();
    
    private SpringConfigUtil(){
        
    }

    public static SpringConfigUtil getInstance(){
        return theInstance;
    }
    /**
     *
     * @param clazzLoader
     * @param packages
     * @return
     */
    public boolean configure( ClassLoader clazzLoader,String ... packages) {
          return AppContainer.getInstance().registerConfig(SpringConfig.class);
    }

    
   
    

    
}
