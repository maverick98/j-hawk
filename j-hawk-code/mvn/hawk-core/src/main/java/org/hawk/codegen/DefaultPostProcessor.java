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
package org.hawk.codegen;

/**
 *
 * @author Manoranjan Sahu
 */
public class DefaultPostProcessor implements IPostProcessor {

    @Override
    public boolean processMultipleDataPost(IntermediateTemplateData intermediateTemplateData) {
        return this.processMultipleDataInternal(intermediateTemplateData);
    }

    @Override
    public boolean processSingleDataPost(IntermediateTemplateData intermediateTemplateData) {
        return this.processSingleDataInternal(intermediateTemplateData);
    }

    private boolean processMultipleDataInternal(IntermediateTemplateData intermediateTemplateData) {
        String templateData = intermediateTemplateData.getTemplateData();
        String key = intermediateTemplateData.getKey();
        String value =  intermediateTemplateData.getValue();
        templateData = templateData.replaceFirst(key, value);
        intermediateTemplateData.setTemplateData(templateData);
        return true;
    }

    private boolean processSingleDataInternal(IntermediateTemplateData intermediateTemplateData) {
        String templateData = intermediateTemplateData.getTemplateData();
        String key = intermediateTemplateData.getKey();
        String value = (String) intermediateTemplateData.getValue();
        templateData = templateData.replaceAll(key, value);
        intermediateTemplateData.setTemplateData(templateData);
        return true;
    }

    @Override
    public boolean handleMultipleDataPostProcessFinished(IntermediateTemplateData intermediateTemplateData) {
        return true;
    }

    @Override
    public boolean handlePostProcessFinished(IntermediateTemplateData intermediateTemplateData) {
        return true;
    }

    
}
