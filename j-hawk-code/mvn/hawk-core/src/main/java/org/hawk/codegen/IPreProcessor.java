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
 * @author user
 */
public interface IPreProcessor {

    public boolean processMultipleDataPre(IntermediateTemplateData intermediateTemplateData);
    
    public boolean handleMultipleDataPreProcessFinished(IntermediateTemplateData intermediateTemplateData);
    
    public boolean processSingleDataPre(IntermediateTemplateData intermediateTemplateData);
    
    public boolean handlePreProcessFinished(IntermediateTemplateData intermediateTemplateData);
    
    
}
