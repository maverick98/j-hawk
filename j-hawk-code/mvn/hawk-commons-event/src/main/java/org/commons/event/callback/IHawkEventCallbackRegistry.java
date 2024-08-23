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

package org.commons.event.callback;

import org.commons.event.HawkEventPayload;
import org.commons.event.IHawkEvent;
import org.commons.event.exception.HawkEventException;

/**
 *
 * @author user
 */
public interface IHawkEventCallbackRegistry {

    public boolean register( Class <? extends IHawkEvent> pluginEventClazz ,IHawkEventCallback hawkPluginCallback ) throws HawkEventException;
    
    public boolean deRegister( Class <? extends IHawkEvent> pluginEventClazz ,IHawkEventCallback hawkPluginCallback ) throws HawkEventException;
    
    public boolean dispatch(Class <? extends IHawkEvent> pluginEventClazz , HawkEventPayload hawkEventPayload) throws HawkEventException;

}
