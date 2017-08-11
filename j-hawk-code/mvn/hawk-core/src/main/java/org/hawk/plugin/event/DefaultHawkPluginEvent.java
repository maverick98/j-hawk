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
package org.hawk.plugin.event;

import java.util.Set;
import java.util.TreeSet;
import org.commons.event.AbstractHawkEvent;
import org.commons.event.HawkEventPayload;
import org.commons.event.callback.IHawkEventCallback;
import org.commons.event.exception.HawkEventException;
import org.hawk.logger.HawkLogger;
import org.hawk.plugin.event.callback.IHawkPluginCallbackCore;
import org.hawk.plugin.event.callback.IHawkPluginCallbackPlugin;

/**
 *
 * @author Manoranjan Sahu
 */
   
   
public class DefaultHawkPluginEvent extends AbstractHawkEvent {

    private static final HawkLogger logger = HawkLogger.getLogger(DefaultHawkPluginEvent.class.getName());

    private final Set<IHawkPluginCallbackCore> coreCallbacks = new TreeSet<>();

    private final Set<IHawkPluginCallbackPlugin> pluginCallbacks = new TreeSet<>();

    public Set<IHawkPluginCallbackCore> getCoreCallbacks() {
        return coreCallbacks;
    }

    public Set<IHawkPluginCallbackPlugin> getPluginCallbacks() {
        return pluginCallbacks;
    }

    public Boolean isCoreCallbacksEmpty() {
        return this.getCoreCallbacks().isEmpty();
    }

    public Boolean isPluginCallbacksEmpty() {
        return this.getPluginCallbacks().isEmpty();
    }

    public Integer getCoreCallbacksSize() {
        return this.getCoreCallbacks().size();
    }

    public Integer getPluginCallbacksSize() {
        return this.getPluginCallbacks().size();
    }

    public DefaultHawkPluginEvent() {
        super();
    }

    @Override
    public Boolean register(IHawkEventCallback hawkPluginCallback) throws HawkEventException {
        boolean registered;
        if (hawkPluginCallback.getSequence() == null) {
            throw new HawkEventException("Illegal argument");
        }
        if (hawkPluginCallback instanceof IHawkPluginCallbackCore) {
            this.getCoreCallbacks().add((IHawkPluginCallbackCore) hawkPluginCallback);
        } else if (hawkPluginCallback instanceof IHawkPluginCallbackPlugin) {
            this.getPluginCallbacks().add((IHawkPluginCallbackPlugin) hawkPluginCallback);
        }
        registered = true;
        return registered;
    }

    @Override
    public Boolean deRegister(IHawkEventCallback hawkPluginCallback) throws HawkEventException {
        boolean isDeRegisterd;
        if (hawkPluginCallback == null || hawkPluginCallback.getSequence() == null) {
            throw new HawkEventException("Illegal argument");
        }
        Object value = null;
        if (hawkPluginCallback instanceof IHawkPluginCallbackCore) {
            value = this.getCoreCallbacks().remove((IHawkPluginCallbackCore) hawkPluginCallback);
        } else if (hawkPluginCallback instanceof IHawkPluginCallbackPlugin) {
            value = this.getPluginCallbacks().remove((IHawkPluginCallbackPlugin) hawkPluginCallback);
        }

        if (value != null) {
            isDeRegisterd = true;
        } else {
            isDeRegisterd = false;
        }
        return isDeRegisterd;
    }

   

    private Boolean notifyCoreCallbacks(HawkEventPayload hawkEventPayload) throws HawkEventException {

        for (IHawkPluginCallbackCore hawkPluginCallbackCore : this.getCoreCallbacks()) {
            hawkEventPayload.setEvent(this);
            this.notifyCallback(hawkPluginCallbackCore, hawkEventPayload);
        }
        return true;
    }

    private Boolean notifyPluginCallbacks(HawkEventPayload hawkEventPayload) throws HawkEventException {

        for (IHawkPluginCallbackPlugin hawkPluginCallbackPlugin : this.getPluginCallbacks()) {
            hawkEventPayload.setEvent(this);
            this.notifyCallback(hawkPluginCallbackPlugin, hawkEventPayload);
        }
        return true;
    }

   
    @Override
    public Boolean notifyCallbacks(HawkEventPayload hawkEventPayload) throws HawkEventException {
        Boolean coreCallbacksNotified = this.notifyCoreCallbacks(hawkEventPayload);
        Boolean pluginCallbacksNotified = this.notifyPluginCallbacks(hawkEventPayload);
        Boolean result = coreCallbacksNotified && pluginCallbacksNotified;
        return result;
    }
}
