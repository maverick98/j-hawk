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
