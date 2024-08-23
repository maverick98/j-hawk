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

package org.hawk.file;

import java.io.File;
import org.apache.ant.compress.taskdefs.Unzip;

import org.commons.string.StringUtil;

/**
 *
 * @author Manoranjan Sahu
 */
public class ZipUtil {

    public static boolean unzip(String zipFileStr, String dstStr) throws Exception{
        
        if(StringUtil.isNullOrEmpty(zipFileStr)){
            throw new Exception("invalid zip file");
        }
        if(StringUtil.isNullOrEmpty(dstStr)){
            throw new Exception("invalid dir");
        }
        File dstDir = new File(dstStr);
       
        Unzip unzip = new Unzip();
        File zipFile = new File(zipFileStr);
        unzip.setSrc(zipFile);
        unzip.setDest(dstDir);
        unzip.execute();
        return true;
    }
}
