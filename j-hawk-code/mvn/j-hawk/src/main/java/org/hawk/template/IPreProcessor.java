/*
 * This file is part of j-hawk
 * CopyLeft (C) 2012-2013 Manoranjan Sahu, All Rights are left.
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
package org.hawk.template;

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
