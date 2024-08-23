/*
 * This file is part of j-hawk
 *  
 *

 * 
 *
 * 
 */

package org.commons.logger;

import org.apache.log4j.Logger;

/**
 *
 * @author manosahu
 */
public class CommonLogger implements ILogger {

    private Logger logger;

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void info(String msg) {
        logger.info(msg);
    }

    @Override
    public void error(String msg) {
        logger.error(msg);
    }

    @Override
    public void error(String msg, Throwable th) {
        logger.error(msg, th);
    }

    @Override
    public void error(Throwable th) {
        logger.error(th);
    }

    @Override
    public void warn(String msg) {
        logger.warn(msg);
    }

    @Override
    public void warn(String msg, Throwable th) {
        logger.warn(msg,th);
    }

    @Override
    public void warn(Throwable th) {
        logger.warn(th);
    }

}
