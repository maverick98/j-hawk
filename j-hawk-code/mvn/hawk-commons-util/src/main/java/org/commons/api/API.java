/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.commons.api;

/**
 *
 * @author manosahu
 */
public @interface API {
    String since();
    String deprecatedSince() default "";
    
}
