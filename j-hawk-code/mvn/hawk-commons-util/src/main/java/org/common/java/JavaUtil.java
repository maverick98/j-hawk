package org.common.java;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author reemeeka
 */
public class JavaUtil {

    public static boolean isNull(Object object) {
        return object == null;
    }

    public static boolean isNull(Object... objects) {
       
        for (Object object : objects) {
            if (!isNull(object)) {
                return false;
            }
        }
        return true;
    }

}
