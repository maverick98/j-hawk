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
package org.hawk.html.java;

import java.io.File;

import org.hawk.html.java.annotation.HTMLTableColumn;

/**
 *
 * @author Manoranjan Sahu
 */
public class HawkPluginRepository extends HtmlJavaBean {

    private static final String testResultTemplate = "conf/hawkPluginRepository.html";
    private static final String resulHtmlFile = "hawkPluginRepository.html";
    private String plugin;
    private String version;
    private String releaseDate;
    private String downloadURL;

    @HTMLTableColumn(sequence = 1, header = "Plugin", setter = "setPlugin")
    public String getPlugin() {
        return plugin;
    }

    public void setPlugin(String plugin) {
        this.plugin = plugin;
    }

    @HTMLTableColumn(sequence = 2, header = "Version", setter = "setVersion")
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @HTMLTableColumn(sequence = 3, header = "Release Date", setter = "setReleaseDate")
    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDownloadURL() {
        return downloadURL;
    }

    @HTMLTableColumn(sequence = 4, header = "downloadURL", setter = "setDownloadURL")
    public void setDownloadURL(String downloadURL) {
        this.downloadURL = downloadURL;
    }

    @Override
    public String toString() {
        return "HawkPluginRepository{" + "plugin=" + plugin + ", version=" + version + ", releaseDate=" + releaseDate + ", downloadURL=" + downloadURL + '}';
    }
    
    public static void main(String args[]) throws Exception {

        IHtmlJavaService htmlJavaService = new DefaultHtmlJavaServiceImpl();
        Object obj = htmlJavaService.toJavaList(new File(resulHtmlFile), HawkPluginRepository.class);
        System.out.println(obj);
    }
}
