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

import org.apache.log4j.Logger;
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
        HawkCommandParser hawkCommandParser = AppContainer.getInstance().getBean( HawkCommandParser.class);
        if (hawkCommandParser.shouldUseLogger()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Thread {").append(Thread.currentThread().getName()).append("} ").append(msg);
            logger.info(sb.toString());
        }
        HawkOutput hawkOutput = AppContainer.getInstance().getBean( HawkOutput.class);
        hawkOutput.writeOutput(msg);
    }

    public void error(String msg) {
        HawkCommandParser hawkCommandParser = AppContainer.getInstance().getBean( HawkCommandParser.class);
        if (hawkCommandParser.shouldUseLogger()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Thread {").append(Thread.currentThread().getName()).append("} ").append(msg);
            logger.fatal(sb.toString());
        }
        HawkOutput hawkOutput = AppContainer.getInstance().getBean( HawkOutput.class);
        hawkOutput.writeError(msg);

    }

    public void error(String msg, Throwable th) {
        th.printStackTrace();
        HawkCommandParser hawkCommandParser = AppContainer.getInstance().getBean( HawkCommandParser.class);
        if (hawkCommandParser.shouldUseLogger()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Thread {").append(Thread.currentThread().getName()).append("} ").append(msg);
            logger.fatal(sb.toString(), th);
        }
        HawkOutput hawkOutput = AppContainer.getInstance().getBean( HawkOutput.class);
        hawkOutput.writeError(msg);
        if (hawkOutput.getError() != null) {
            th.printStackTrace(hawkOutput.getError());
        }

    }

    public void error(Throwable th) {

        this.error("", th);

    }

    public void warn(String msg) {
        HawkCommandParser hawkCommandParser = AppContainer.getInstance().getBean( HawkCommandParser.class);
        if (hawkCommandParser.shouldUseLogger()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Thread {").append(Thread.currentThread().getName()).append("} ").append(msg);
            logger.warn(sb.toString());
        }
        HawkOutput hawkOutput = AppContainer.getInstance().getBean(HawkOutput.class);
        hawkOutput.writeError(msg);

    }

    public void warn(String msg, Throwable th) {
        HawkCommandParser hawkCommandParser = AppContainer.getInstance().getBean(HawkCommandParser.class);
        if (hawkCommandParser.shouldUseLogger()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Thread {").append(Thread.currentThread().getName()).append("} ").append(msg);
            logger.warn(sb.toString(), th);
        }
        HawkOutput hawkOutput = AppContainer.getInstance().getBean(HawkOutput.class);
        hawkOutput.writeError(msg);
        th.printStackTrace(hawkOutput.getError());
    }

    
    public void warn(Throwable th) {

        this.warn("", th);
        HawkOutput hawkOutput = AppContainer.getInstance().getBean(HawkOutput.class);
        hawkOutput.writeError(th.getMessage());
        th.printStackTrace(hawkOutput.getError());
    }
}
