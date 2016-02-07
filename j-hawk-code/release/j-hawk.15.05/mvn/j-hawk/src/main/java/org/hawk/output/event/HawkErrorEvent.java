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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.hawk.output.event;

import org.common.di.AppContainer;
import org.commons.event.IHawkEvent;
 import org.commons.reflection.Create;

/**
 *
 * @author manosahu
 */
//@Component(HAWKERROREVENT)
//@Qualifier(DEFAULTQUALIFIER)
public class HawkErrorEvent extends DefaultHawkOutputEvent {

    public HawkErrorEvent() {

    }

    @Create
    public IHawkEvent create() {
        return AppContainer.getInstance().getBean(this.getClass());
    }
}
