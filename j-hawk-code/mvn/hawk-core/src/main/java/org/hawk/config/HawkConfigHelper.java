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
package org.hawk.config;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import org.common.di.AppContainer;

import org.commons.implementor.InstanceVisitable;
import org.hawk.di.spring.SpringConfig;

/**
 *
 * @author Manoranjan Sahu
 */
public class HawkConfigHelper {

    public static void configure(boolean shouldScan) throws Exception {

        if (shouldScan) {
            //debatable ... but when was the last time,I advocated for software patent.
            long start2 = System.currentTimeMillis();
            AppContainer.getInstance().registerConfig(SpringConfig.class);
            
            AppContainer.getInstance().refreshAppCtx();
            long diff2 = System.currentTimeMillis() - start2;
            System.out.println("my spring took {" + diff2 + "}ms");

        }
        long start1 = System.currentTimeMillis();
        InstanceVisitable instanceVisitable = new InstanceVisitable();
        instanceVisitable.setClazz(DefaultHawkConfig.class);
        final Map<Integer, IHawkConfig> sortedConfigs = new TreeMap<>();

        try {
            new HawkConfigVisitor() {
                
                @Override
                public void onVisit(IHawkConfig hawkConfig) {
                    sortedConfigs.put(hawkConfig.getSequence(), hawkConfig);
                }
            }.visit(instanceVisitable);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception(ex);
        }
        long diff1 = System.currentTimeMillis() - start1;
        System.out.println("visting  took {" + diff1 + "}ms");
        for (Entry<Integer, IHawkConfig> entry : sortedConfigs.entrySet()) {
            long start = System.currentTimeMillis();
            IHawkConfig hawkConfig = entry.getValue();
            hawkConfig.configure();
            long diff = System.currentTimeMillis() - start;
            System.out.println("{" + hawkConfig + "} took {" + diff + "}ms");
        }
    }

    public static void configure() throws Exception {
        configure(true);
    }

}
