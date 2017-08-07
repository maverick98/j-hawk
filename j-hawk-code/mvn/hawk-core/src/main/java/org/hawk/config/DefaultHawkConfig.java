/*
 * This file is part of j-hawk
 * CopyLeft (C) 2012-2013 Manoranjan Sahu, All Rights are left.
 *
 * 
 */
package org.hawk.config;

import java.io.File;

import org.hawk.logger.HawkLogger;
import org.hawk.main.HawkTargetSetting;
import org.hawk.sequence.ISequenceProvider;

/**
 * This is a startup class.
 * <p> Apart from initializing Hawk, it kicks off the startup of<br>
 * target application.<br>
 * Note: The target module should implement <tt>IModule</tt>.
 *
 * @VERSION 1.0 9 Apr, 2010
 * @author msahu
 */
public class DefaultHawkConfig implements IHawkConfig {

    private static final HawkLogger logger = HawkLogger.getLogger(DefaultHawkConfig.class.getName());
    private HawkTargetSetting hawkTargetSetting = new HawkTargetSetting();
    private ISequenceProvider cachingSequence = new HawkConfigSequenceProvider();

    public HawkTargetSetting getHawkTargetSetting() {
        return hawkTargetSetting;
    }

    public void setHawkTargetSetting(HawkTargetSetting hawkTargetSetting) {
        this.hawkTargetSetting = hawkTargetSetting;
    }

    public boolean reConfigure(String setttingsConf) {
        return reConfigure(new File(setttingsConf));
    }

    public boolean reConfigure(File setttingsConf) {
        boolean status = false;

        //status = this.configureInternal(setttingsConf);
        return status;
    }

    @Override
    public Integer getSequence() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return cachingSequence.getSequence(this.getClass());
    }

    @Override
    public boolean configure() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
