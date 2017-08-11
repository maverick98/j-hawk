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


package org.hawk.pattern;



import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.hawk.lang.singleline.pattern.LinePattern;

/**
 * This is generic pattern matcher.
 * It returns a map containing key value pair as
 * integer and the matcher's grouping value at that group no respectively.
 * @version 1.0 8 Apr, 2010
 * @author msahu
 */
public class PatternMatcher {


    /**
     * This returns a map containing key value pair as
     * integer and the matcher's grouping value at that group no respectively.
     * @param pattern input RegEx Pattern.
     * @param input   input string to be parsed.
     * @return returns a map containing key value pair as
     * integer and the matcher's grouping value at that group no respectively.
     */
    public static Map<Integer,String> match(Pattern pattern, String input){
        if(pattern == null || input == null || input.isEmpty()){
            return null;
        }
        Map<Integer,String> map = new HashMap<>();

        Matcher matcher = pattern.matcher(input);
        if(matcher.matches()){
            int count = matcher.groupCount();
            for(int i=1;i<=count;i++){
                map.put(i,(String)matcher.group(i).trim());
            }
            map.put(0, input);
        }else{
            return null;
        }
        return map;
    }
     public static Map<Integer,String> match(Set<LinePattern> linePatterns, String input){
        if(linePatterns == null || input == null || input.isEmpty()){
            return null;
        }
         Map<Integer,String> map = null;
         for(LinePattern linePattern : linePatterns){
             map = match(linePattern.getPattern(),input);
             if(map != null){
                 break;
             }
         }
         return map;
     }
}




