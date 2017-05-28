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
package org.hawk.plugin;

import java.io.File;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;
import org.hawk.html.java.HtmlJavaBean;
import org.hawk.lang.singleline.pattern.LinePattern;
import org.hawk.logger.HawkLogger;
import org.hawk.pattern.PatternMatcher;
import static org.hawk.plugin.constant.HawkPluginConstant.METADATAXML;
import static org.hawk.plugin.constant.HawkPluginConstant.PLUGINDIR;
import static org.hawk.plugin.constant.HawkPluginConstant.PLUGINEXTENSIONS;
import org.hawk.plugin.exception.HawkPluginException;
import org.hawk.plugin.metadata.HawkPluginMetaData;
import org.commons.string.StringUtil;

/**
 *
 * @author Manoranjan Sahu
 */
public class HawkPlugin extends HtmlJavaBean implements Comparable<HawkPlugin> {

    private static final HawkLogger logger = HawkLogger.getLogger(HawkPlugin.class.getName());
    private String pluginArchive; // set in ctor
    private HawkPluginMetaData pluginMetaData;
    private String pluginRootDir;
    private String metadataXML = METADATAXML;
    private boolean extractedJustNow = false;
    private Object config;
    private String name;

    public void setName(String name) {
        this.name = name;
    }
    private String extension;
    private Boolean validated = false;
    private Boolean extracted = false;
    private Boolean loaded = false;
    private Boolean configLoaded = false;

    private static final Pattern PLG_REVERSE_PATTERN = Pattern.compile("(glp)\\.(.*)");
    private static final Pattern ZIP_REVERSE_PATTERN = Pattern.compile("(piz)\\.(.*)");
   
    

    public Boolean getConfigLoaded() {
        return configLoaded;
    }

    public void setConfigLoaded(Boolean configLoaded) {
        this.configLoaded = configLoaded;
    }

    public Boolean getLoaded() {
        return loaded;
    }

    public void setLoaded(Boolean loaded) {
        this.loaded = loaded;
    }

    public Boolean getExtracted() {
        return extracted;
    }

    public void setExtracted(Boolean extracted) {
        this.extracted = extracted;
    }

    public Boolean getValidated() {
        return validated;
    }

    public void setValidated(Boolean validated) {
        this.validated = validated;
    }
   
    public String getAbsolutePath(String path) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getPluginHome());
        sb.append(File.separator);
        sb.append(path);
        return sb.toString();
    }

    public Object getConfig() {
        return config;
    }

    public void setConfig(Object config) {
        this.config = config;
    }
   
    public HawkPlugin(String pluginArchive) throws HawkPluginException {
        this(pluginArchive, PLUGINDIR);
    }

    public HawkPlugin(String pluginArchiveStr, String pluginRootDir) throws HawkPluginException {

        this.pluginArchive = pluginArchiveStr;
        this.pluginRootDir = pluginRootDir;
        this.extractInfo();
    }

    public String getMetadataXML() {
        return metadataXML;
    }

    public void setMetadataXML(String metadataXML) {
        this.metadataXML = metadataXML;
    }

    public String getMetaDataXMLPath() {
        return this.getAbsolutePath(this.getMetadataXML());
    }

    public boolean metaDataXMLExists() {
        return this.fileExists(this.getMetaDataXMLPath());
    }

    private boolean fileExists(String fileStr) {
        if (StringUtil.isNullOrEmpty(fileStr)) {
            return false;
        }
        File f = new File(fileStr);
        return f.exists();
    }

    public HawkPluginMetaData getPluginMetaData() {
        return pluginMetaData;
    }

    public void setPluginMetaData(HawkPluginMetaData pluginMetaData) {
        this.pluginMetaData = pluginMetaData;
    }

    public String getPluginRootDir() {
        return pluginRootDir;
    }

    public void setPluginRootDir(String pluginRootDir) {
        this.pluginRootDir = pluginRootDir;
    }

    public String getPluginHome() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getPluginRootDir());
        sb.append(File.separator);
        sb.append(this.getName());
        return sb.toString();
    }

    public boolean isExtractedJustNow() {
        return extractedJustNow;
    }

    public void setExtractedJustNow(boolean extractedJustNow) {
        this.extractedJustNow = extractedJustNow;
    }

    public boolean isExtracted() {
        return this.isExtractedJustNow() ? true : this.metaDataXMLExists();
    }
    private String reverse(String str){
        
        char [] strArr = str.toCharArray();
        for(int i = 0; i < strArr.length/2; i++){
            char c = strArr[i] ;
            strArr[i] = strArr[strArr.length-1 -i];
            strArr[strArr.length-1 -i] = c;
            
        }
        return new String(strArr);
    }
    public boolean extractInfo(){
        LinePattern plgLinePattern = new LinePattern();
        plgLinePattern.setSequence(1);
        plgLinePattern.setPattern(PLG_REVERSE_PATTERN);
        
        LinePattern zipLinePattern = new LinePattern();
        zipLinePattern.setSequence(2);
        zipLinePattern.setPattern(ZIP_REVERSE_PATTERN);
        
        Set<LinePattern> all = new TreeSet<>();
        all.add(plgLinePattern);
        all.add(zipLinePattern);
        
        Map<Integer,String> map = PatternMatcher.match(all, this.reverse(this.getPluginArchive()));
        
        String ext = this.reverse(map.get(1));
        this.setExtension(ext);
        String name = this.reverse(map.get(2));
        this.setName(name);
        return true;
    }
   
    
    public String getName() {
        return name;
    }
    

    public Integer getExtLength() {
        return this.getExtension().length();
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getPluginArchive() {
        return pluginArchive;
    }

    public void setPluginArchive(String pluginArchiveStr) {

        this.pluginArchive = pluginArchiveStr;
    }

    public boolean validate() throws HawkPluginException {
        boolean isValid;
        if (StringUtil.isNullOrEmpty(pluginArchive)) {
            throw new HawkPluginException("invalid hawk plugin");
        }
        isValid = this.isValidExtension();
        return isValid;
    }

    private boolean isValidExtension() {
        boolean isValid = false;
        for (String ext : PLUGINEXTENSIONS) {
            isValid = this.getPluginArchive().endsWith(ext);
            if (isValid) {
                break;
            }
        }
        return isValid;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.getPluginArchive() != null ? this.getPluginArchive().hashCode() : 0);
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
        final HawkPlugin other = (HawkPlugin) obj;
        if ((this.pluginArchive == null) ? (other.pluginArchive != null) : !this.pluginArchive.equals(other.pluginArchive)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return '{' + pluginArchive + '}';
    }

    @Override
    public int compareTo(HawkPlugin o) {
        return this.getPluginArchive().compareTo(o.getPluginArchive());
    }
}
