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

package org.hawk.config;

import org.hawk.config.logging.LoggingConfig;
import org.hawk.config.shutdown.ShutdownHookConfig;
import org.hawk.sequence.DefaultSequenceProvider;

/**
 *
 * @author Manoranjan Sahu
 */
   
   
public class HawkConfigSequenceProvider extends DefaultSequenceProvider {

     public HawkConfigSequenceProvider() {
        this.getSequenceMap().put(ShutdownHookConfig.class, 2000);
        this.getSequenceMap().put(LoggingConfig.class, 3000);
        this.getSequenceMap().put(HawkModuleConfig.class, 4000);
       
        

    }
}
