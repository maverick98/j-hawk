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
