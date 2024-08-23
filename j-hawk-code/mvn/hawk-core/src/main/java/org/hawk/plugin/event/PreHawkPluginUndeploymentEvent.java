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
package org.hawk.plugin.event;

import org.common.di.AppContainer;
import org.commons.event.IHawkEvent;
 import org.commons.reflection.Create;

/**
 *
 * @author Manoranjan Sahu
 */
   
   
public class PreHawkPluginUndeploymentEvent extends DefaultHawkPluginEvent {

    public PreHawkPluginUndeploymentEvent() {
        super();
    }

    @Create
    public IHawkEvent create() {
        return AppContainer.getInstance().getBean(this.getClass());
    }
}