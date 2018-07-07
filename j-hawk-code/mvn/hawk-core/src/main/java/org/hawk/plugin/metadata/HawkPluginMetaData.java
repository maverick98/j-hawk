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
package org.hawk.plugin.metadata;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import org.commons.file.FileUtil;

import org.hawk.software.Contributor;
import org.hawk.software.Software;
import org.hawk.software.Version;
import org.hawk.xml.XMLUtil;

/**
 *
 * @author Manoranjan Sahu
 */

@XmlRootElement
public class HawkPluginMetaData implements Comparable<HawkPluginMetaData>{
    
    private Classpath classpath;
    private Software software;
    private String logPath;
    private boolean bgRun;
    private String downloadURL;
    private Configuration configuration;
    
    private List<PluginModuleClazz> pluginModuleClazz = new ArrayList<>();

    public List<PluginModuleClazz> getPluginModuleClazz() {
        return pluginModuleClazz;
    }

    public void setPluginModuleClazz(List<PluginModuleClazz> pluginModuleClazz) {
        this.pluginModuleClazz = pluginModuleClazz;
    }
    
    
    

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public String getDownloadURL() {
        return downloadURL;
    }

    public void setDownloadURL(String downloadURL) {
        this.downloadURL = downloadURL;
    }
    
    

   

    
    public boolean isBgRun() {
        return bgRun;
    }

    public void setBgRun(boolean bgRun) {
        this.bgRun = bgRun;
    }
    
    
    public String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }
    
    

    public Classpath getClasspath() {
        return classpath;
    }

    public void setClasspath(Classpath classpath) {
        this.classpath = classpath;
    }
    
    public Software getSoftware() {
        return software;
    }
    
    public void setSoftware(Software software) {
        this.software = software;
    }

   

   
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + (this.software != null ? this.software.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final HawkPluginMetaData other = (HawkPluginMetaData) obj;
        if (this.software != other.software && (this.software == null || !this.software.equals(other.software))) {
            return false;
        }
        return true;
    }
    
    
    public static void main(String args[]) throws Exception {
        TestPlugin hawkPluginMetaData = new TestPlugin();
        hawkPluginMetaData.setTabPath("adadfafds");
        hawkPluginMetaData.setBgRun(false);

        Software software = new Software();
        Version hawkVersion = new Version();
        hawkVersion.setVersion("13.10");
        Contributor contributor = new Contributor();
        contributor.setAuthorType(Contributor.ContributorTypeEnum.INDIVIDUAL);
        contributor.setName("Manoranjan Sahu");
        contributor.setEmail("abeautifulmind98@gmail.com");
        contributor.setWebsite("http://www.j-hawk.in");
        contributor.setContactNo("+91-9740319263");
        contributor.setAddress("#1211,2nd floor,22nd cross,3rd sector, HSR Layout, Bangalore-560102,Karnataka,India");
        software.setVersion(hawkVersion);
        software.setContributor(contributor);
        software.setCategory("Sports Analytics");
        software.setName("hawk-eye");
        software.setReleaseDate(new Date());
        software.setWebsite("http://www.j-hawk.in");
        software.setAbout("This is a software about analyzing cricket data collected from cricinfo.com");
        hawkPluginMetaData.setSoftware(software);
        Jar jar1  = new Jar();
        jar1.setDesc("aaaa");
        jar1.setPath("aaaaaa");
        Jar jar2  = new Jar();
        jar2.setDesc("adsfafd");
        jar2.setPath("dfdfdf");
        Classpath classpath = new Classpath();
        classpath.getJar().add(jar1);
        classpath.getJar().add(jar2);
        hawkPluginMetaData.setClasspath(classpath);
        hawkPluginMetaData.setBgRun(false);
        hawkPluginMetaData.setLogPath(".");
        XMLUtil.marshal(hawkPluginMetaData, "plugin.xml");
        System.out.println(FileUtil.readFile("plugin.xml"));

        System.out.println(XMLUtil.unmarshal("plugin.xml", TestPlugin.class).getSoftware().getAbout());




    }

    @Override
    public int compareTo(HawkPluginMetaData o) {
        return this.getSoftware().compareTo(o.getSoftware());
    }

    
}
