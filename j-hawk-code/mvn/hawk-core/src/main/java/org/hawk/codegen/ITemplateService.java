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
public interface ITemplateService {

    public String toFile(FileTemplateJavaBean fileJavaBean) throws Exception;

    public String preProcess(FileTemplateJavaBean fileJavaBean) throws Exception;

    public String postProcess(FileTemplateJavaBean fileJavaBean) throws Exception ;
}
