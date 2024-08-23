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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.commons.logger;

import org.apache.log4j.Logger;

/**
 *
 * @author manosahu
 */
public interface ILogger {

    public Logger getLogger();

    public void setLogger(Logger logger);

    public void info(String msg);

    public void error(String msg);

    public void error(String msg, Throwable th);

    public void error(Throwable th);

    public void warn(String msg);

    public void warn(String msg, Throwable th);

    public void warn(Throwable th);
}
