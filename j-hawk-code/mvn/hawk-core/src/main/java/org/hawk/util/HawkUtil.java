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

import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import org.commons.file.FileUtil;
import static org.hawk.constant.HawkConstant.J_HAWK_IDE_TITLE;
import static org.hawk.constant.HawkConstant.MAINTHREADNAME;
import static org.hawk.constant.HawkConstant.VERSION;
import org.hawk.logger.HawkLogger;
import org.hawk.pattern.PatternMatcher;

/**
 *
 * @VERSION 1.0 30 Mar, 2010
 * @author msahu
 */
public class HawkUtil {

    private static final HawkLogger logger = HawkLogger.getLogger(HawkUtil.class.getName());

  
   

   

   

    public static boolean isMainThread() {
        String currentThreadName = Thread.currentThread().getName();

        return currentThreadName.equals(MAINTHREADNAME);
    }

    public static String convertHawkStringToJavaString(String hawkString) {
        String result;
        if (hawkString == null || hawkString.isEmpty()) {
            result = hawkString;
        } else {

            hawkString = hawkString.trim();
            if (hawkString.startsWith("\"") && hawkString.endsWith("\"")) {
                result = hawkString.substring(1, hawkString.length() - 1);
            } else {
                result = hawkString;
            }
        }

        return result;
    }

  
   
    public static String getHawkIDETitle() {
        StringBuilder sb = new StringBuilder();
        sb.append(J_HAWK_IDE_TITLE);
        sb.append(" ");
        sb.append(VERSION);
        return sb.toString();
    }

     public static String replace(String input, String startLineRegEx, String endLineRegEx, String key, String value) {
        StringBuilder sb = new StringBuilder();
        Map<Integer, String> map = FileUtil.dumpStringToMap(input);
        Pattern startLinePattern = Pattern.compile(startLineRegEx);
        Pattern endLinePattern = Pattern.compile(endLineRegEx);
        boolean shouldStart = false;
        boolean shouldStop = false;
        for (Entry<Integer, String> entry : map.entrySet()) {
            String line = entry.getValue();
            Map<Integer, String> startLineMatchedMap = PatternMatcher.match(startLinePattern, line);
            if (startLineMatchedMap != null && !startLineMatchedMap.isEmpty()) {
                shouldStart = true;
            }
            Map<Integer, String> endLineMatchedMap = PatternMatcher.match(endLinePattern, line);
            if (endLineMatchedMap != null && !endLineMatchedMap.isEmpty()) {
                shouldStop = true;
            }
            if (shouldStart && !shouldStop) {
                line = line.replaceAll(key, value);
            }
            if (line != null) {
                sb.append(line);
                sb.append("\n");
            }

        }

        return sb.toString();

    }
 
    private static final String HAWK_JAVA_PROP = "hawk.java.prop";
    private static final Pattern REPLACEMENT_PATTERN = Pattern.compile("(\\{(\\d+)\\})");
}
