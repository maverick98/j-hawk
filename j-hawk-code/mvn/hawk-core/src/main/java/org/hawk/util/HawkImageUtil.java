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
package org.hawk.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import javax.imageio.ImageIO;
import org.hawk.logger.HawkLogger;

/**
 *
 * @author manoranjan
 */
public class HawkImageUtil {
    private static final HawkLogger logger = HawkLogger.getLogger(HawkImageUtil.class.getName());
    public static boolean copyLogo() {
        boolean result = false;    
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("images/logo.png");
        BufferedImage bi;
        try {
            bi = ImageIO.read(is);
            String logoImagePath = System.getProperty("LOGPATH") + "/images";
            File logoImageDir = new File(logoImagePath);
            result = logoImageDir.mkdirs();
            File logoImageFile = new File(logoImagePath+"/"+"logo.png");
            ImageIO.write(bi, "png", logoImageFile);

        } catch (Exception ex) {
            
           logger.error( "Could not copy hawk logo", ex);
        }
        return result;
    }
}
