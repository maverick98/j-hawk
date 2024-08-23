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
package org.commons.resource;

import java.io.Closeable;
import org.apache.log4j.Logger;


/**
 *
 * @author Manoranjan Sahu
 */
public class ResourceUtil {

    private static final Logger logger = Logger.getLogger(ResourceUtil.class.getName());

    public static boolean close(Closeable... closeables) {
        boolean status = false;
        if (closeables != null) {
            for (Closeable closeable : closeables) {
                try {
                    if (closeable != null) {
                        closeable.close();
                    }

                } catch (Exception ex) {
                    logger.error(ex);
                    throw new Error("unable to close resource " + closeable, ex);
                }
            }
            status = true;
        }
        return status;

    }
}
