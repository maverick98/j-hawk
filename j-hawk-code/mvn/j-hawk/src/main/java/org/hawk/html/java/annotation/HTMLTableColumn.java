/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hawk.html.java.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author manosahu
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface HTMLTableColumn {
    
    int sequence();
    String header();
    String preData() default "";
    String postData() default "";
    String setter() default "";
    
}
