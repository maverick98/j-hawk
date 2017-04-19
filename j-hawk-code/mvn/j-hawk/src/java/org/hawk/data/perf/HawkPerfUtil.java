/*
 * This file is part of j-hawk
 * CopyLeft (C) 2012-2013 Manoranjan Sahu, All Rights are left.
 *
 * 
 *
 * 1) If you modify a source file, make a comment in it containing your name and the date.
 * 2) If you distribute a modified version, you must do it under the GPL 2.
 * 3) Developers are encouraged but not required to notify the j-hawk maintainers at abeautifulmind98@gmail.com
 * when they make a useful addition. It would be nice if significant contributions could be merged into the main distribution.
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

        List<HawkPerfData> result = new ArrayList<HawkPerfData>();


        Collections.sort(all, new Comparator<HawkPerfData>() {

            @Override
            public int compare(HawkPerfData o1, HawkPerfData o2) {
               return  Long.valueOf(o1.getModuleStartTimeLong()).compareTo(Long.valueOf(o2.getModuleStartTimeLong()));
            }
        });
        result.add(all.get(0));
        result.add(all.get(all.size()-1));
        return result;
    }

    public static List<String> fetchBoundaryTime(List<HawkPerfData> all){

        List<String> result = new ArrayList<String>();

        Collections.sort(all, new Comparator<HawkPerfData>() {

            @Override
            public int compare(HawkPerfData o1, HawkPerfData o2) {
               long diff = o1.getModuleStartTimeLong()-o2.getModuleStartTimeLong();
               return (int)diff;
            }
        });
        result.add(all.get(0).getModuleStartTime());
        result.add(all.get(all.size()-1).getModuleStartTime());
        return result;
    }
}




