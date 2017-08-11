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
