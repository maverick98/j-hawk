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
package org.hawk.tst.perf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.commons.file.FileUtil;
import static org.hawk.constant.HawkConstant.ENCODING;
import static org.hawk.constant.HawkConstant.FORMATTER;


import org.hawk.html.java.HtmlJavaBean;
import org.hawk.html.java.annotation.HTMLTableColumn;
import org.hawk.logger.HawkLogger;
import org.hawk.tst.HawkTestCaseStatusEnum;
import org.commons.resource.ResourceUtil;
import org.commons.string.StringUtil;

/**
 *
 * @author Manoranjan Sahu
 */
public class InternalPerfTest extends HtmlJavaBean {

    private static final HawkLogger logger = HawkLogger.getLogger(InternalPerfTest.class.getName());
    private String testCase;
    private String testCasePath;
    private File echoFile;
    private File echoNewFile;
    private File diffFile;
    private Date startDate;
    private Date endDate;
    private String lastDuration;
    private String duration;
    private PrintWriter output;
    private static final String testResultTemplate = "conf/internalPerfTestResult.html";
    private static final String resulHtmlFile = "internalPerfTestResult.html";
    private static final String replacementDataStr = "#HawkInternalPerfResult#";

    public InternalPerfTest() {
        super("conf/internalPerfTestResult.html");
    }
    private HawkTestCaseStatusEnum status;

    public String getTestCasePath() {
        return testCasePath;
    }

    public void setTestCasePath(String testCasePath) {
        this.testCasePath = testCasePath;
    }

    public PrintWriter getOutput() {
        return output;
    }

    public void setOutput(PrintWriter output) {
        this.output = output;
    }

