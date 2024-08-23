/*
 * This file is part of impensa.
 *  
 *
 *  
 *    
 *
 */
package org.common.bean;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author manosahu
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Property {

    String name();
    boolean ignore() default false;
}
