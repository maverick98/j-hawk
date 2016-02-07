/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hawk.logger;

import org.apache.log4j.Logger;
import org.hawk.module.script.ScriptUsage;
import org.hawk.module.script.enumeration.BuildModeEnum;

/**
 *
 * @author msahu
 */
public class HawkLogger {

    private Logger logger;
    private static BuildModeEnum buildMode = ScriptUsage.getInstance().getBuildMode();

    public static boolean shouldUseLogger() {
        return !(buildMode == null || buildMode == BuildModeEnum.SCRIPTING);
    }

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

        if (shouldUseLogger()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Thread {").append(Thread.currentThread().getName()).append("} ").append(msg);
            logger.info(sb.toString());
        }
    }

   public void severe(String msg){
        if (shouldUseLogger()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Thread {").append(Thread.currentThread().getName()).append("} ").append(msg);
            logger.fatal(sb.toString());
        }

   }
   public void severe(String msg , Throwable th){
        if (shouldUseLogger()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Thread {").append(Thread.currentThread().getName()).append("} ").append(msg);
            logger.fatal(sb.toString(),th);
        }

   }
   public void severe(Throwable th){

       this.severe("", th);

   }
   
   public void warn(String msg){
        if (shouldUseLogger()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Thread {").append(Thread.currentThread().getName()).append("} ").append(msg);
            logger.warn(sb.toString());
        }

   }
   public void warn(String msg , Throwable th){
        if (shouldUseLogger()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Thread {").append(Thread.currentThread().getName()).append("} ").append(msg);
            logger.warn(sb.toString(),th);
        }

   }
   public void warn(Throwable th){

       this.warn("", th);

   }


}
