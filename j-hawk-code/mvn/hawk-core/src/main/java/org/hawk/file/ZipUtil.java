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
