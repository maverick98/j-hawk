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

package org.hawk.output.event.callback;

import org.commons.event.HawkEvent;
import org.commons.event.HawkEventPayload;
import org.commons.event.callback.IHawkEventCallback;
import org.hawk.output.event.HawkErrorEvent;
import org.hawk.output.event.HawkIOException;
import org.hawk.output.event.HawkOutputEvent;

/**
 *
 * @author manosahu
 */
   
   
public class HawkIOEventCallback implements IHawkEventCallback {

    @Override
    public Integer getSequence() {
        return 1;
    }

    @Override
    public int compareTo(IHawkEventCallback o) {
        return this.getSequence().compareTo(o.getSequence());
    }

    @HawkEvent(type = HawkOutputEvent.class)
    public boolean onHawkOutput(HawkEventPayload hawkPluginPayload) throws HawkIOException {
        System.out.println("Recvd hawkoutput event {"+hawkPluginPayload.getPayload()+"}");
        return true;
    }
    
    @HawkEvent(type = HawkErrorEvent.class)
    public boolean onHawkError(HawkEventPayload hawkPluginPayload) throws HawkIOException {
        System.out.println("Recvd hawkerror event");
        return true;
    }

}
