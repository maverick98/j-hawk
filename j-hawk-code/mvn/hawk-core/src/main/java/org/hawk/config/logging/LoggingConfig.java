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
package org.hawk.config.logging;

import org.apache.log4j.PropertyConfigurator;
import org.hawk.config.DefaultHawkConfig;
import org.hawk.config.IHawkConfig;
import static org.hawk.constant.HawkConstant.LOG4JPROPSKEY;
import org.common.di.AppContainer;

import org.common.di.ScanMe;
import org.commons.reflection.Create;

/**
 *
 * @author Manoranjan Sahu
 */
@ScanMe(true)
   
   
public class LoggingConfig extends DefaultHawkConfig {

    @Override
    public boolean configure() {
        return this.configureLogging();
    }

    @Override
    public Integer getSequence() {
        return super.getSequence();
    }

    private boolean configureLogging() {

        boolean loggerConfigured;

        String log4jPropsPath = this.getHawkTargetSetting().getProperty(LOG4JPROPSKEY);

        if (log4jPropsPath != null) {

            PropertyConfigurator.configure(log4jPropsPath);

            loggerConfigured = true;

        } else {

            loggerConfigured = false;

        }

        return loggerConfigured;

    }

    @Create
    public IHawkConfig create() {
        return AppContainer.getInstance().getBean(LoggingConfig.class);
    }
}
