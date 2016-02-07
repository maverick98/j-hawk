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
