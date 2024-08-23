/*
 * This file is part of j-hawk
 *  
 *
 * 
 *
 *  
 *  
 *  
 *  
 *
 * 
 * 
 *
 * 
 */
package org.hawk.module.core;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.common.di.AppContainer;
import org.hawk.module.annotation.SubTask;
 import org.commons.reflection.Create;
import org.common.di.ScanMe;

/**
 *
 * @version 1.0 4 Jan, 2011
 * @author msahu
 */
@ScanMe(true)
   
   

public class MathModule extends HawkCoreModule {

    @Override
    public String toString() {
        return this.getName();
    }

    public String getName() {
        return "MathModule";
    }

    @Override
    public boolean startUp() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean reset() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @SubTask(name = "sqrt", sequence = 1, ignoreException = false, hawkParam = "var number")
    public double squareRoot(Object... args) throws Exception {
        String var = args[0].toString();
        double number = Double.parseDouble(var);
        return  Math.sqrt(number);
    }

    @SubTask(name = "sin", sequence = 1, ignoreException = false, hawkParam = "var number")
    public double sin(Object... args) throws Exception {
        String var = args[0].toString();
        double number = Double.parseDouble(var);
        return  Math.sin(number);
    }

    @SubTask(name = "sinh", sequence = 1, ignoreException = false, hawkParam = "var number")
    public double sinh(Object... args) throws Exception {
        String var = args[0].toString();
        double number = Double.parseDouble(var);
        return   Math.sinh(number);
    }

    @SubTask(name = "cos", sequence = 1, ignoreException = false, hawkParam = "var number")
    public double cos(Object... args) throws Exception {
        String var = args[0].toString();
        double number = Double.parseDouble(var);
        return   Math.cos(number);
    }

    @SubTask(name = "cosh", sequence = 1, ignoreException = false, hawkParam = "var number")
    public double cosh(Object... args) throws Exception {
        String var = args[0].toString();
        double number = Double.parseDouble(var);
        return   Math.cosh(number);
    }

    @SubTask(name = "exp", sequence = 1, ignoreException = false, hawkParam = "var number")
    public double exp(Object... args) throws Exception {
        String var = args[0].toString();
        double number = Double.parseDouble(var);
        return   Math.exp(number);
    }

    @SubTask(name = "asin", sequence = 1, ignoreException = false, hawkParam = "var number")
    public double aSin(Object... args) throws Exception {
        String var = args[0].toString();
        double number = Double.parseDouble(var);
        return   Math.asin(number);
    }

    @SubTask(name = "acos", sequence = 1, ignoreException = false, hawkParam = "var number")
    public double aCos(Object... args) throws Exception {
        String var = args[0].toString();
        double number = Double.parseDouble(var);
        return   Math.acos(number);
    }

    @SubTask(name = "atan", sequence = 1, ignoreException = false, hawkParam = "var number")
    public double aTan(Object... args) throws Exception {
        String var = args[0].toString();
        double number = Double.parseDouble(var);
        return   Math.atan(number);
    }

    @SubTask(name = "abs", sequence = 1, ignoreException = false, hawkParam = "var number")
    public double abs(Object... args) throws Exception {
        String var = args[0].toString();
        double number = Double.parseDouble(var);
        return   Math.abs(number);
    }

    @SubTask(name = "ceil", sequence = 1, ignoreException = false, hawkParam = "var number")
    public double ceil(Object... args) throws Exception {
        String var = args[0].toString();
        double number = Double.parseDouble(var);
        return   Math.ceil(number);
    }

    @SubTask(name = "floor", sequence = 1, ignoreException = false, hawkParam = "var number")
    public double floor(Object... args) throws Exception {
        String var = args[0].toString();
        double number = Double.parseDouble(var);
        return   Math.floor(number);
    }

    @SubTask(name = "log", sequence = 1, ignoreException = false, hawkParam = "var number")
    public double log(Object... args) throws Exception {
        String var = args[0].toString();
        double number = Double.parseDouble(var);
        return   Math.log(number);
    }

    @SubTask(name = "pow", sequence = 1, ignoreException = false, hawkParam = "var number")
    public double pow(Object... args) throws Exception {
        String numberStr = args[0].toString();
        double number = Double.parseDouble(numberStr);
        String powerStr = args[1].toString();
        double power = Double.parseDouble(powerStr);

        return   Math.pow(number, power);
    }

    @SubTask(name = "scale", sequence = 1, ignoreException = false, hawkParam = "var number , var scale , var roundingMode")
    public double scale(Object... args) throws Exception {
        String numStr = args[0].toString();
        BigDecimal number = new BigDecimal(numStr);
        int scale = Integer.parseInt(args[1].toString());
        String roundingMode = args[2].toString();
        if ("halfup".equals(roundingMode)) {
            number = number.setScale(scale, RoundingMode.HALF_UP);
        }
        if ("halfdown".equals(roundingMode)) {
            number = number.setScale(scale, RoundingMode.HALF_DOWN);
        }
        if ("halfeven".equals(roundingMode)) {
            number = number.setScale(scale, RoundingMode.HALF_EVEN);
        }
        return number.doubleValue();
    }
    
    @Create
    public HawkCoreModule create() {
        return AppContainer.getInstance().getBean(this.getClass());
    }
   
}
