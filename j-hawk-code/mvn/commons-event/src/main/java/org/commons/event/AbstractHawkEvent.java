/*
 * This file is part of j-hawk
 * CopyLeft (C) 2012-2013 Manoranjan Sahu, All Rights are left.
 * 

 */
package org.commons.event;

import org.commons.event.callback.IHawkEventCallback;
import org.commons.event.exception.HawkEventException;
import org.commons.logger.ILogger;
import org.commons.logger.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 *
 * @author manosahu
 */
public abstract class AbstractHawkEvent implements IHawkEvent {

    private static final ILogger logger = LoggerFactory.getLogger(AbstractHawkEvent.class.getName());

    @Override
    public Boolean dispatch(HawkEventPayload hawkEventPayload) throws HawkEventException {
        if (hawkEventPayload == null) {
            throw new HawkEventException("illegal args");
        }

        return this.notifyCallbacks(hawkEventPayload);
    }

    @Override
    public Boolean notifyCallback(IHawkEventCallback callback, HawkEventPayload hawkPluginPayload) throws HawkEventException {

        Class<? extends IHawkEvent> eventClazz = hawkPluginPayload.getEvent().getClass();

        Method[] methods = callback.getClass().getMethods();

        for (Method method : methods) {

            if (method.isAnnotationPresent(HawkEvent.class)) {

                HawkEvent hawkEventAnnotation = (HawkEvent) method.getAnnotation(HawkEvent.class);
                if (hawkEventAnnotation.type() == eventClazz) {
                    try {
                        method.invoke(callback, new Object[]{hawkPluginPayload});
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                        ex.printStackTrace();
                        throw new HawkEventException(ex);
                    }
                }
            }
        }

        return true;
    }

    @Override
    public abstract Boolean notifyCallbacks(HawkEventPayload hawkEventPayload) throws HawkEventException;
}
