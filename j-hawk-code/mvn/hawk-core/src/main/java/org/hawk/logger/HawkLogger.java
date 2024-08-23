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
                 for(Object  param:params){
                   logger.log(Level.INFO,param.toString());
                }
            }
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
