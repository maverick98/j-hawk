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

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.commons.file.FileUtil;
import org.commons.reflection.ClazzUtil;

import org.hawk.html.HTMLUtil;
import org.hawk.html.java.annotation.HTMLTableColumn;
import org.hawk.logger.HawkLogger;
import org.hawk.tst.perf.InternalPerfTestHTMLJavaServiceImpl;
import org.hawk.util.HttpUtil;
import org.commons.string.StringUtil;


/**
 *
 * @author Manoranjan Sahu
 */
   
   
public class DefaultHtmlJavaServiceImpl implements IHtmlJavaService {

    private static final HawkLogger logger = HawkLogger.getLogger(InternalPerfTestHTMLJavaServiceImpl.class.getName());
    private static final Pattern HTMLROWPATTERN = Pattern.compile("<tr\\s*>.*</tr\\s*>");

    /**
     *
     * @param tstResults
     * @return
     * @throws Exception
     */
    @Override
    public String createHTMLData(List<HtmlJavaBean> tstResults) throws Exception {
        String htmlData = this.toHTMLTable(tstResults);
        String tscResulHTML = null;
        String template = tstResults.get(0).getTemplate();
        String replacementData = tstResults.get(0).getReplacementData();

        tscResulHTML = FileUtil.readFile(template, true);

        if (tscResulHTML != null) {
            tscResulHTML = tscResulHTML.replace(replacementData, htmlData);
        }
        return tscResulHTML;
    }

    @Override
    public boolean createHTMLFile(List<HtmlJavaBean> tstResults) throws Exception {
        String result = this.createHTMLData(tstResults);
        String resultHTMLFile = tstResults.get(0).getResultHTMLFile();
        boolean status;

        status = FileUtil.writeFile(resultHTMLFile, result);

        return status;
    }

    @Override
    public String toHTMLTable(List<HtmlJavaBean> tstResults) throws Exception {
        StringBuilder result = new StringBuilder();

        result.append("<table>");
        result.append("\n");
        HtmlJavaBean headerObj = tstResults.get(0);
        result.append(this.toHTMLTableHeaderRow(headerObj));
        result.append("\n");

        for (HtmlJavaBean htmlJavaBean : tstResults) {
            result.append(this.toHTMLTableDataRow(htmlJavaBean));
            result.append("\n");
        }

        result.append("</table>");
        result.append("\n");

        return result.toString();
    }

    @Override
    public String toHTMLTable(HtmlJavaBean javaObject) throws Exception {
        StringBuilder result = new StringBuilder();
        result.append("<table>");
        result.append("\n");
        result.append(this.toHTMLTableHeaderRow(javaObject));
        result.append("\n");
        result.append(this.toHTMLTableDataRow(javaObject));
        result.append("\n");
        result.append("</table>");
        result.append("\n");
        return result.toString();
    }

