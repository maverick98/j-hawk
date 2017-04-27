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
package org.hawk.tst.perf;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.commons.file.FileUtil;


import org.hawk.html.java.DefaultHtmlJavaServiceImpl;
import org.hawk.html.java.HtmlJavaBean;
import org.hawk.logger.HawkLogger;
import org.hawk.tst.HawkTestCaseStatusEnum;

/**
 *
 * @author Manoranjan Sahu
 */
//@Component(INTERNALPERFTESTHTMLJAVASERVICE)
//@Qualifier(DEFAULTQUALIFIER)
public class InternalPerfTestHTMLJavaServiceImpl extends DefaultHtmlJavaServiceImpl {

    private static final HawkLogger logger = HawkLogger.getLogger(InternalPerfTestHTMLJavaServiceImpl.class.getName());
    private static final Pattern HTMLROWPATTERN = Pattern.compile("<tr\\s*>.*</tr\\s*>");
    private String testResultTemplate = "conf/internalPerfTestResult.html";

  
   
   
    public Map<String, InternalPerfTest> extractHawkTestRows(String testResultFile) throws Exception {
        Map<String, InternalPerfTest> result = new HashMap<String, InternalPerfTest>();


        List<HtmlJavaBean> hawkJavaBeans = this.toJavaList(new File(testResultFile), InternalPerfTest.class);
        for(HtmlJavaBean htmlJavaBean : hawkJavaBeans){
            InternalPerfTest internalPerfTest = (InternalPerfTest)htmlJavaBean;
            result.put(internalPerfTest.getTestCase(),internalPerfTest);
        }

        return result;
    }

    @Override
    public String toHTMLTable(List<HtmlJavaBean> tstResults) throws Exception {
        for (HtmlJavaBean htmlJavaBean : tstResults) {
            InternalPerfTest hawkTest = (InternalPerfTest) htmlJavaBean;
            boolean anyDiff = false;
            if (hawkTest.checkForDiff()) {
                anyDiff = FileUtil.diff(hawkTest.getEchoFile(), hawkTest.getEchoNewFile(), hawkTest.getDiffFile());
            }
            hawkTest.setStatus(!anyDiff ? HawkTestCaseStatusEnum.PASS : HawkTestCaseStatusEnum.FAIL);


        }
        StringBuilder result = new StringBuilder();

        result.append("<table>");
        result.append("\n");
        HtmlJavaBean headerObj = tstResults.get(0);
        result.append(this.toHTMLTableHeaderRow(headerObj));
        result.append("\n");
        Map<String, InternalPerfTest> olderMap = this.extractHawkTestRows("internalPerfTestResult.html");
        for (HtmlJavaBean htmlJavaBean : tstResults) {
            InternalPerfTest internalPerfTest = (InternalPerfTest)htmlJavaBean;
            result.append(this.toHTMLTableDataRow(internalPerfTest));
            if (olderMap.containsKey(internalPerfTest.getTestCase())) {
                internalPerfTest.setLastDuration(String.valueOf(olderMap.get(internalPerfTest.getTestCase()).getDuration()));
            }
            result.append("\n");
        }


        result.append("</table>");
        result.append("\n");



        return result.toString();
    }
}
