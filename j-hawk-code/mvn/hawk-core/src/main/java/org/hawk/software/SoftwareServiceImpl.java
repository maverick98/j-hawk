/*
 * This file is part of j-hawk
 * CopyLeft (C) BigBang<->BigCrunch Manoranjan Sahu, All Rights are left.
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

package org.hawk.software;


import org.hawk.xml.XMLUtil;

/**
 *
 * @author manosahu
 */
   
   
public class SoftwareServiceImpl implements ISoftwareService {

    @Override
    public Software getSoftware() throws Exception {

        return XMLUtil.unmarshal(Thread.currentThread().getContextClassLoader().getResource("release/software.xml").openStream(), Software.class);
    }

    @Override
    public boolean isHigherVersion(Software software1, Software software2) throws Exception {
        
        return software1.getVersion().compareTo(software2.getVersion()) > 0;
    }

    @Override
    public boolean isLowerVersion(Software software1, Software software2) throws Exception {
        return software1.getVersion().compareTo(software2.getVersion()) < 0;
    }

    @Override
    public boolean isSameVersion(Software software1, Software software2) throws Exception {

        return software1.getVersion().compareTo(software2.getVersion()) == 0;
    }

}
