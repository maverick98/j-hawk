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
