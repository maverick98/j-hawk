/**
 *  
 * left.
 *
 *    
 *  
 *
 *
 */
package org.commons.file;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import static org.commons.file.FileConstant.ENCODING;
import static org.commons.io.IOUtil.dumpInputStreamToMap;
import org.commons.logger.ILogger;
import org.commons.logger.LoggerFactory;
import org.commons.resource.ResourceUtil;
import org.commons.string.StringUtil;

/**
 *
 * @author Manoranjan Sahu
 */
public class FileUtil {

    private static final ILogger logger = LoggerFactory.getLogger(org.commons.resource.ResourceUtil.class.getName());

    /**
     * This reads the hawk script file and returns them as a map with line no
     * and script as key value pair respectively.
     *
     * @param scriptFile hawk script file
     * @return a map containing line no and the script
     */
    public static Map<Integer, String> dumpFileToMap(File scriptFile) {
        if (scriptFile == null) {
            throw new IllegalArgumentException("null file");
        }
        try {
            return dumpInputStreamToMap(new FileInputStream(scriptFile));
        } catch (FileNotFoundException ex) {
            logger.error("error while reading from file  {" + scriptFile + "}" + ex.getMessage());
        }
        return null;
    }

    /**
     * This reads the hawk script file and returns them as a map with line no
     * and script as key value pair respectively.
     *
     * @param scriptFile hawk script file
     * @return a map containing line no and the script
     */
    public static Map<Integer, String> dumpFileToMap(String scriptFile) {
        if (StringUtil.isNullOrEmpty(scriptFile)) {
            throw new IllegalArgumentException("null file");
        }
        return dumpFileToMap(new File(scriptFile));
    }

    public static Map<Integer, String> dumpStringToMap(String scriptFileData) {
        if (StringUtil.isNullOrEmpty(scriptFileData)) {
            throw new IllegalArgumentException("null filedata");
        }
        return dumpInputStreamToMap(new ByteArrayInputStream(scriptFileData.getBytes(Charset.forName(ENCODING))));
    }

    public static Reader readFileAsStream(String fileName) {

        if (StringUtil.isNullOrEmpty(fileName)) {
            throw new IllegalArgumentException("Invalid fileName");
        }
        return readFileAsStream(new File(fileName));

    }

    public static Reader readFileAsStream(File fileName) {

        if (fileName == null) {
            throw new IllegalArgumentException("Invalid fileName");
        }
        InputStreamReader isr;
        FileInputStream fis;
        BufferedReader result = null;
        try {
            fis = new FileInputStream(fileName);
            isr = new InputStreamReader(fis, Charset.forName(ENCODING));

            result = new BufferedReader(isr);
        } catch (FileNotFoundException ex) {
            logger.error("file not found", ex);
        }
        return result;

    }