    @Override
    public List<HtmlJavaBean> toJavaList(File htmlFile, Class clazz) throws Exception {
        String fileStr = htmlFile.getAbsolutePath();
        URL url = null;
        try {
            System.out.println(fileStr);
            url = new URL("file:\\" + fileStr);
        } catch (MalformedURLException ex) {
            Logger.getLogger(DefaultHtmlJavaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Map<Integer, String> dataMap = FileUtil.dumpFileToMap(htmlFile);
        return this.toJavaList(url, clazz);
    }

    private List<HtmlJavaBean> toJavaListInternal(Map<Integer, String> dataMap, Class clazz) throws Exception {
        List<HtmlJavaBean> result = new ArrayList<>();
        if (dataMap != null && !dataMap.isEmpty()) {
            for (Map.Entry<Integer, String> entry : dataMap.entrySet()) {
                if (!StringUtil.isNullOrEmpty(entry.getValue())) {
                    if (HTMLROWPATTERN.matcher(entry.getValue()).matches()) {
                        HtmlJavaBean hawkTest = this.toJava(entry.getValue(), clazz);
                        if (hawkTest != null) {
                            result.add(hawkTest);
                        }
                    }
                }
            }
        }

        return result;
    }

    @Override
    public List<HtmlJavaBean> toJavaList(URL sourceURL, Class clazz) throws Exception {
        Map<Integer, String> dataMap = HttpUtil.dumpURLToMap(sourceURL);
        return this.toJavaListInternal(dataMap, clazz);
    }

    @Override
    public HtmlJavaBean toJava(String htmlTableRowData, Class clazz) throws Exception {
        Object result = null;
        Map<Integer, String> dataMap = HTMLUtil.splitRow(htmlTableRowData);
        Map<Integer, HTMLTableColumnContainer> map = this.getSortedHTMLTableRowMethodMap(clazz);
        Class parameterTypes[] = new Class[]{String.class};
        try {
            for (Map.Entry<Integer, HTMLTableColumnContainer> entry : map.entrySet()) {
                if (!StringUtil.isNullOrEmpty(entry.getValue().getSetter())) {
                    if (!StringUtil.isNullOrEmpty(dataMap.get(entry.getKey()))) {
                        Object arg[] = new Object[]{dataMap.get(entry.getKey())};
                        if (result == null) {
                            result = ClazzUtil.instantiate(clazz);
                        }
                        ClazzUtil.invoke(result, entry.getValue().getSetter(), parameterTypes, arg);
                    }
                }
            }
        } catch (Exception ex) {
            throw new Exception(ex);
        }
        return (HtmlJavaBean) result;
    }

    @Override
    public String toHTMLTableHeaderRow(HtmlJavaBean javaObject) throws Exception {
        if (javaObject == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Set<HTMLTableColumnContainer> htmltccs = this.getSortedHTMLTableRowMethods(javaObject);

        sb.append("<tr>");
        if (htmltccs != null) {

            for (HTMLTableColumnContainer hTMLTableColumnContainer : htmltccs) {
                sb.append("<th>");

                sb.append(hTMLTableColumnContainer.getHeader());

                sb.append("</th>");
            }
        }
        sb.append("</tr>");

        return sb.toString();
    }

    @Override
    public String toHTMLTableDataRow(HtmlJavaBean javaObject) throws Exception {
        if (javaObject == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Set<HTMLTableColumnContainer> htmltccs = this.getSortedHTMLTableRowMethods(javaObject);
        Object[] args = new Object[0];
        sb.append("<tr>");
        try {
            if (htmltccs != null) {

                for (HTMLTableColumnContainer hTMLTableColumnContainer : htmltccs) {
                    sb.append("<td>");
                    if (hTMLTableColumnContainer.getPreDataMethod() != null) {
                        sb.append(ClazzUtil.invoke(javaObject, hTMLTableColumnContainer.getPreDataMethod(), args));
                    }
                    sb.append(ClazzUtil.invoke(javaObject, hTMLTableColumnContainer.getMethod(), args));
                    if (hTMLTableColumnContainer.getPostDataMethod() != null) {
                        sb.append(ClazzUtil.invoke(javaObject, hTMLTableColumnContainer.getPostDataMethod(), args));
                    }
                    sb.append("</td>");
                }
            }
        } catch (Exception ex) {
            throw new Exception(ex);
        }
        sb.append("</tr>");

        return sb.toString();
    }

    private Set<HTMLTableColumnContainer> getSortedHTMLTableRowMethods(Object javaObject) throws Exception {

        Map<Integer, HTMLTableColumnContainer> map = this.getSortedHTMLTableRowMethodMap(javaObject.getClass());
        Set<HTMLTableColumnContainer> result = new TreeSet<>();
        map.entrySet().stream().forEach((entry) -> {
            result.add(entry.getValue());
        });
        return result;
    }

    private Map<Integer, HTMLTableColumnContainer> getSortedHTMLTableRowMethodMap(Class clazz) throws Exception {

        Map<Integer, HTMLTableColumnContainer> result = new TreeMap<>();

        Method[] methods = clazz.getDeclaredMethods();
        Class[] parameterTypes = new Class[0];
        try {
            for (Method method : methods) {
                if (method.isAnnotationPresent(HTMLTableColumn.class)) {
                    HTMLTableColumn htmlTableColumn = (HTMLTableColumn) method.getAnnotation(HTMLTableColumn.class);
                    HTMLTableColumnContainer htmltcc = new HTMLTableColumnContainer();
                    htmltcc.setMethod(method);
                    htmltcc.setSequence(htmlTableColumn.sequence());
                    htmltcc.setHeader(htmlTableColumn.header());
                    htmltcc.setSetter(htmlTableColumn.setter());
                    htmltcc.setPreDataMethod(ClazzUtil.findMethod(clazz, htmlTableColumn.preData(), parameterTypes));
                    htmltcc.setPostDataMethod(ClazzUtil.findMethod(clazz, htmlTableColumn.postData(), parameterTypes));
                    result.put(htmlTableColumn.sequence(), htmltcc);

                }
            }
        } catch (Exception ex) {
            throw new Exception(ex);
        }
        return result;
    }
}
