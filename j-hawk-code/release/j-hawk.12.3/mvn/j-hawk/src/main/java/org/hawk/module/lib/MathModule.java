

package org.hawk.module.lib;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.hawk.module.AbstractModule;
import org.hawk.module.annotation.Module;
import org.hawk.module.annotation.SubTask;

/**
 *
 * @version 1.0 4 Jan, 2011
 * @author msahu
 */
@Module(name="MathModule")
public class MathModule extends AbstractModule{

     @Override
    public String toString() {
        return this.getName();
    }
    public String getName() {
        return "MathModule";
    }
    public boolean startUp() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean reset() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @SubTask(name="sqrt",sequence = 1,ignoreException=false,hawkParam="var number")
    public String squareRoot(Object ... args) throws Exception{
        String var = args[0].toString();
        double number =  Double.parseDouble(var);
        return ""+Math.sqrt(number);
    }

    @SubTask(name="sin",sequence = 1,ignoreException=false,hawkParam="var number")
    public String sin(Object ... args) throws Exception{
        String var = args[0].toString();
        double number =  Double.parseDouble(var);
        return ""+Math.sin(number);
    }
    @SubTask(name="sinh",sequence = 1,ignoreException=false,hawkParam="var number")
    public String sinh(Object ... args) throws Exception{
        String var = args[0].toString();
        double number =  Double.parseDouble(var);
        return ""+Math.sinh(number);
    }
    @SubTask(name="cos",sequence = 1,ignoreException=false,hawkParam="var number")
    public String cos(Object ... args) throws Exception{
        String var = args[0].toString();
        double number =  Double.parseDouble(var);
        return ""+Math.cos(number);
    }
    @SubTask(name="cosh",sequence = 1,ignoreException=false,hawkParam="var number")
    public String cosh(Object ... args) throws Exception{
        String var = args[0].toString();
        double number =  Double.parseDouble(var);
        return ""+Math.cosh(number);
    }
    @SubTask(name="exp",sequence = 1,ignoreException=false,hawkParam="var number")
    public String exp(Object ... args) throws Exception{
        String var = args[0].toString();
        double number =  Double.parseDouble(var);
        return ""+Math.exp(number);
    }
    @SubTask(name="asin",sequence = 1,ignoreException=false,hawkParam="var number")
    public String aSin(Object ... args) throws Exception{
        String var = args[0].toString();
        double number =  Double.parseDouble(var);
        return ""+Math.asin(number);
    }
    @SubTask(name="acos",sequence = 1,ignoreException=false,hawkParam="var number")
    public String aCos(Object ... args) throws Exception{
        String var = args[0].toString();
        double number =  Double.parseDouble(var);
        return ""+Math.acos(number);
    }
    @SubTask(name="atan",sequence = 1,ignoreException=false,hawkParam="var number")
    public String aTan(Object ... args) throws Exception{
        String var = args[0].toString();
        double number =  Double.parseDouble(var);
        return ""+Math.atan(number);
    }
    @SubTask(name="abs",sequence = 1,ignoreException=false,hawkParam="var number")
    public String abs(Object ... args) throws Exception{
        String var = args[0].toString();
        double number =  Double.parseDouble(var);
        return ""+Math.abs(number);
    }
    @SubTask(name="ceil",sequence = 1,ignoreException=false,hawkParam="var number")
    public String ceil(Object ... args) throws Exception{
        String var = args[0].toString();
        double number =  Double.parseDouble(var);
        return ""+Math.ceil(number);
    }
    @SubTask(name="floor",sequence = 1,ignoreException=false,hawkParam="var number")
    public String floor(Object ... args) throws Exception{
        String var = args[0].toString();
        double number =  Double.parseDouble(var);
        return ""+Math.floor(number);
    }
    @SubTask(name="log",sequence = 1,ignoreException=false,hawkParam="var number")
    public String log(Object ... args) throws Exception{
        String var = args[0].toString();
        double number =  Double.parseDouble(var);
        return ""+Math.log(number);
    }

    @SubTask(name="pow",sequence = 1,ignoreException=false,hawkParam="var number")
    public String pow(Object ... args) throws Exception{
        String numberStr = args[0].toString();
        double number =  Double.parseDouble(numberStr);
        String powerStr = args[1].toString();
        double power =  Double.parseDouble(powerStr);
        
        return ""+Math.pow(number,power);
    }

    @SubTask(name="scale",sequence = 1,ignoreException=false,hawkParam="var number , var scale , var roundingMode")
    public String scale(Object ... args) throws Exception{
        String numStr = args[0].toString();
        BigDecimal number = new BigDecimal(numStr);
        int scale = Integer.parseInt(args[1].toString());
        String roundingMode = args[2].toString();
        if("halfup".equals(roundingMode)){
            number = number.setScale(scale, RoundingMode.HALF_UP);
        }
        if("halfdown".equals(roundingMode)){
            number = number.setScale(scale, RoundingMode.HALF_DOWN);
        }
        if("halfeven".equals(roundingMode)){
            number = number.setScale(scale, RoundingMode.HALF_EVEN);
        }
        return number.toString();
    }


}