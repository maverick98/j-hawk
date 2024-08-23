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
