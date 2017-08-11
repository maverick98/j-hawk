/*
 * This file is part of j-hawk
 * CopyLeft (C) BigBang<->BigCrunch Manoranjan Sahu, All Rights are left.
 *
 *
 * 
 */
package org.hawk.config.shutdown;

import org.hawk.config.DefaultHawkConfig;
import org.hawk.config.IHawkConfig;
import org.common.di.AppContainer;
import org.hawk.shutdown.HawkShutdown;

import org.common.di.ScanMe;
import org.commons.reflection.Create;

/**
 *
 * @author Manoranjan Sahu
 */
@ScanMe(true)
   
   
public class ShutdownHookConfig extends DefaultHawkConfig {

    @Override
    public boolean configure() {
        Runtime.getRuntime().addShutdownHook(new Thread(new HawkShutdown()));
        return true;
    }

    @Override
    public Integer getSequence() {
        return super.getSequence();
    }

    @Create
    public IHawkConfig create() {
        return AppContainer.getInstance().getBean(ShutdownHookConfig.class);
    }

    @Override
    public String toString() {
        return "ShutdownHookConfig{" + '}';
    }
    
    
}
