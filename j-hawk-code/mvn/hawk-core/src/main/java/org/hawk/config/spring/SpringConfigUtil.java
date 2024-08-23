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
