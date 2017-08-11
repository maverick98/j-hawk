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
package org.hawk.tst.functional.html;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.commons.file.FileUtil;


import org.hawk.html.java.DefaultHtmlJavaServiceImpl;
import org.hawk.html.java.HtmlJavaBean;
import org.hawk.logger.HawkLogger;
import org.hawk.tst.HawkTestCaseStatusEnum;
import org.hawk.tst.functional.InternalFunctionalTest;
import org.commons.string.StringUtil;

/**
 *
 * @author Manoranjan Sahu
 */
   
   
public class InternalFuncTestHTMLJavaServiceImpl extends DefaultHtmlJavaServiceImpl {

    private static final HawkLogger logger = HawkLogger.getLogger(InternalFuncTestHTMLJavaServiceImpl.class.getName());
    private static final Pattern HTMLROWPATTERN = Pattern.compile("<tr\\s*>.*</tr\\s*>");
    private String testResultTemplate = "conf/internalFunctionalTestResult.html";

   
   


    public Map<String, InternalFunctionalTest> extractHawkTestRows1(String testResultFile) throws Exception {
        Map<String, InternalFunctionalTest> result = new HashMap<>();


        Map<Integer, String> dataMap = FileUtil.dumpFileToMap(testResultFile);
        if (dataMap != null && !dataMap.isEmpty()) {
            for (Map.Entry<Integer, String> entry : dataMap.entrySet()) {
                if (!StringUtil.isNullOrEmpty(entry.getValue())) {
                    if (HTMLROWPATTERN.matcher(entry.getValue()).matches()) {
                        InternalFunctionalTest hawkTest = (InternalFunctionalTest) this.toJava(entry.getValue(),InternalFunctionalTest.class);

                        if (hawkTest != null) {
                            result.put(hawkTest.getTestCase(), hawkTest);
                            //System.out.println(hawkTest.getDuration());
                        }
                    }
                }
            }
        }

        return result;
    }

    @Override
    public String toHTMLTable(List<HtmlJavaBean> tstResults) throws Exception {
        for (HtmlJavaBean htmlJavaBean : tstResults) {
            boolean anyDiff = false;
            InternalFunctionalTest hawkTest = (InternalFunctionalTest) htmlJavaBean;
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

        for (HtmlJavaBean internalFunctionalTest : tstResults) {
            result.append(this.toHTMLTableDataRow(internalFunctionalTest));
            result.append("\n");
        }


        result.append("</table>");
        result.append("\n");



        return result.toString();
    }
}
