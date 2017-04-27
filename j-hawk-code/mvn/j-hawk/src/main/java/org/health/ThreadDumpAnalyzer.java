/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.health;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author manosahu
 */
public class ThreadDumpAnalyzer {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void startScheduleTask() {
        
        final ScheduledFuture<?> taskHandle = scheduler.scheduleAtFixedRate(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            loadThreadDumps();
                        } catch (Exception ex) {
                            ex.printStackTrace(); //or loggger would be better
                        }
                    }
                }, 0, 2000, TimeUnit.MILLISECONDS);
    }

    private void loadThreadDumps() {
        Map<Thread, StackTraceElement[]> map  = Thread.getAllStackTraces();
        //System.out.println(map);
        this.showMap(map);
    }
    private void showMap(Map<Thread, StackTraceElement[]> map){
        for(Entry<Thread, StackTraceElement[]> entry : map.entrySet()){
            Thread t = entry.getKey();
            StackTraceElement[] stackTraces = entry.getValue();
            for(StackTraceElement ste : stackTraces){
                if(t.getName().equals("main")){
                   // System.out.println( ste.toString());
                }
            }
        }
      //  System.out.println("##########");
    }

}
