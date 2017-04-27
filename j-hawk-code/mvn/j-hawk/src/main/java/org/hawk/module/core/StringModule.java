/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hawk.module.core;

import org.common.di.ScanMe;
import org.hawk.module.annotation.SubTask;

/**
 *
 * @author manosahu
 */
@ScanMe(true)
public class StringModule extends HawkCoreModule {

    @Override
    public String toString() {
        return this.getName();
    }

    public String getName() {
        return "StringModule";
    }

    @Override
    public boolean startUp() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean reset() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @SubTask(name = "length", sequence = 1, ignoreException = false, hawkParam = "var string")
    public int length(Object... args) throws Exception {
        String var = args[0].toString();
        return var.length();
    }

    @SubTask(name = "charAt", sequence = 1, ignoreException = false, hawkParam = "var string")
    public String charAt(Object... args) throws Exception {
        String var = args[0].toString();
        return String.valueOf(var.charAt(Integer.parseInt(args[1].toString())));
    }
}
