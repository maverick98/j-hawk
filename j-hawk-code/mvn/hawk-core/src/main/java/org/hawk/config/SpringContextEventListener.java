/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hawk.config;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

/**
 *
 * @author reemeeka
 */
public class SpringContextEventListener {

    @EventListener({ContextRefreshedEvent.class})
    void contextRefreshedEvent() {
        System.out.println("a context refreshed event happened");
    }
}
