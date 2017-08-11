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
package org.common.properties;

import static org.common.java.JavaUtil.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 * An utility to save and load properties.
 *
 * @version 1.0 8 Apr, 2010
 * @author msahu
 */
public class PropertiesUtil {

    private static final Logger logger = Logger.getLogger(PropertiesUtil.class.getName());

    /**
     * This saves properties into the file.
     *
     * @param props
     * @param file
     * @return <tt>true</tt> on success <tt>false</tt> otherwise.
     */
    public static boolean saveProperties(Properties props, String file) {
        if (isNull(props, file)) {
            return false;
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            props.store(fos, "");
        } catch (IOException ex) {
            logger.error(null, ex);
            return false;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException ex) {
                    logger.error(null, ex);
                }
            }
        }
        return true;
    }

    public static Properties loadConfigsFromClazzpath(String fileName) {

        Properties result = new Properties();
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        try {
            result.load(is);
        } catch (IOException ex) {
            logger.error(null, ex);
        }
        return result;

    }

    /**
     * This loads properties from the file.
     *
     * @param props
     * @param file
     * @return <tt>Properties</tt>
     */
    public static Properties loadProperties(String file) {

        return loadProperties(new File(file));
    }

    /**
     * This loads properties from the file.
     *
     * @param props
     * @param file
     * @return <tt>Properties</tt>
     */
    public static Properties loadProperties(File file) {
        logger.info("loading the file {" + file + "}");
        Properties props = new Properties();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            props.load(fis);
        } catch (IOException ex) {
            logger.error(null, ex);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException ex) {
                    logger.error(null, ex);
                }
            }
        }
        return props;
    }
}
