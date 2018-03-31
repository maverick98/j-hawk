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
package org.commons.event.callback;

import org.commons.api.API;
import org.commons.event.HawkEventPayload;
import org.commons.event.IHawkEvent;
import org.commons.event.exception.HawkEventException;
import org.commons.logger.ILogger;
import org.commons.logger.LoggerFactory;
import org.commons.reflection.ClazzUtil;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Manoranjan Sahu
 */
@API(since = "13.03", deprecatedSince = "14.01")
public class HawkEventCallbackRegistry implements IHawkEventCallbackRegistry {

    private static final ILogger logger = LoggerFactory.getLogger(HawkEventCallbackRegistry.class.getName());
    private final Map<Class<? extends IHawkEvent>, IHawkEvent> eventRegistry = new HashMap<>();

    @Override
    public boolean register(Class< ? extends IHawkEvent> pluginEventClazz, IHawkEventCallback hawkPluginCallback) throws HawkEventException {
        boolean isRegistered = false;

        if (pluginEventClazz != null && hawkPluginCallback != null && hawkPluginCallback.getSequence() != null) {
            IHawkEvent pluginEvent = eventRegistry.get(pluginEventClazz);
            if (pluginEvent == null) {

                try {
                    pluginEvent = ClazzUtil.instantiateFromSpring(pluginEventClazz, pluginEventClazz);
                } catch (Exception ex) {
                    logger.error(ex);
                    throw new HawkEventException(ex);
                }

            }
            if (pluginEvent != null) {
                isRegistered = pluginEvent.register(hawkPluginCallback);

                eventRegistry.put(pluginEventClazz, pluginEvent);
            }

        }
        return isRegistered;
    }

    @Override
    public boolean deRegister(Class<? extends IHawkEvent> pluginEventClazz, IHawkEventCallback hawkPluginCallback) throws HawkEventException {
        boolean isDeRegistered = false;
        if (pluginEventClazz != null && hawkPluginCallback != null && hawkPluginCallback.getSequence() != null) {
            IHawkEvent pluginEvent = eventRegistry.get(pluginEventClazz);
            isDeRegistered = pluginEvent.deRegister(hawkPluginCallback);

        }
        return isDeRegistered;
    }

    @Override
    public boolean dispatch(Class<? extends IHawkEvent> pluginEventClazz, HawkEventPayload hawkEventPayload) throws HawkEventException {
        boolean allExecuted = false;
        if (pluginEventClazz != null && hawkEventPayload != null) {
            IHawkEvent pluginEvent = eventRegistry.get(pluginEventClazz);
            if (pluginEvent != null) {
                allExecuted = pluginEvent.dispatch(hawkEventPayload);
            }
        }

        return allExecuted;
    }
}
