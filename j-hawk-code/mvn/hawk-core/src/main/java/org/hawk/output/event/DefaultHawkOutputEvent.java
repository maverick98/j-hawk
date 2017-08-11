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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.hawk.output.event;

import java.util.Set;
import java.util.TreeSet;
import org.commons.event.AbstractHawkEvent;
import org.commons.event.HawkEventPayload;
import org.commons.event.callback.IHawkEventCallback;
import org.commons.event.exception.HawkEventException;
import org.hawk.logger.HawkLogger;

/**
 *
 * @author manosahu
 */
public class DefaultHawkOutputEvent extends AbstractHawkEvent{
    private static final HawkLogger logger = HawkLogger.getLogger(DefaultHawkOutputEvent.class.getName());

    private final Set<IHawkEventCallback> callbacks = new TreeSet<>();

    @Override
    public Boolean notifyCallbacks(HawkEventPayload hawkEventPayload) throws HawkEventException {
        hawkEventPayload.setEvent(this);
        for(IHawkEventCallback hawkEventCallback : this.getCallbacks()){
            this.notifyCallback(hawkEventCallback, hawkEventPayload);
        }
        return true;
    }

    public Set<IHawkEventCallback> getCallbacks() {
        return callbacks;
    }
    

    @Override
    public Boolean register(IHawkEventCallback hawkEventCallback) throws HawkEventException {
        return this.getCallbacks().add(hawkEventCallback);
    }

    @Override
    public Boolean deRegister(IHawkEventCallback hawkEventCallback) throws HawkEventException {
        return this.getCallbacks().remove(hawkEventCallback);
    }

}
