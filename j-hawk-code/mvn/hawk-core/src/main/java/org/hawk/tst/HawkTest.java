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
package org.hawk.tst;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.commons.file.FileUtil;
import static org.hawk.constant.HawkConstant.FORMATTER;


import org.hawk.html.java.HtmlJavaBean;
import org.hawk.html.java.IHtmlJavaService;
import org.hawk.html.java.annotation.HTMLTableColumn;
import org.hawk.logger.HawkLogger;
import org.commons.string.StringUtil;

/**
 *
 * @author Manoranjan Sahu
 */
public class HawkTest extends HtmlJavaBean{

    private static final HawkLogger logger = HawkLogger.getLogger(HawkTest.class.getName());
    private String testCase;
    private String testCasePath;
    private File echoFile;
    private File echoNewFile;
    private File diffFile;
    private Date startDate;
    private Date endDate;
    private Long duration;
    private OutputStream output;
    private FileOutputStream fos;

    public String getTestCasePath() {
        return testCasePath;
    }

    public void setTestCasePath(String testCasePath) {
        this.testCasePath = testCasePath;
    }

    public FileOutputStream getFos() {
        return fos;
    }

    public void setFos(FileOutputStream fos) {
        this.fos = fos;
    }

    public OutputStream getOutput() {
        return output;
    }

    public void setOutput(OutputStream output) {
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

    public static HawkTest prepareHawkTest(String testCase, String testCasePath) throws Exception {
        HawkTest hawkTest = new HawkTest();
        hawkTest.setStartDate(new Date());
        hawkTest.setTestCase(testCase);
        hawkTest.setTestCasePath(testCasePath);
        String echoFileStr = hawkTest.getEchoFileStr();
        File hawkOutputFile = new File(echoFileStr);
        File hawkOutputNewFile;
        FileOutputStream fos;
        BufferedOutputStream outBfr;
        try {
            if (hawkOutputFile.exists()) {
                hawkTest.setEchoFile(hawkOutputFile);
                String echoNewFileStr = hawkTest.getEchoNewFileStr();
                hawkOutputNewFile = new File(echoNewFileStr);
                fos = new FileOutputStream(hawkOutputNewFile);
                //osw = new OutputStreamWriter(fos,Charset.forName(ENCODING));
                outBfr = new BufferedOutputStream(fos);

                hawkTest.setEchoNewFile(hawkOutputNewFile);
                String echoDiffFileStr = hawkTest.getEchoDiffFileStr();
                File hawkOutputDiffFile = new File(echoDiffFileStr);
                hawkTest.setDiffFile(hawkOutputDiffFile);
            } else {
                fos = new FileOutputStream(hawkOutputFile);
                outBfr = new BufferedOutputStream(fos);
                hawkTest.setEchoFile(hawkOutputFile);

            }
            hawkTest.setFos(fos);
            hawkTest.setOutput(outBfr);
        } catch (FileNotFoundException fnfe) {
            throw new Exception("file not found ", fnfe);
        } 
        return hawkTest;
    }

    @HTMLTableColumn(sequence = 1, header = "Test Case", setter = "setTestCase")
    public String getTestCase() {
        return testCase;
    }
    private HawkTestCaseStatusEnum status;

    @HTMLTableColumn(sequence = 5, header = "Status", preData = "highlightStatusPre", postData = "highlightStatusPost")
    public HawkTestCaseStatusEnum getStatus() {
        return status;
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

    @HTMLTableColumn(sequence = 2, header = "Test started at", setter = "setFormattedStartDate")
    public String getFormattedStartDate() {
        SimpleDateFormat formatter = new SimpleDateFormat(FORMATTER);
        return formatter.format(this.getStartDate());
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

    @HTMLTableColumn(sequence = 3, header = "Test ended at", setter = "setFormattedEndDate")
    public String getFormattedEndDate() {
        SimpleDateFormat formatter = new SimpleDateFormat(FORMATTER);
        return formatter.format(this.getEndDate());
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

    @HTMLTableColumn(sequence = 4, header = "Duration (ms)" , setter="setDuration")
    public long getDuration() {
        if( duration == null){
            duration = (this.getEndDate().getTime() - this.getStartDate().getTime());
        }
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = Long.parseLong(duration);
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

    @HTMLTableColumn(sequence = 6, header = "diff")
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

    /**
     * TODO check it later.
     * @return 
     */
    public String toHTML() {
        String result = null;
        IHtmlJavaService htmlJavaService = null;//AppContainer.getInstance().getBean(HTMLJAVASERVICE, IHtmlJavaService.class);
        try {
            result = htmlJavaService.toHTMLTableDataRow(this);
        } catch (Exception ex) {
            logger.error(ex);
        }
        return result;
    }

    @Override
    public String toString() {
        return this.toHTML();
    }
}
