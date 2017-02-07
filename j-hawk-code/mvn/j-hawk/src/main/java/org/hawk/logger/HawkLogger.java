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
package org.hawk.logger;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.common.di.AppContainer;
import org.hawk.executor.command.HawkCommandParser;
import org.hawk.output.HawkOutput;

/**
 *
 * @author msahu
 */
public class HawkLogger {

    private Logger logger;

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public static HawkLogger getLogger(String name) {

        HawkLogger hawkLogger = new HawkLogger();
        hawkLogger.setLogger(Logger.getLogger(name));
        return hawkLogger;
    }

    public void info(String msg) {
        this.info(msg, null);
    }

    public void info(String msg, Object[] params) {
        HawkCommandParser hawkCommandParser = AppContainer.getInstance().getBean(HawkCommandParser.class);
        if (hawkCommandParser.shouldUseLogger()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Thread [").append(Thread.currentThread().getName()).append("] ").append(msg);
            if (params != null) {
                logger.log(Level.INFO, msg, params);
            } else {
                logger.log(Level.INFO, sb.toString());
            }
        }
       for(Object  param:params){
           logger.log(Level.INFO,param.toString());
       }
        HawkOutput hawkOutput = AppContainer.getInstance().getBean(HawkOutput.class);
        hawkOutput.writeOutput(msg);
    }

    public void error(String msg) {
        Object[] params = null;
        this.error(msg, params);
    }

    public void error(String msg, Object[] params) {
        HawkCommandParser hawkCommandParser = AppContainer.getInstance().getBean(HawkCommandParser.class);
        if (hawkCommandParser.shouldUseLogger()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Thread {").append(Thread.currentThread().getName()).append("} ").append(msg);
            if (params != null) {
                logger.log(Level.SEVERE, sb.toString(), params);
            } else {
                logger.log(Level.SEVERE, sb.toString());
            }
        }
        HawkOutput hawkOutput = AppContainer.getInstance().getBean(HawkOutput.class);
        hawkOutput.writeError(msg);

    }

    public void error(String msg, Throwable th) {
        th.printStackTrace();
        HawkCommandParser hawkCommandParser = AppContainer.getInstance().getBean(HawkCommandParser.class);
        if (hawkCommandParser.shouldUseLogger()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Thread {").append(Thread.currentThread().getName()).append("} ").append(msg);
            logger.log(Level.SEVERE, sb.toString(), th);

        }
        HawkOutput hawkOutput = AppContainer.getInstance().getBean(HawkOutput.class);
        hawkOutput.writeError(msg);
        if (hawkOutput.getError() != null) {
            th.printStackTrace(hawkOutput.getError());
        }

    }

    public void error(Throwable th) {

        this.error("", th);

    }

    public void warn(String msg) {
        this.warn(msg, null);

    }

    public void warn(String msg, Object[] params) {
        HawkCommandParser hawkCommandParser = AppContainer.getInstance().getBean(HawkCommandParser.class);
        if (hawkCommandParser.shouldUseLogger()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Thread {").append(Thread.currentThread().getName()).append("} ").append(msg);
            if (params != null) {
                logger.log(Level.WARNING, sb.toString(), params);
            } else {
                logger.log(Level.WARNING, sb.toString());
            }
        }
        HawkOutput hawkOutput = AppContainer.getInstance().getBean(HawkOutput.class);
        hawkOutput.writeError(msg);

    }

}
