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

package org.hawk.executor.command.homepage;

import java.util.Map;
import java.util.Set;
import org.commons.file.FileUtil;

import org.hawk.executor.IHawkCommandExecutor;
import org.hawk.executor.command.IHawkExecutionCommand;

import org.hawk.html.HTMLFormatException;
import org.hawk.html.IHtmlFormatter;
import org.hawk.tst.HawkTestProperties;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Manoranjan Sahu
 */
   
   
public class ScriptHTMLExecutor implements IHawkCommandExecutor {
    @Autowired(required = true)
       
    private IHtmlFormatter htmlFormatter;

    public IHtmlFormatter getHtmlFormatter() {
        return htmlFormatter;
    }

    public void setHtmlFormatter(IHtmlFormatter htmlFormatter) {
        this.htmlFormatter = htmlFormatter;
    }
    
    @Override
    public boolean execute(IHawkExecutionCommand hawkCommand) throws Exception {
         HawkTestProperties hawkTestProperties = new HawkTestProperties();
        if (!hawkTestProperties.isLoaded()) {
            hawkTestProperties.load();
        }
        try {
            Set<String> tests = hawkTestProperties.getTestSuites().keySet();
            String scriptLinks = htmlFormatter.createHyperLink(tests);
            String template = FileUtil.readFile("conf/scriptTemplate.html", true);
            template = template.replace("#HAWKSCRIPTS#", scriptLinks);
            for (Map.Entry<String, String> entry : hawkTestProperties.getTestSuites().entrySet()) {

                String script = entry.getValue();
                String scriptOutput = script +".echo";
                
                String scriptHtml = htmlFormatter.formatFile(script);
                String tmp = template.replace("#HAWKSCRIPT#", scriptHtml);
                String scriptOutputHtml = htmlFormatter.formatFile(scriptOutput);
                System.out.println(scriptOutputHtml);
                tmp = tmp.replace("#OUTPUT#",scriptOutputHtml);
                String path = entry.getValue() + ".html";
                FileUtil.writeFile(path, tmp);


            }
        } catch (HTMLFormatException ex) {
            throw new Exception(ex);
        }
        return true;
    }
}
