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
package org.commons.io;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import javax.imageio.ImageIO;
import static org.commons.file.FileConstant.ENCODING;
import org.commons.logger.ILogger;
import org.commons.logger.LoggerFactory;
import org.commons.resource.ResourceUtil;

/**
 *
 * @author manosahu
 */
public class IOUtil {

    private static final ILogger logger = LoggerFactory.getLogger(IOUtil.class.getName());

    public static Reader createReader(String data) {

        return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(data.getBytes(Charset.forName(ENCODING))), Charset.forName(ENCODING)));
    }

    public static String readLineFromConsole() throws IOException {

        String data;

        Scanner scanner = new Scanner(System.in, ENCODING);
        data = scanner.nextLine();
        return data;
    }

    /**
     * This reads the hawk script file and returns them as a map with line no
     * and script as key value pair respectively.
     *
     * @param scriptFile hawk script file
     * @return a map containing line no and the script
     */
    public static Map<Integer, String> dumpInputStreamToMap(InputStream in) {
        Map<Integer, String> result = new TreeMap<Integer, String>();

        BufferedReader bfr = null;

        try {

            bfr = new BufferedReader(new InputStreamReader(in, Charset.forName(ENCODING)));
            String line;
            int i = 0;
            do {
                line = bfr.readLine();
                result.put(i, line);
                i++;
            } while (line != null);

        } catch (Exception ex) {
            logger.error("error while reading " + ex.getMessage());
        } finally {
            ResourceUtil.close(bfr);
        }

        return result;
    }

    public static String dumpInputStreamToString(InputStream in) {
        StringBuilder result = new StringBuilder();

        BufferedReader bfr = null;

        try {

            bfr = new BufferedReader(new InputStreamReader(in, Charset.forName(ENCODING)));
            String line;
            result.append("<html>");
            do {
                line = bfr.readLine();
                result.append(line);
                result.append("<br>");

            } while (line != null);
            result.append("</html>");
        } catch (Exception ex) {
            logger.error("error while reading " + ex.getMessage());
        } finally {
            ResourceUtil.close(bfr);
        }

        return result.toString();
    }

    public static boolean writeToPrintWriter(PrintWriter output, String data) {

        boolean status;
        if (output != null) {
            try {
                output.write(data);
                status = true;
            } catch (Exception ex) {

                logger.error("error while writing ", ex);
                status = false;
            }
        } else {
            status = false;
        }
        return status;
    }

    public static boolean writeToStream(OutputStream output, String data) {
        boolean status;
        if (output != null) {
            try {
                output.write(data.getBytes(Charset.forName(ENCODING)));
                status = true;
            } catch (Exception ex) {

                logger.error("error while writing ", ex);
                status = false;
            }
        } else {
            status = false;
        }
        return status;
    }

    public static void sleep(long thinkTime) {
        if (thinkTime > 0) {
            try {

                Thread.sleep(thinkTime * 1000);
            } catch (InterruptedException ex) {
                logger.error(ex.getMessage());
            }
        }
    }

    public static boolean copyImage(String from, String formatName, File output) {

        boolean result = false;
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(from);
        BufferedImage bi;
        try {
            bi = ImageIO.read(is);
            result = ImageIO.write(bi, formatName, output);

        } catch (Exception ex) {

            logger.error("Could not copy "+from, ex);
        }
        return result;
    }
}