    public static String readFile(String fileName, boolean readFromClazzpath) {
        if (StringUtil.isNullOrEmpty(fileName)) {
            throw new IllegalArgumentException("Invalid fileName");
        }
        String result;
        if (readFromClazzpath) {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);

            if (is == null) {
                return null;
            }
            result = readFile(new InputStreamReader(is, Charset.forName(ENCODING)));
        } else {
            result = readFile(fileName);
        }
        return result;
    }

    public static String readFile(File fileName) {

        String result = null;
        if (fileName == null) {
            throw new IllegalArgumentException("Invalid fileName");
        }
        InputStreamReader isr = null;
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(fileName);
            isr = new InputStreamReader(fis, Charset.forName(ENCODING));
            result = readFile(isr);
        } catch (FileNotFoundException ex) {
            logger.error("Could not find file {" + fileName + "}");
        } finally {
            ResourceUtil.close(isr, fis);
        }
        return result;
    }

    public static String readFile(String fileName) {

        return readFile(new File(fileName));
    }

    private static String readFile(InputStreamReader inputStreamReader) {
        if (inputStreamReader == null) {
            throw new IllegalArgumentException("Invalid inputstream reader...");
        }
        StringBuilder sb = new StringBuilder();
        BufferedReader bfr = null;
        try {
            bfr = new BufferedReader(inputStreamReader);

            String line;
            do {
                line = bfr.readLine();
                if (line != null) {
                    sb.append(line);
                    sb.append("\n");
                }
            } while (line != null);
        } catch (Exception ex) {
            logger.error(ex.getMessage());

        } finally {
            ResourceUtil.close(bfr);
        }
        return sb.toString();
    }

    public static boolean diff(File f1, File f2, File diffFile) {
        boolean anyDiff = false;
        BufferedWriter diffBfr = null;
        FileInputStream file1 = null;
        FileInputStream file2 = null;
        InputStreamReader is1 = null;
        InputStreamReader is2 = null;
        BufferedReader bfr1 = null;
        BufferedReader bfr2 = null;
        OutputStreamWriter diffOSW = null;
        FileOutputStream diffFOS = null;

        try {
            file1 = new FileInputStream(f1);
            file2 = new FileInputStream(f2);
            is1 = new InputStreamReader(file1, Charset.forName(ENCODING));
            is2 = new InputStreamReader(file2, Charset.forName(ENCODING));
            bfr1 = new BufferedReader(is1);

            bfr2 = new BufferedReader(is2);
            String s1, s2;

            while ((s1 = bfr1.readLine()) != null && (s2 = bfr2.readLine()) != null) {
                if (!s1.equals(s2)) {
                    System.out.println("---" + s1);
                    System.out.println("+++" + s2);

                    anyDiff = true;
                    if (diffBfr == null) {
                        diffFOS = new FileOutputStream(diffFile);
                        diffOSW = new OutputStreamWriter(diffFOS, Charset.forName(ENCODING));
                        diffBfr = new BufferedWriter(diffOSW);
                    }
                    diffBfr.write("---" + s1);
                    diffBfr.write("<br>");
                    diffBfr.write("+++" + s2);
                }
            }
        } catch (FileNotFoundException th) {
            logger.error(th);
        } catch (IOException th) {
            logger.error(th);
        } finally {
            ResourceUtil.close(bfr1, is1, file1);
            ResourceUtil.close(bfr2, is2, file2);
            ResourceUtil.close(diffBfr, diffOSW, diffFOS);

        }
        return anyDiff;
    }

    public static boolean diff(String file1, String file2, String diffFileStr) {

        File f1 = new File(file1);
        File f2 = new File(file2);
        File diffFile = new File(diffFileStr);
        return diff(f1, f2, diffFile);
    }

    public static boolean writeFile(String filePath, String data) {

        return writeFile(new File(filePath), data);
    }

    public static boolean writeFile(File filePath, String data) {

        return writeFile(filePath, data, false);
    }

    public static boolean writeFile(File file, String data, boolean append) {
        boolean status;
        BufferedWriter bfr = null;
        OutputStreamWriter osw = null;
        FileOutputStream fos = null;

        try {

            fos = new FileOutputStream(file, append);
            osw = new OutputStreamWriter(fos, Charset.forName(ENCODING));
            bfr = new BufferedWriter(osw);
            bfr.write(data);
            status = true;

        } catch (IOException ex) {

            logger.error("error while writing ", ex);
            status = false;
        } finally {
            status = ResourceUtil.close(bfr, osw, fos);
        }
        return status;
    }

    public static boolean writeFile(String filePath, String data, boolean append) {

        return writeFile(new File(filePath), data, append);
    }

    public static PrintWriter createPrintWriter(File file) {
        PrintWriter printWriter;
        FileOutputStream fos;
        OutputStreamWriter osw;
        try {
            fos = new FileOutputStream(file);
            osw = new OutputStreamWriter(fos, Charset.forName(ENCODING));
            printWriter = new PrintWriter(osw);
        } catch (FileNotFoundException ex) {
            logger.error("error while createing print writer to file " + file, ex);
            printWriter = null;
        }
        return printWriter;
    }

    public static OutputStream createStream(File file) {
        FileOutputStream fos;

        try {
            fos = new FileOutputStream(file);

        } catch (FileNotFoundException ex) {
            logger.error(ex);
            return null;

        }
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        return bos;
    }

    public static boolean remove(String fileStr) {
        File file = new File(fileStr);
        try {
            FileUtils.deleteDirectory(file);
        } catch (IOException ex) {
            logger.error("unable to delete {" + file + "}");
            return false;
        }
        return !file.exists();
    }

    public static boolean copy(String srcFileStr, String destDirStr) {
        try {
            FileUtils.copyFileToDirectory(new File(srcFileStr), new File(destDirStr));
        } catch (IOException ex) {
            ex.printStackTrace();
            logger.error(ex);
            return false;
        }

        return true;
    }

    public static boolean exists(String inputFile) {

        boolean exists;
        if (StringUtil.isNullOrEmpty(inputFile)) {
            return false;
        }
        File tmpFile = new File(inputFile);
        exists = tmpFile.exists();
        return exists;
    }

}
