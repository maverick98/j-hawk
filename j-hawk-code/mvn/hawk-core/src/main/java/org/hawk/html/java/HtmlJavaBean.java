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
