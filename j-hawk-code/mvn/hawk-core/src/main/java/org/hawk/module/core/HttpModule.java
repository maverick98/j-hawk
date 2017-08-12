/*
 * This file is part of j-hawk
 * CopyLeft (C) BigBang<->BigCrunch Manoranjan Sahu, All Rights are left.
 * 
 */
package org.hawk.module.core;

import com.jayway.restassured.RestAssured;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import org.hawk.data.perf.PerfDataProcessor;
import org.common.di.AppContainer;
import org.hawk.http.HttpExecutor;
import org.hawk.http.IHttpExecutor;
import org.hawk.lang.object.ControlRequest;
import org.hawk.lang.object.StructureScript;
import org.hawk.logger.HawkLogger;
import org.hawk.module.annotation.CollectPerfData;
import org.hawk.module.annotation.SubTask;
 import org.commons.reflection.Create;
import org.common.di.ScanMe;
import org.hawk.http.HttpRestExecutor;

/**
 * This is a hawk core module . This hits the targetURL with the parameters.
 * This can upload files too.
 *
 * @version 1.0 1 Aug, 2010
 * @author msahu
 * @see SystemModule
 */
@ScanMe(true)
   
   
@CollectPerfData(myself = true)

public class HttpModule extends HawkCoreModule {

    private List<Thread> threads = new ArrayList<Thread>();

    @Override
    public String toString() {
        return this.getName();
    }

    public String getName() {
        return "HttpModule";
    }

    @Override
    public boolean startUp() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean reset() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @SubTask(name = "execute", sequence = 1, ignoreException = false, hawkParam = "var hawkStruct")
    public boolean execute(Object... args) throws Exception {
        for (Object arg : args) {
            StructureScript httpStructScript = (StructureScript) arg;

            ControlRequest controlRequest = new ControlRequest(httpStructScript.toJavaMap());
            httpStructScript.setControlRequest(controlRequest);
            IHttpExecutor httpExecutor = null; //new HttpExecutor(this.getName(), httpStructScript);
            if(controlRequest.isRest()){
                httpExecutor = new HttpRestExecutor(this.getName(), httpStructScript);
            }else{
                httpExecutor = new HttpExecutor(this.getName(), httpStructScript);
            }

            String currentActionName = controlRequest.getActionName();
            boolean useThread = controlRequest.isConcurrent();
            if (useThread) {
                logger.info("creating hawk thread....");
                Thread hawkHttpAgentThread = new Thread(httpExecutor);
                hawkHttpAgentThread.setName(currentActionName);
                hawkHttpAgentThread.start();
                this.threads.add(hawkHttpAgentThread);
            } else {
                logger.info("serial execution");
                Thread.currentThread().setName(currentActionName);
                httpExecutor.execute();
            }
        }
        return true;
    }
    
    @SubTask(name = "setKeyStore", sequence = 1, ignoreException = false, hawkParam = "var hawkStruct")
    public boolean setKeyStore(Object... args) throws Exception {
        String keystoreFile = args[0].toString();
        String password= args[1].toString();
        System.out.println(keystoreFile);
        System.out.println(password);
        RestAssured.keystore(getCertificateFile(keystoreFile).getPath(), password);
        return true;
    }
     static File getCertificateFile(String name) {
        String val = "";
        try {
            
            File fromPath = new File(name);
            String localScriptPath = System.getProperty("java.io.tmpdir");
            File tmpFile = File.createTempFile("cert-", ".pem", new File(localScriptPath));
            Files.copy(fromPath.toPath(), tmpFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
           
            return tmpFile;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This waits for all the hawk threads to finish their tasks.
     *
     * @param allThreads list of all threads which are started.
     * @return true on success
     */
    @SubTask(name = "wait", sequence = 1, ignoreException = false, hawkParam = "")
    public boolean waitforThreadsToFinish(Object... args) throws Exception {
        boolean status;
        if (this.threads == null || this.threads.isEmpty()) {
            status = false;
        } else {
            for (Thread thread : this.threads) {
                try {
                    thread.join();
                } catch (InterruptedException ex) {
                    logger.error(null, ex);
                    status = false;

                }
            }
            status = this.resetThreads(args);
        }
        return status;
    }

    /**
     * This resets all the hawk threads.
     *
     * @param allThreads list of all threads which are started.
     * @return true on success
     */
    public boolean resetThreads(Object... args) throws Exception {
        boolean status = false;
        this.threads = new ArrayList<>();
        PerfDataProcessor hawkPerfDataCollector = AppContainer.getInstance().getBean(PerfDataProcessor.class);
        hawkPerfDataCollector.dump(true);
        status = true;
        return status;
    }
    @Create
    public HawkCoreModule create() {
        return AppContainer.getInstance().getBean(this.getClass());
    }
    private static final HawkLogger logger = HawkLogger.getLogger(HttpModule.class.getName());
}
