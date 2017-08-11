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
package org.commons.string;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Stack;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author msahu
 */
public class StringUtil {

    public static boolean isNullOrEmpty(String input) {

        return input == null || input.trim().isEmpty();
    }

    public static boolean isStringDataType(String exp) {
        boolean status = false;
        if (exp == null || exp.isEmpty()) {
            status = false;
        } else {
            String tmp = exp.trim();
            if (tmp.startsWith("\"") && tmp.endsWith("\"")) {
                status = true;
            }
        }
        return status;
    }

    public static String stringifyArgs(String args[]) {
        StringBuilder sb = new StringBuilder();
        if (args != null && args.length > 0) {
            Arrays.stream(args,0,args.length-1)
                  .forEach
                    (
                            arg
                            ->
                            {
                                sb.append(arg);
                                sb.append(" ");
                            }
                    );
            sb.append(args[args.length-1]);
        }
        return sb.toString();
    }

    public static String parseBracketData(String input) {
        return parseDelimeterData(input, '(', ')');
    }

    public static String parseDelimeterData(String input, int pos, char startingDelimChar, char closingDelimChar) {
        if(pos < 0){
            return null;
        }
        String result = null;
        if (input != null && !input.isEmpty()) {
            String sd = String.valueOf(startingDelimChar);
            String cd = String.valueOf(closingDelimChar);
            Stack<String> stack = new Stack<>();
            boolean atLeastOneStartingBracketFound = false;
            boolean atLeastOneClosingBracketFound = false;
            int i = 0;
            int start = 0;
            int end = input.length();
            for (i = pos; i < input.length(); i++) {
                String eleStr = input.charAt(i) + "";
                if (!atLeastOneStartingBracketFound && eleStr.equals(sd)) {
                    stack.push(sd);
                    if (!atLeastOneStartingBracketFound) {
                        start = i;
                    }
                    atLeastOneStartingBracketFound = true;
                } else if (eleStr.equals(cd)) {
                    atLeastOneClosingBracketFound = true;
                    if(!stack.isEmpty()){
                    stack.pop();
                    }else{
                        return null;
                    }
                } else if (eleStr.equals(sd)) {
                    stack.push(sd);
                }
                if (atLeastOneStartingBracketFound && stack.isEmpty()) {
                    end = i;
                    break;
                }
            }
            if (atLeastOneStartingBracketFound && input.substring(pos).startsWith(sd) && atLeastOneClosingBracketFound) {
                result = input.substring(start + 1, end);
            } else {
                result = null;
            }
        }
        return result;
    }

    public static String parseDelimeterData(String input, char startingDelimChar, char closingDelimChar) {
        return parseDelimeterData(input, 0, startingDelimChar, closingDelimChar);
    }
    private static final Pattern REPLACEMENT_PATTERN = Pattern.compile("(\\{(\\d+)\\})");

    public static String replace(String replacementData, Map<String, String> replacements) {
        String result = null;
        if (isNullOrEmpty(replacementData)) {
            throw new Error("Empty replacementData");
        }
        if(replacements == null || replacements.isEmpty()){
            return replacementData;
        }
        Matcher m = REPLACEMENT_PATTERN.matcher(replacementData);
        while (m.find()) {
            String replacement = replacements.get(m.group(2));
            if (replacement == null) {
                throw new Error("set the parameter properly");
            }
            result = m.replaceFirst(replacement);
            m = REPLACEMENT_PATTERN.matcher(result);
        }
        return result;
    }

    public static String toggle(String str) {
        if(isNullOrEmpty(str)){
            return str;
        }
        String part = str.substring(1);
        String tmp = String.valueOf(str.charAt(0));
        if (tmp.equals(tmp.toLowerCase(Locale.ENGLISH))) {
            tmp = tmp.toUpperCase();
        } else {
            tmp = tmp.toLowerCase();
        }
        return tmp + part;
    }

}
