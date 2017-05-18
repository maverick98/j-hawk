/*
 * This file is part of j-hawk
 * CopyLeft (C) 2012-2013 Manoranjan Sahu, All Rights are left.
 *
*
 */
package org.commons.logger;

import org.apache.log4j.Logger;

/**
 *
 * @author manosahu
 */
public class LoggerFactory {

    public static ILogger getLogger(String name) {

        ILogger logger = new CommonLogger();
        logger.setLogger(Logger.getLogger(name));
        return logger;
    }

    public static ILogger getLogger(String name, ILogger logger) {

        //ILogger hawkLogger = new CommonLogger();
        logger.setLogger(Logger.getLogger(name));
        return logger;
    }
}
