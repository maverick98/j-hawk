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
 */
package org.hawk.codegen;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.commons.file.FileUtil;
import org.commons.reflection.ClazzUtil;

import org.hawk.executor.command.interpreter.ScriptInterpretationCommand;

import org.hawk.logger.HawkLogger;
import org.hawk.output.DefaultHawkOutputWriter;
import org.hawk.output.HawkOutput;
import org.hawk.output.IHawkOutputWriter;

import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Manoranjan Sahu
 */
public class TemplateServiceImpl implements ITemplateService {

    private static final HawkLogger logger = HawkLogger.getLogger(TemplateServiceImpl.class.getName());
    @Autowired(required = true)
    private HawkOutput hawkOutput;

    public HawkOutput getHawkOutput() {
        return hawkOutput;
    }

    public void setHawkOutput(HawkOutput hawkOutput) {
        this.hawkOutput = hawkOutput;
    }

    @Override
    public String toFile(FileTemplateJavaBean fileJavaBean) throws Exception {
        String result;
        ScriptInterpretationCommand scriptInterpretationCommand = new ScriptInterpretationCommand();
        String hawkScriptingData;
        try {
            hawkScriptingData = this.preProcess(fileJavaBean);
            scriptInterpretationCommand.setHawkScriptData(hawkScriptingData);
            DefaultHawkOutputWriter defaultHawkOutputWriter = new DefaultHawkOutputWriter();
            defaultHawkOutputWriter.setEchoFile(new File(fileJavaBean.getOutputFile()));
            defaultHawkOutputWriter.setErrorFile(new File(fileJavaBean.getErrorFile()));
            this.getHawkOutput().setHawkOutputWriter(defaultHawkOutputWriter);
            System.out.println(hawkScriptingData);
            scriptInterpretationCommand.execute();
            fileJavaBean.setCurrentData(FileUtil.readFile(fileJavaBean.getOutputFile()));
            result = this.postProcess(fileJavaBean);
            FileUtil.writeFile(fileJavaBean.getOutputFile(), result);
        } catch (Exception ex) {
            logger.error(ex);
            throw new Exception(ex);
        }

        return result;
    }

    private IPreProcessor findPreProcessor(FileTemplateJavaBean templateJavaBean) {
        boolean isPreProcessorImplented = (templateJavaBean instanceof IPreProcessor);
        IPreProcessor preProcessor = null;
        if (isPreProcessorImplented) {
            if (templateJavaBean instanceof IPreProcessor) {
                preProcessor = (IPreProcessor) templateJavaBean;
            }
        } else {
            preProcessor = new DefaultPreProcessor();
        }

        return preProcessor;
    }

    private IPostProcessor findPostProcessor(FileTemplateJavaBean templateJavaBean) {
        boolean isPostProcessorImplented = (templateJavaBean instanceof IPostProcessor);
        IPostProcessor postProcessor = null;
        if (isPostProcessorImplented) {
            if (templateJavaBean instanceof IPostProcessor) {
                postProcessor = (IPostProcessor) templateJavaBean;
            }
        } else {
            postProcessor = new DefaultPostProcessor();
        }

        return postProcessor;
    }

