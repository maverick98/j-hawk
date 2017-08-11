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
package org.hawk.executor.command.internaltest.perf;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.commons.file.FileUtil;
import org.common.di.AppContainer;

import org.hawk.executor.DefaultScriptExecutor;
import org.hawk.executor.IHawkCommandExecutor;
import org.hawk.executor.command.IHawkExecutionCommand;
import org.hawk.html.java.HtmlJavaBean;
import org.hawk.logger.HawkLogger;
import org.hawk.output.HawkOutput;
import org.hawk.tst.HawkTestCaseStatusEnum;
import org.hawk.tst.HawkTestProperties;
import org.hawk.tst.perf.InternalPerfTest;
import org.commons.resource.ResourceUtil;
import org.hawk.executor.command.interpreter.ScriptExecutor;
import org.hawk.tst.perf.InternalPerfTestHTMLJavaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Manoranjan Sahu
 */
   
   
public class InternalPerfTestExecutor implements IHawkCommandExecutor {

    private static final HawkLogger logger = HawkLogger.getLogger(InternalPerfTestExecutor.class.getName());
    @Autowired(required = true)
       
    private HawkOutput hawkOutput;
    @Autowired(required = true)
       
    private HawkTestProperties hawkTestProperties;
    @Autowired(required = true)
       
    private InternalPerfTestHTMLJavaServiceImpl internalPerfTestHTMLJavaServiceImpl;

    public InternalPerfTestHTMLJavaServiceImpl getHtmlJavaService() {
        return internalPerfTestHTMLJavaServiceImpl;
    }

    public void setHtmlJavaService(InternalPerfTestHTMLJavaServiceImpl hawkTestResult) {
        this.internalPerfTestHTMLJavaServiceImpl = hawkTestResult;
    }

    public HawkOutput getHawkOutput() {
        return hawkOutput;
    }

    public void setHawkOutput(HawkOutput hawkOutput) {
        this.hawkOutput = hawkOutput;
    }

    public HawkTestProperties getHawkTestProperties() {
        return hawkTestProperties;
    }

    public void setHawkTestProperties(HawkTestProperties hawkTestProperties) {
        this.hawkTestProperties = hawkTestProperties;
    }

    @Override
    public boolean execute(IHawkExecutionCommand hawkCommand) throws Exception {
        boolean status = false;
        if (!this.getHawkTestProperties().isLoaded()) {
            this.getHawkTestProperties().load();
        }
        List<HtmlJavaBean> hawkTests = new ArrayList<>();
        for (Map.Entry<String, String> entry : this.getHawkTestProperties().getTestSuites().entrySet()) {
            InternalPerfTest hawkTest = InternalPerfTest.prepareHawkTest(entry.getKey(), entry.getValue());
            hawkTests.add(hawkTest);
            long sigmaDuration = 0;
            for (int i = 1; i <= 10; i++) {
                this.runTestCase(hawkTest);
                sigmaDuration += Long.parseLong(hawkTest.getDuration());
            }
            long avgDuration = sigmaDuration / 10;
            hawkTest.setDuration(String.valueOf(avgDuration));
        }
        this.getHtmlJavaService().createHTMLFile(hawkTests);
        return status;


    }

    private boolean runTestCase(InternalPerfTest hawkTest) {

        DefaultScriptExecutor scriptInterpreter = AppContainer.getInstance().getBean(ScriptExecutor.class);

        try {

            System.out.println("compiling" + hawkTest.getTestCasePath());
            hawkTest.setStartDate(new Date());
            boolean compiled = scriptInterpreter.compile(FileUtil.readFile(hawkTest.getTestCasePath()));
            if (!compiled) {
                hawkTest.setStatus(HawkTestCaseStatusEnum.COMPILATION_FAILED);
                hawkTest.setEndDate(new Date());
                return false;
            }
            System.out.println("passed");

            this.getHawkOutput().setOutput(hawkTest.getOutput());
            System.out.println("executing " + hawkTest.getTestCasePath());
            scriptInterpreter.interpret();
            hawkTest.setEndDate(new Date());
            scriptInterpreter.reset();
            System.out.println("execution passed successfully !!!");

        } catch (Exception ex) {
            logger.error(ex);
        }  finally {
            ResourceUtil.close(hawkTest.getOutput(), hawkTest.getOutput());
        }
        return true;
    }
}
