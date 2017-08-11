/*
 * This file is part of j-hawk
 * CopyLeft (C) BigBang<->BigCrunch Manoranjan Sahu, All Rights are left.
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
