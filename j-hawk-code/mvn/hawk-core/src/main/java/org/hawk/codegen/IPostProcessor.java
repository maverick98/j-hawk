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
public interface IPostProcessor {

    public boolean processMultipleDataPost(IntermediateTemplateData intermediateTemplateData);
    
    public boolean handleMultipleDataPostProcessFinished(IntermediateTemplateData intermediateTemplateData);
    
    public boolean processSingleDataPost(IntermediateTemplateData intermediateTemplateData);
    
    public boolean handlePostProcessFinished(IntermediateTemplateData intermediateTemplateData);
    
    
    
}
