package org.hawk.util;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.IOException;
import org.hawk.exception.HawkException;
import org.hawk.ds.Stack;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.hawk.logger.HawkLogger;
import static org.hawk.constant.HawkConstant.*;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.hawk.exception.HawkError;

/**
 *
 * @VERSION 1.0 30 Mar, 2010
 * @author msahu
 */
public class HawkUtil {

    private static final HawkLogger logger = HawkLogger.getLogger(HawkUtil.class.getName());

    public static String readLineFromConsole() throws HawkException {

        String data = null;

        Scanner scanner = new Scanner(System.in);
        data = scanner.nextLine();
        return data;
    }

    public static void sleep(long thinkTime) {
        if (thinkTime > 0) {
            try {

                Thread.sleep(thinkTime * 1000);
            } catch (InterruptedException ex) {
                logger.severe( ex.getMessage());
            }
        }
    }

    public static boolean getBooleanData(double element) throws HawkException {
        if (!(element == 1.0 || element == 0.0)) {
            throw new HawkException("Can not convert element{" + element + "} to boolean.");
        }

        boolean result = false;

        result = (element == 1.0) ? true : false;

        return result;
    }

    public static boolean isMainThread() {
        String currentThreadName = Thread.currentThread().getName();

        return currentThreadName.equals(MAINTHREADNAME);
    }

