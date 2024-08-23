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



package org.hawk.data.perf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @version 1.0 2 Jul, 2010
 * @author msahu
 */
public class HawkPerfUtil {


    public static List<HawkPerfData> fetchBoundaryPerfData(List<HawkPerfData> all){
        if(all == null || all.isEmpty()){
            return null;
        }

        List<HawkPerfData> result = new ArrayList<>();


        Collections.sort(all, (HawkPerfData o1, HawkPerfData o2) -> Long.valueOf(o1.getModuleStartTimeLong()).compareTo(Long.valueOf(o2.getModuleStartTimeLong())));
        result.add(all.get(0));
        result.add(all.get(all.size()-1));
        return result;
    }

    public static List<String> fetchBoundaryTime(List<HawkPerfData> all){

        List<String> result = new ArrayList<>();

        Collections.sort(all, (HawkPerfData o1, HawkPerfData o2) -> {
            long diff = o1.getModuleStartTimeLong()-o2.getModuleStartTimeLong();
            return (int)diff;
        });
        result.add(all.get(0).getModuleStartTime());
        result.add(all.get(all.size()-1).getModuleStartTime());
        return result;
    }
}




