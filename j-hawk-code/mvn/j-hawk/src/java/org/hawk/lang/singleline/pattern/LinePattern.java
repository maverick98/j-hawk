/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hawk.lang.singleline.pattern;

import java.util.regex.Pattern;

/**
 *
 * @author manosahu
 */
public class LinePattern implements Comparable<LinePattern> {

    private String regEx;

    private Pattern pattern;

    private Integer sequence;

    public Pattern getPattern() {
        if (this.pattern == null) {
            this.pattern = Pattern.compile(this.getRegEx());
        }
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public String getRegEx() {
        return regEx;
    }

    public void setRegEx(String regEx) {
        String processedRegEx = this.removeFormatting(regEx);
        this.regEx = processedRegEx;
    }
     private String removeFormatting(String regEx) {
         String result = regEx.trim();
         result = result.replaceAll("\n", "");
         result = result.replaceAll("\\s*", "");
         
         return result;
    }


    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    @Override
    public int compareTo(LinePattern o) {

        int rtn;
        if (o == null) {
            rtn = 0;
        } else {
            rtn = this.getSequence().compareTo(o.getSequence());
        }
        return rtn;

    }

    @Override
    public String toString() {
        return "LinePattern{" + "regEx=" + regEx + ", sequence=" + sequence + '}';
    }

   
    
}