    private static void close(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException e) {
                logger.severe( e.getMessage());
            }
        }
    }

    public static String convertHawkStringToJavaString(String hawkString) {
        if (hawkString == null || hawkString.isEmpty()) {
            return hawkString;
        }

        hawkString = hawkString.trim();
        if (hawkString.startsWith("\"") && hawkString.endsWith("\"")) {
            return hawkString.substring(1, hawkString.length() - 1);
        } else {
            return hawkString;
        }


    }

    public static boolean executeProcess(String command) {

        if (command == null || command.isEmpty()) {
            return false;
        }
        Process proc = null;
        boolean status = true;
        try {
            System.out.println("Executing ... {" + command + "}");
            proc = Runtime.getRuntime().exec(command);
            proc.waitFor();
        } catch (Throwable th) {
            logger.severe( "Could not execute {" + command + "}", th);
            status = false;
        } finally {
            if (proc != null) {
                close(proc.getOutputStream());
                close(proc.getInputStream());
                close(proc.getErrorStream());
                proc.destroy();
            }
        }
        return status;

    }

    public static boolean isStringDataType(String exp) {
        if (exp == null || exp.isEmpty()) {
            return false;
        }
        boolean status = false;
        String tmp = exp.trim();
        if (tmp.startsWith("\"") && tmp.endsWith("\"")) {
            status = true;
        }
        return status;
    }

    public static String readFile(String fileName, boolean readFromClazzpath) throws HawkException {
        if (fileName == null && fileName.isEmpty()) {
            throw new HawkException("Invalid fileName");
        }
        String result = null;
        if (readFromClazzpath) {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);

            if (is == null) {
                throw new HawkException("Could not load the resource {" + fileName + "}");
            }
            result = readFile(new InputStreamReader(is));
        } else {
            result = readFile(fileName);
        }
        return result;
    }

    public static String readFile(String fileName) throws HawkException {

        String result = null;
        if (fileName == null && fileName.isEmpty()) {
            throw new HawkException("Invalid fileName");
        }
        try {
            result = readFile(new InputStreamReader(new FileInputStream(fileName)));
        } catch (FileNotFoundException ex) {
            logger.severe( "Could not find file {" + fileName + "}");
        }
        return result;
    }

    private static String readFile(InputStreamReader inputStreamReader) throws HawkException {
        if (inputStreamReader == null) {
            throw new HawkException("Invalid inputstream reader...");
        }
        StringBuilder sb = new StringBuilder();
        BufferedReader bfr = null;
        try {
            bfr = new BufferedReader(inputStreamReader);

            String line = null;
            do {
                line = bfr.readLine();
                if (line != null) {
                    sb.append(line);
                    sb.append("\n");
                }
            } while (line != null);
        } catch (Exception ex) {
            logger.severe( ex.getMessage());
            return null;
        } finally {
            try {
                bfr.close();
            } catch (Exception ex1) {
                logger.severe( ex1.getMessage());
                return null;
            }
        }
        return sb.toString();
    }

    /**
     * This reads the hawk script file and returns them as a map
     * with line no and script as key value pair respectively.
     * @param scriptFile hawk script file
     * @return a map containing line no and the script
     */
    public static Map<Integer, String> dumpFileToMap(String scriptFile) {
        Map<Integer, String> data = new TreeMap<Integer, String>();

        BufferedReader bfr = null;

        try {

            bfr = new BufferedReader(new InputStreamReader(new FileInputStream(new File(scriptFile))));
            String line = null;
            int i = 0;
            do {
                line = bfr.readLine();
                data.put(i, line);
                i++;
            } while (line != null);

        } catch (Exception ex) {
            System.out.println("error while reading {" + scriptFile + "}" + ex.getMessage());
            logger.severe("error while reading {" + scriptFile + "}" + ex.getMessage());
            return null;
        } finally {
            if (bfr != null) {
                try {
                    bfr.close();
                } catch (IOException ex) {
                    System.out.println("error while closing {" + scriptFile + "}" + ex.getMessage());
                    logger.severe("error while closing {" + scriptFile + "}" + ex.getMessage());
                    return null;
                }
            }
        }

        return data;
    }

    public static boolean writeFile(String filePath, String data) throws HawkException {


        BufferedWriter bfr = null;
        try {
            bfr = new BufferedWriter(new FileWriter(new File(filePath)));
            bfr.write(data);


        } catch (Exception ex) {

            logger.severe( "error while writing ", ex);
            return false;
        } finally {
            try {

                bfr.close();

            } catch (Exception ex1) {

                logger.severe( "error while closing buffer " + ex1);
                return false;
            }
        }
        return true;
    }

    public static String parseBracketData(String input) throws HawkException {

        return parseDelimeterData(input, '(', ')');
    }

    public static String parseDelimeterData(String input, char startingDelimChar, char closingDelimChar) throws HawkException {

        if (input == null || input.isEmpty()) {
            return "";
        }
        String sd = "" + startingDelimChar;
        String cd = "" + closingDelimChar;
        Stack<String> stack = new Stack<String>();
        boolean atLeastOneStartingBracketFound = false;
        int i = 0;
        int start = 0;
        int end = input.length();
        for (; i < input.length(); i++) {

            String eleStr = input.charAt(i) + "";
            if (!atLeastOneStartingBracketFound && eleStr.equals(sd)) {
                stack.push(sd);
                if (!atLeastOneStartingBracketFound) {
                    start = i;
                }
                atLeastOneStartingBracketFound = true;

            } else if (eleStr.equals(cd)) {
                stack.pop();
            } else if (eleStr.equals(sd)) {
                stack.push(sd);
            }
            if (atLeastOneStartingBracketFound && stack.isEmpty()) {
                end = i;
                break;
            }
        }
        String data = null;
        if (atLeastOneStartingBracketFound && input.startsWith(sd)) {
            data = input.substring(start + 1, end);
        } else {
            data = null;
        }
        return data;
    }

    public static String replace(String replacementData,Map<String, String> replacements) {
        String result = null;
       
        if (replacementData == null || replacementData.isEmpty()) {
            throw new HawkError("set the parameter properly");
        }
         
        Matcher m = REPLACEMENT_PATTERN.matcher(replacementData);
        while (m.find()) {

            String replacement = replacements.get(m.group(2));

            if (replacement == null) {
                throw new HawkError("set the parameter properly");
            }
            result = m.replaceFirst(replacement);

            m = REPLACEMENT_PATTERN.matcher(result);

        }
        return result;
    }
    private static final Pattern REPLACEMENT_PATTERN = Pattern.compile("(\\{(\\d+)\\})");
}
