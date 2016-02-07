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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hawk.executor.command.plugin.available;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import static org.hawk.constant.HawkConstant.FORMATTER;
import org.hawk.html.java.HtmlJavaBean;
import org.hawk.html.java.annotation.HTMLTableColumn;
import org.commons.string.StringUtil;

/**
 *
 * @author manosahu
 */
public class AvailablePluginHtmlJavaBean extends HtmlJavaBean {

    private String plugin;
    private String version;
    private Date releaseDate;
    private String path;
    private String author;

    
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

    public void setReleaseDate(String formatterStartDate) {
        if (!StringUtil.isNullOrEmpty(formatterStartDate)) {
            SimpleDateFormat formatter = new SimpleDateFormat(FORMATTER);
            try {

                this.setReleaseDate(formatter.parse(formatterStartDate));
            } catch (ParseException ex) {
                //logger.error(ex);
            }
        }
    }

    @HTMLTableColumn(sequence = 3, header = "Release Date", setter = "setReleaseDate")
    public String getFormattedReleaseDate() {
        SimpleDateFormat formatter = new SimpleDateFormat(FORMATTER);
        return formatter.format(this.getReleaseDate());
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }
    @HTMLTableColumn(sequence = 4, header = "Path", setter = "setPath")
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
    @HTMLTableColumn(sequence = 5, header = "Author", setter = "setAuthor")
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
