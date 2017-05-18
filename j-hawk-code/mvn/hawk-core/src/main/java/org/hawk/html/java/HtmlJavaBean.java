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

package org.hawk.html.java;

/**
 *
 * @author user
 */
public class HtmlJavaBean {
    private String template;
    private String replacementData;
    private String resultHTMLFile;

    public String getReplacementData() {
        return replacementData;
    }

    public void setReplacementData(String replacementData) {
        this.replacementData = replacementData;
    }
    
    

    public HtmlJavaBean(String template) {
        this.template = template;
    }
    
    public HtmlJavaBean(){
        
    }
    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getResultHTMLFile() {
        return this.resultHTMLFile;
    }
    public void setResultHTMLFile(String resultHTMLFile) {
         this.resultHTMLFile  =resultHTMLFile;
    }
    
}
