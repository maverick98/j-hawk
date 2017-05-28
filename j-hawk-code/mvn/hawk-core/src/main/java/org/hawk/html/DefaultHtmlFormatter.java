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

package org.hawk.html;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import org.commons.file.FileUtil;
import org.commons.string.StringUtil;

/**
 *
 * @author Manoranjan Sahu
 */
   
   
public class DefaultHtmlFormatter implements IHtmlFormatter{
    public static void main(String args[]) throws HTMLFormatException{
        DefaultHtmlFormatter htmlFormatter = new DefaultHtmlFormatter();
        String input = "    India";
        System.out.println(htmlFormatter.encodeSpace(input));
        String fileName = "e:\\git\\j-hawk-code\\scripts\\fibonacci.hawk";
        System.out.println(htmlFormatter.formatFile(fileName,"fibonacci.html"));
    }
    private String encodeSpace(String input){
         input = input.replace(" ", "&nbsp;");
         return input;
        
    }
    @Override
    public String formatFile(String fileName) throws HTMLFormatException {
        if(StringUtil.isNullOrEmpty(fileName)){
            throw new HTMLFormatException("illegal args");
        }
        StringBuilder result= new StringBuilder();
        Map<Integer,String> data = FileUtil.dumpFileToMap(fileName);
        if(data != null && !data.isEmpty()){
            for(Entry<Integer,String> entry : data.entrySet()){
                String line = entry.getValue();
                
                if(!StringUtil.isNullOrEmpty(line)){
                result.append(this.encodeSpace(line));
                }
                result.append("<br>");
                
            }
        }
        
        return result.toString();
        
    }

    @Override
    public String formatFile(String fileName, String outputFile) throws HTMLFormatException {
        String outputData = this.formatFile(fileName);
       
            FileUtil.writeFile(outputFile, outputData);
        
        return outputData;
    }

    @Override
    public String createHyperLink(Collection<String> coll) throws HTMLFormatException {
        
        StringBuilder sb = new StringBuilder();
        for(String str : coll){
            String link = "./"+str+".hawk.html";
            sb.append("<a href=\""+link+"\">"+this.encodeSpace(str)+"</a>");
            sb.append("<br>");
        }
        
        return sb.toString();
    }

}
