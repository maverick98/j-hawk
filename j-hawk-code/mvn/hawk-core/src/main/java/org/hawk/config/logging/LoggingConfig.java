/*
 * This file is part of j-hawk
 * CopyLeft (C) 2012-2013 Manoranjan Sahu, All Rights are left.
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