    public String getEchoFileStr() {
        String result;
        if (!StringUtil.isNullOrEmpty(this.getTestCasePath())) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.getTestCasePath());
            sb.append(".echo");
            result = sb.toString();
        } else {
            result = null;
        }
        return result;
    }

    public String getEchoNewFileStr() {
        String result;
        if (!StringUtil.isNullOrEmpty(this.getTestCasePath())) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.getTestCasePath());
            sb.append(".echonew");
            result = sb.toString();
        } else {
            result = null;
        }
        return result;
    }

    public String getEchoDiffFileStr() {
        String result;
        if (!StringUtil.isNullOrEmpty(this.getTestCasePath())) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.getTestCasePath());
            sb.append(".echodiff");
            result = sb.toString();
        } else {
            result = null;
        }
        return result;
    }

    public static InternalPerfTest prepareHawkTest(String testCase, String testCasePath) throws Exception {
        InternalPerfTest hawkTest = new InternalPerfTest();
        hawkTest.setTemplate(testResultTemplate);
        hawkTest.setResultHTMLFile(resulHtmlFile);
        hawkTest.setReplacementData(replacementDataStr);
        hawkTest.setStartDate(new Date());
        hawkTest.setTestCase(testCase);
        hawkTest.setTestCasePath(testCasePath);
        String echoFileStr = hawkTest.getEchoFileStr();
        File hawkOutputFile = new File(echoFileStr);
        File hawkOutputNewFile;


        PrintWriter printWriter = null;
        try {
            if (hawkOutputFile.exists()) {
                hawkTest.setEchoFile(hawkOutputFile);
                String echoNewFileStr = hawkTest.getEchoNewFileStr();
                hawkOutputNewFile = new File(echoNewFileStr);
                printWriter = new PrintWriter(hawkOutputNewFile, ENCODING);
                hawkTest.setEchoNewFile(hawkOutputNewFile);
                String echoDiffFileStr = hawkTest.getEchoDiffFileStr();
                File hawkOutputDiffFile = new File(echoDiffFileStr);
                hawkTest.setDiffFile(hawkOutputDiffFile);
            } else {
                printWriter = new PrintWriter(hawkOutputFile, ENCODING);
                hawkTest.setEchoFile(hawkOutputFile);


            }
            hawkTest.setOutput(printWriter);
        } catch (FileNotFoundException fnfe) {
            throw new Exception("file not found ", fnfe);
        } catch (IOException ioex) {
            throw new Exception("error occurred", ioex);
        } finally {
            ResourceUtil.close(printWriter);
        }
        return hawkTest;
    }

    @HTMLTableColumn(sequence = 1, header = "Test Case", setter = "setTestCase")
    public String getTestCase() {
        return testCase;
    }

    @HTMLTableColumn(sequence = 2, header = "Test started at", setter = "setFormattedStartDate")
    public String getFormattedStartDate() {
        SimpleDateFormat formatter = new SimpleDateFormat(FORMATTER);
        return formatter.format(this.getStartDate());
    }

    @HTMLTableColumn(sequence = 3, header = "Test ended at", setter = "setFormattedEndDate")
    public String getFormattedEndDate() {
        SimpleDateFormat formatter = new SimpleDateFormat(FORMATTER);
        return formatter.format(this.getEndDate());
    }

    @HTMLTableColumn(sequence = 4, header = "Status", preData = "highlightStatusPre", postData = "highlightStatusPost")
    public HawkTestCaseStatusEnum getStatus() {
        return status;
    }

    @HTMLTableColumn(sequence = 5, header = "Last Duration (ms)", setter = "setLastDuration")
    public String getLastDuration() {
        if (StringUtil.isNullOrEmpty(this.lastDuration)) {
            lastDuration = "NA";
        }
        return lastDuration;
    }

    @HTMLTableColumn(sequence = 6, header = "Duration (ms)", setter = "setDuration")
    public String getDuration() {
        if (StringUtil.isNullOrEmpty(this.duration)) {
            duration = String.valueOf(this.getEndDate().getTime() - this.getStartDate().getTime());
        }
        return duration;
    }

    @HTMLTableColumn(sequence = 7, header = "diff")
    public String getDiff() {
        String diff = "NA";
        if (this.checkForDiff()) {
           
                diff = FileUtil.readFile(this.getDiffFile());
           
        }
        if (diff == null) {
            diff = "NA";
        }
        return diff;
    }

    public String highlightStatusPre() {
        StringBuilder sb = new StringBuilder();
        if (HawkTestCaseStatusEnum.PASS.equals(this.getStatus())) {
            sb.append("<h4> <font color =\"GREEN\">");
        } else {
            sb.append("<h2><font color =\"RED\">");
        }
        return sb.toString();
    }

    public String highlightStatusPost() {
        StringBuilder sb = new StringBuilder();
        if (HawkTestCaseStatusEnum.PASS.equals(this.getStatus())) {
            sb.append("</font></h4>");
        } else {
            sb.append("</font></h2>");
        }
        return sb.toString();
    }

    public void setStatus(HawkTestCaseStatusEnum status) {
        this.status = status;
    }

    public void setTestCase(String testCase) {
        this.testCase = testCase;
    }

    public Date getStartDate() {
        return startDate != null ? (Date) startDate.clone() : null;
    }

    public void setStartDate(Date startDate) {
        this.startDate = (Date) startDate.clone();
    }

    public Date getEndDate() {
        return endDate != null ? (Date) endDate.clone() : null;
    }

    public void setEndDate(Date endDate) {
        this.endDate = (Date) endDate.clone();
    }

    public void setFormattedStartDate(String formatterStartDate) {
        if (!StringUtil.isNullOrEmpty(formatterStartDate)) {
            SimpleDateFormat formatter = new SimpleDateFormat(FORMATTER);
            try {

                this.setStartDate(formatter.parse(formatterStartDate));
            } catch (ParseException ex) {
                logger.error(ex);
            }
        }
    }

    public void setFormattedEndDate(String formatterEndDate) {
        if (!StringUtil.isNullOrEmpty(formatterEndDate)) {
            SimpleDateFormat formatter = new SimpleDateFormat(FORMATTER);
            try {

                this.setEndDate(formatter.parse(formatterEndDate));
            } catch (ParseException ex) {
                logger.error(ex);
            }
        }
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setLastDuration(String duration) {
        this.lastDuration = duration;
    }

    public boolean checkForDiff() {
        return echoFile != null && echoNewFile != null;
    }

    public File getEchoFile() {
        return echoFile;
    }

    public void setEchoFile(File echoFile) {
        this.echoFile = echoFile;
    }

    public File getEchoNewFile() {
        return echoNewFile;
    }

    public void setEchoNewFile(File echoNewFile) {
        this.echoNewFile = echoNewFile;
    }

    public File getDiffFile() {
        return diffFile;
    }

    public void setDiffFile(File diffFile) {
        this.diffFile = diffFile;
    }
}
