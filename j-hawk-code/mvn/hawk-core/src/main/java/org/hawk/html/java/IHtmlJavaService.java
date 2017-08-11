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
