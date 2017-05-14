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