    @Override
    public String preProcess(FileTemplateJavaBean templateJavaBean) throws Exception {
        templateJavaBean.loadCurrentData();
        String templateData = templateJavaBean.getCurrentData();

        IPreProcessor preProcessor = this.findPreProcessor(templateJavaBean);
        IPreProcessor defaultPreProcessor = new DefaultPreProcessor();
        IntermediateTemplateData intermediateTemplateData = new IntermediateTemplateData();
        for (Field field : templateJavaBean.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(FieldData.class)) {
                FieldData fieldData = (FieldData) field.getAnnotation(FieldData.class);
                if (fieldData.pre()) {

                    String methodStr = ClazzUtil.getGetterMethod(field.getName());

                    Object data = null;
                    try {
                        data = ClazzUtil.invoke(templateJavaBean, methodStr, new Class[]{}, new Object[]{});
                    } catch (Exception ex) {
                        Logger.getLogger(TemplateServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (data == null) {
                        continue;
                    }
                    String key = ((FieldData) field.getAnnotation(FieldData.class)).value();
                    intermediateTemplateData.setTemplateData(templateData);
                    intermediateTemplateData.setKey(key);
                    intermediateTemplateData.setValue(data.toString());
                    if (fieldData.multiple()) {
                        List<String> coll = (List<String>) data;
                        for (int i = 0; i < coll.size(); i++) {
                            intermediateTemplateData.setIndex(i);
                            intermediateTemplateData.setValue(coll.get(i));
                            try {
                                preProcessor.processMultipleDataPre(intermediateTemplateData);
                            } catch (UnsupportedOperationException uoe) {
                                logger.error(uoe);
                            }
                        }
                        preProcessor.handleMultipleDataPreProcessFinished(intermediateTemplateData);

                    } else {

                        intermediateTemplateData.setValue(data.toString());
                        try {
                            preProcessor.processSingleDataPre(intermediateTemplateData);
                        } catch (UnsupportedOperationException uoe) {
                            defaultPreProcessor.processSingleDataPre(intermediateTemplateData);
                        }

                    }
                    templateData = intermediateTemplateData.getTemplateData();
                }

            }
        }
        preProcessor.handlePreProcessFinished(intermediateTemplateData);
        templateJavaBean.setCurrentData(templateData);
        return templateData;
    }

    @Override
    public String postProcess(FileTemplateJavaBean templateJavaBean) throws Exception {

        String templateData = templateJavaBean.getCurrentData();
        Class clazz = templateJavaBean.getClass();

        IPostProcessor postProcessor = this.findPostProcessor(templateJavaBean);
        IPostProcessor defaultPostProcessor = new DefaultPostProcessor();
        IntermediateTemplateData intermediateTemplateData = new IntermediateTemplateData();
        intermediateTemplateData.setTemplateData(templateData);
        boolean isThereAnyMultipleData = false;
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(FieldData.class)) {
                FieldData fieldData = (FieldData) field.getAnnotation(FieldData.class);
                if (fieldData.post()) {
                    String methodStr = ClazzUtil.getGetterMethod(field.getName());
                    Object data = null;
                    try {
                        data = ClazzUtil.invoke(templateJavaBean, methodStr, new Class[]{}, new Object[]{});
                    } catch (Exception ex) {
                        Logger.getLogger(TemplateServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (data == null) {
                        continue;
                    }
                    String key = ((FieldData) field.getAnnotation(FieldData.class)).value();
                    intermediateTemplateData.setTemplateData(templateData);
                    intermediateTemplateData.setKey(key);

                    if (fieldData.multiple()) {

                        List<String> coll = (List<String>) data;
                        if (!coll.isEmpty()) {
                            isThereAnyMultipleData = true;
                        }
                        for (int i = 0; i < coll.size(); i++) {
                            intermediateTemplateData.setIndex(i);
                            intermediateTemplateData.setValue(coll.get(i));
                            try {
                                postProcessor.processMultipleDataPost(intermediateTemplateData);
                            } catch (UnsupportedOperationException uoe) {
                                defaultPostProcessor.processMultipleDataPost(intermediateTemplateData);
                            }
                        }

                    } else {

                        intermediateTemplateData.setValue(data.toString());
                        try {
                            postProcessor.processSingleDataPost(intermediateTemplateData);
                        } catch (UnsupportedOperationException uoe) {
                            defaultPostProcessor.processSingleDataPost(intermediateTemplateData);
                        }

                    }
                    templateData = intermediateTemplateData.getTemplateData();
                }

            }
        }
        if (isThereAnyMultipleData) {
            postProcessor.handleMultipleDataPostProcessFinished(intermediateTemplateData);
        }
        templateData = intermediateTemplateData.getTemplateData();
        templateJavaBean.setCurrentData(templateData);
        return templateData;
    }
}
