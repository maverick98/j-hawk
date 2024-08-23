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

package org.commons.file;

import java.io.File;
import org.apache.ant.compress.taskdefs.Unzip;
import org.commons.string.StringUtil;



/**
 *
 * @author Manoranjan Sahu
 */
public class ZipUtil {

    public static boolean unzip(String zipFileStr, String dstStr) throws ZipException{
        
        if(StringUtil.isNullOrEmpty(zipFileStr)){
            throw new ZipException("invalid zip file");
        }
        if(StringUtil.isNullOrEmpty(dstStr)){
            throw new ZipException("invalid dir");
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
