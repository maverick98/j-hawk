
package org.hawk.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import org.hawk.logger.HawkLogger;

/**
 * An utility to save and load properties.
 * @version 1.0 8 Apr, 2010
 * @author msahu
 */

public class PropertiesUtil {
    private static  final HawkLogger logger = HawkLogger.getLogger(PropertiesUtil.class.getName());
    /**
     * This saves properties into the file.
     * @param props
     * @param file
     * @return <tt>true</tt> on success <tt>false</tt> otherwise.
     */
    public static boolean saveProperties(Properties props, String file){

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            props.store(fos, "");
        } catch (IOException ex) {
            logger.severe( null, ex);
            return false;
        }finally{
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException ex) {
                    logger.severe( null, ex);
                }
            }
        }
        return true;
    }

    /**
     * This loads properties from  the file.
     * @param props
     * @param file
     * @return <tt>Properties</tt>
     */
    public static Properties loadProperties(String file){
        Properties props = new Properties();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            props.load(fis);
        } catch (IOException ex) {
            logger.severe( null, ex);
        }finally{
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException ex) {
                   logger.severe( null, ex);
                }
            }
        }
        return props;
    }
}
