/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
    public static void copyLogo() {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("images/logo.png");
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(is);
            String logoImagePath = System.getProperty("LOGPATH") + "/images";
            File logoImageDir = new File(logoImagePath);
            logoImageDir.mkdirs();
            File logoImageFile = new File(logoImagePath+"/"+"logo.png");
            ImageIO.write(bi, "png", logoImageFile);

        } catch (Throwable th) {
            th.printStackTrace();
           logger.severe( "Could not copy hawk logo", th);
        }
    }
}
