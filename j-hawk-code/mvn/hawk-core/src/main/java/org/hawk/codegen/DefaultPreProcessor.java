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
public class DefaultPreProcessor implements IPreProcessor{

    @Override
    public boolean processMultipleDataPre(IntermediateTemplateData intermediateTemplateData) {
        return this.processMultipleDataInternal(intermediateTemplateData);
    }

    @Override
    public boolean processSingleDataPre(IntermediateTemplateData intermediateTemplateData) {
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
        String value =  intermediateTemplateData.getValue();
        templateData = templateData.replaceAll(key, value);
        intermediateTemplateData.setTemplateData(templateData);
        return true;
    }

    @Override
    public boolean handleMultipleDataPreProcessFinished(IntermediateTemplateData intermediateTemplateData) {
        return true;
    }

    @Override
    public boolean handlePreProcessFinished(IntermediateTemplateData intermediateTemplateData) {
       return true;
    }

    
    

}
