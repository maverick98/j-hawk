/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.commons.event;

/**
 *
 * @author manosahu
 */
public class HawkEventPayload {

    private IHawkEvent event;
    private Object payload;

    public IHawkEvent getEvent() {
        return event;
    }

    public void setEvent(IHawkEvent event) {
        this.event = event;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

  

}
