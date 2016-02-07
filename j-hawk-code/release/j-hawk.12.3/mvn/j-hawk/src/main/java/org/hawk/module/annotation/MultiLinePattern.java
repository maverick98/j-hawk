/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.hawk.module.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author manoranjan
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MultiLinePattern {

    String startDelim();
    String endDelim();
    String startPattern();
    String endPattern();
}
