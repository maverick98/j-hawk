/*
 * This file is part of j-hawk
 *  
 *

 * 
 *
 * 
 */
package org.commons.event;

import org.commons.event.callback.IHawkEventCallback;
import org.commons.event.exception.HawkEventException;


/**
 *
 * @author user
 */
public interface IHawkEvent {

    public Boolean register(IHawkEventCallback hawkEventCallback) throws HawkEventException;

    public Boolean deRegister(IHawkEventCallback hawkEventCallback) throws HawkEventException;

    public Boolean dispatch(HawkEventPayload hawkEventPayload) throws HawkEventException;

    public Boolean notifyCallbacks(HawkEventPayload hawkEventPayload) throws HawkEventException;
    
    public Boolean notifyCallback(IHawkEventCallback hawkEventCallback, HawkEventPayload hawkPluginPayload) throws HawkEventException;

}
