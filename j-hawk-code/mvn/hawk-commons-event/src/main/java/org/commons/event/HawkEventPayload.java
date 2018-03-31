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
public class HawkEventPayload<T> {

    private IHawkEvent event;
    private T payload;

    public IHawkEvent getEvent() {
        return event;
    }

    public void setEvent(IHawkEvent event) {
        this.event = event;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

  

}
