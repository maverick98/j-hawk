

package org.hawk.html;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.hawk.exception.HawkException;
import org.hawk.util.HawkUtil;

/**
 * A utility to parse select tag in html data
 * @see Select
 * @see Option
 * @author msahu
 */
public class HTMLUtil {

    private static final String all="\\w|\\d|;|\\(|\\|\\-|:|\\)|\"|\\r|\\n|\\.|\\[|\\]|_|=|\\s|\\?|/";
    private static final String selectRegEx="<\\s*select\\s*([\\w+\\s*=\\s*["+all+"]+]+)\\s*>";
    private static final String optionRegEx="<\\s*option\\s*([\\w+\\s*=\\s*["+all+"]+]+)\\s*>(["+all+"]+)<\\s*/\\s*option\\s*>";
    private static final String valueRegEx ="[\\s|\\S]*value\\s*=\\s*(\"?["+all+"]+\"?)[\\s|\\S]*";

    private static final Pattern selectPattern=Pattern.compile(selectRegEx);
    private static final Pattern optionPattern = Pattern.compile(optionRegEx);
    private static final Pattern valuePattern=Pattern.compile(valueRegEx);

    /**
     * This returns <tt>Select</tt> tag from html  for the input selectTagName
     * @param html input html data
     * @param selectTagName input select tag name
     * @return returns <tt>Select</tt> tag from html  for the input selectTagName
     * @see Select
     * @see Option
     */
    public static Select getSelectTag(String html,String selectTagName)
    {

        Matcher m = selectPattern.matcher(html);
        int i=0;
        Select select = new Select();
        Set<Option> options = new HashSet<Option>();
        select.setOptions(options);
        select.setName(selectTagName);
        while(m.find())
        {
            String tmp = m.group();
            if(tmp.matches("[\\s|\\S]*name\\s*=\\s*\"?"+selectTagName+"\"?[\\s|\\S]*"))
            {
                i = html.indexOf(tmp, i);
                i=i+tmp.length();
                int j = html.indexOf("</select>",i);
                String optionStr = html.substring(i,j);

                Matcher m1 = optionPattern.matcher(optionStr);
                while(m1.find())
                {
                    String valuePair = m1.group(1);
                    String caption=m1.group(2);

                    Matcher m2 = valuePattern.matcher(valuePair);
                    if(m2.matches())
                    {
                        String value = m2.group(1);

                        value = value.substring(1,value.length()-1);

                        Option option = new Option(value,caption);
                        options.add(option);

                    }
                }

            }


        }
        return select;
    }

   
   public static String findHiddenParam(String html,String hiddenParam){
       if(html == null || html.isEmpty()){
           return null;
       }
       Map<String, String> replacements = new TreeMap<String,String>();
       replacements.put("1", hiddenParam);
       String pattern = HawkUtil.replace(HIDDEN_PARAM_PATTERN_STR,replacements);
       Pattern HIDDEN_PARAM_PATTERN = Pattern.compile(pattern);
       String hiddenParamValue=null;
       Matcher hiddenParamMatcher = HIDDEN_PARAM_PATTERN.matcher(html);
      
       boolean found = hiddenParamMatcher.find();
       do{
            if(found){
                hiddenParamValue = hiddenParamMatcher.group(1);
                break;
            }
            if(hiddenParamMatcher.hitEnd()){
                break;
            }
            found = hiddenParamMatcher.find();
       }while(!found);

       return hiddenParamValue;
   }

   public static String findSessionToken(String html){
       String sessionToken = System.getProperty("SESSION_TOKEN");
       return findHiddenParam(html, sessionToken);
   }

   public static void main(String args[]) throws HawkException{

        String html = HawkUtil.readFile("/home/manoranjan/bt/session.html");
        //html = "<input type=\"hidden\" name=\"xpi\" value=\"1317652993752\">";
        String hiddenParamValue = findHiddenParam(html, "OFBIZ_FRAMEWORK_REQUEST_SYNCH_TOKEN");
        System.out.println("hidden param value {"+hiddenParamValue+"}");
        
   }

    private static String HIDDEN_PARAM_PATTERN_STR =
            "<\\s*input\\s*type\\s*=\\s*\"hidden\"\\s*name\\s*=\\s*\"{1}\"\\s*value\\s*=\"([a-z|A-Z|0-9]*)\"\\s*/?>\\s*";
   
}
