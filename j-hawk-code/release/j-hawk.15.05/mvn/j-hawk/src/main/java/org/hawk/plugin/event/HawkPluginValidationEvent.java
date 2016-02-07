/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hawk.plugin.event;

import org.common.di.AppContainer;
import org.commons.event.IHawkEvent;
 import org.commons.reflection.Create;

/**
 *
 * @author manosahu
 */
//@Component(PLUGINVALIDATIONEVENT)
//@Qualifier(DEFAULTQUALIFIER)
public class HawkPluginValidationEvent extends DefaultHawkPluginEvent {

   

    public HawkPluginValidationEvent() {

    }

    @Create
    public IHawkEvent create() {
        return AppContainer.getInstance().getBean(this.getClass());
    }
}
