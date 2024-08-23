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

import java.io.File;
import java.net.URL;
import java.util.List;


/**
 *
 * @author user
 */
public interface IHtmlJavaService {
    public String createHTMLData(List<HtmlJavaBean> tstResults)throws Exception;
    public boolean createHTMLFile(List<HtmlJavaBean> tstResults)throws Exception;
    public String toHTMLTable(HtmlJavaBean javaObject) throws Exception;
    public String toHTMLTableDataRow(HtmlJavaBean javaObject) throws Exception;
    public String toHTMLTableHeaderRow(HtmlJavaBean javaObject)throws Exception;
    public String toHTMLTable(List<HtmlJavaBean> javaObject)throws Exception;
    public HtmlJavaBean toJava(String htmlTableRow , Class<? extends HtmlJavaBean> clazz) throws Exception;
    public List<HtmlJavaBean> toJavaList(File htmlFile , Class clazz) throws Exception;
    public List<HtmlJavaBean> toJavaList(URL sourceURL , Class clazz) throws Exception;
    
    
}
