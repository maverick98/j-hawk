


package org.hawk.pattern;



import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        Map<Integer,String> map = new HashMap<Integer,String>();

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
}




