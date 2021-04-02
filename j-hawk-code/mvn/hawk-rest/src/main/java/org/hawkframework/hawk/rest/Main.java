
package org.hawkframework.hawk.rest;

/**
 *
 * @author reemeeka
 */
import org.hawkframework.hawk.rest.util.HawkRestConfigHelper;
import org.hawk.logger.HawkLogger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
 private static final HawkLogger logger = HawkLogger.getLogger(Main.class.getName());
    public static void main(String[] args) {
        if(HawkRestConfigHelper.configure()){
            SpringApplication.run(Main.class, args);
            logger.info("HawkRest started successfully!!!");
        }else{
            logger.error("HawkRest could not be started");
        }
        
    }
}
