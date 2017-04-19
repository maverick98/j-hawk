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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.commons.process;

import org.commons.logger.ILogger;
import org.commons.logger.LoggerFactory;
import org.commons.resource.ResourceUtil;

/**
 *
 * @author manosahu
 */
public class ProcessUtil {

    private static final ILogger logger = LoggerFactory.getLogger(ResourceUtil.class.getName());

    public static boolean executeProcess(String command) {

        if (command == null || command.isEmpty()) {
            throw new IllegalArgumentException("command is null or empty");
        }
        Process proc = null;
        boolean status = true;
        try {
            System.out.println("Executing ... {" + command + "}");
            proc = Runtime.getRuntime().exec(command);
            proc.waitFor();
        } catch (Throwable th) {
            logger.error("Could not execute {" + command + "}", th);
            status = false;
        } finally {
            if (proc != null) {
                ResourceUtil.close(proc.getOutputStream());
                ResourceUtil.close(proc.getInputStream());
                ResourceUtil.close(proc.getErrorStream());
                proc.destroy();
            }
        }
        return status;

    }
}
