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
package org.hawk.executor.command.internaltest.functional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
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
import org.hawk.tst.functional.InternalFunctionalTest;
import org.commons.resource.ResourceUtil;
import org.hawk.executor.command.interpreter.ScriptExecutor;
import org.hawk.output.DefaultHawkOutputWriter;
import org.hawk.tst.functional.html.InternalFuncTestHTMLJavaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Manoranjan Sahu
 */
public class InternalFunctionalTestExecutor implements IHawkCommandExecutor {

    private static final HawkLogger logger = HawkLogger.getLogger(InternalFunctionalTestExecutor.class.getName());
    @Autowired(required = true)

    private HawkOutput hawkOutput;
    @Autowired(required = true)

    private HawkTestProperties hawkTestProperties;
    @Autowired(required = true)

    private InternalFuncTestHTMLJavaServiceImpl internalFuncTestHTMLJavaServiceImpl;

    public InternalFuncTestHTMLJavaServiceImpl getHtmlJavaService() {
        return internalFuncTestHTMLJavaServiceImpl;
    }

    public void setHtmlJavaService(InternalFuncTestHTMLJavaServiceImpl hawkTestResult) {
        this.internalFuncTestHTMLJavaServiceImpl = hawkTestResult;
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
        boolean status = true;

        List<HtmlJavaBean> hawkTests = new ArrayList<>();

        if (!this.getHawkTestProperties().isLoaded()) {
            this.getHawkTestProperties().load();
        }
        for (Entry<String, String> entry : this.getHawkTestProperties().getTestSuites().entrySet()) {

            if (entry.getKey().equals("8queen")) {
                System.out.println("running " + entry.getKey());
            }
            InternalFunctionalTest hawkTest = InternalFunctionalTest.prepareHawkTest(entry.getKey(), entry.getValue());
            hawkTests.add(hawkTest);
            this.runTestCase(hawkTest);

        }

        this.getHtmlJavaService().createHTMLFile(hawkTests);
        return status;
    }

    private boolean runTestCase(InternalFunctionalTest hawkTest) {

        DefaultScriptExecutor scriptExecutor = AppContainer.getInstance().getBean(ScriptExecutor.class);

        try {

            System.out.println("compiling" + hawkTest.getTestCasePath());
            boolean compiled = scriptExecutor.compile(FileUtil.readFile(hawkTest.getTestCasePath()));
            if (!compiled) {
                hawkTest.setStatus(HawkTestCaseStatusEnum.COMPILATION_FAILED);
                hawkTest.setEndDate(new Date());
                return false;
            }
            System.out.println("passed");
            DefaultHawkOutputWriter defaultHawkOutputWriter = new DefaultHawkOutputWriter();
            defaultHawkOutputWriter.setOutput(hawkTest.getOutput());
            defaultHawkOutputWriter.setEchoOutput(hawkTest.getOutput());
            defaultHawkOutputWriter.setEchoFile(hawkTest.getEchoFile());
            this.getHawkOutput().setHawkOutputWriter(defaultHawkOutputWriter);
            System.out.println("executing " + hawkTest.getTestCasePath());
            scriptExecutor.interpret();
            hawkTest.setEndDate(new Date());
            scriptExecutor.reset();
            System.out.println("execution passed successfully !!!");

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error(ex);
        } finally {
            this.getHawkOutput().closeAll();
            ResourceUtil.close(hawkTest.getOutput(), hawkTest.getOutput());
        }
        return true;
    }
}
