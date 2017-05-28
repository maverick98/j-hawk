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
package org.hawk.executor.cache;

import org.common.di.AppContainer;

import org.commons.implementor.IVisitor;
import org.hawk.input.HawkInput;
import org.hawk.sequence.ISequenceProvider;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Manoranjan Sahu
 */
   
   
public class DefaultScriptCache implements IScriptCache {

    
    @Autowired(required = true)
       
    private ScriptCachingSequenceProvider scriptCachingSequenceProvider;
    @Autowired(required = true)
       
    private HawkInput hawkInput;

    public HawkInput getHawkInput() {
        return hawkInput;
    }

    public void setHawkInput(HawkInput hawkInput) {
        this.hawkInput = hawkInput;
    }
    
    

    public ScriptCachingSequenceProvider getCachingSequence() {
        return scriptCachingSequenceProvider;
    }

    public void setCachingSequence(ScriptCachingSequenceProvider cachingSequence) {
        this.scriptCachingSequenceProvider = cachingSequence;
    }

    @Override
    public Integer getSequence() {
        ISequenceProvider sequenceProvider = AppContainer.getInstance().getBean(ScriptCachingSequenceProvider.class);
        return sequenceProvider.getSequence(this.getClass());
    }

    @Override
    public boolean cache() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean reset() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IScriptCache create() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void accept(IVisitor visitor) throws Exception {
        visitor.visit(this);
    }
}
