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
package org.hawk.plugin;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.commons.file.FileUtil;
import org.hawk.config.clazzloader.ClassPathHacker;
import org.common.di.AppContainer;
import org.commons.event.HawkEventPayload;
import org.commons.event.callback.IHawkEventCallbackRegistry;
import org.commons.event.exception.HawkEventException;

import org.hawk.executor.command.plugin.available.AvailablePluginHtmlJavaBean;

import org.hawk.file.ZipUtil;
import org.hawk.html.java.HtmlJavaBean;
import org.hawk.logger.HawkLogger;
import static org.hawk.plugin.constant.HawkPluginConstant.PLUGINDIR;
import static org.hawk.plugin.constant.HawkPluginConstant.PLUGINEXTENSIONS;
import org.hawk.plugin.event.HawkPluginExtractionEvent;
import org.hawk.plugin.event.HawkPluginLoadingEvent;
import org.hawk.plugin.event.HawkPluginValidationEvent;
import org.hawk.plugin.event.PostHawkPluginDeploymentEvent;
import org.hawk.plugin.event.PostHawkPluginUndeploymentEvent;
import org.hawk.plugin.event.PreHawkPluginDeploymentEvent;
import org.hawk.plugin.event.PreHawkPluginUndeploymentEvent;
import org.hawk.plugin.exception.HawkPluginException;
import org.hawk.plugin.metadata.Classpath;
import org.hawk.plugin.metadata.HawkPluginMetaData;
import org.hawk.plugin.metadata.Jar;
import org.hawk.util.HttpUtil;
import org.commons.string.StringUtil;
import org.commons.event.callback.HawkEventCallbackRegistry;
import org.hawk.tst.functional.html.InternalFuncTestHTMLJavaServiceImpl;
import org.hawk.xml.XMLUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * TODO refactor me... I am putting on weight
 *
 * @author Manoranjan Sahu
 */
public class HawkPluginServiceImpl implements IHawkPluginService {

    private static final HawkLogger logger = HawkLogger.getLogger(HawkPluginServiceImpl.class.getName());

    //@Autowired(required = true)
    //   
    //private IHawkPluginCallbackRegistry hawkPluginCallbackRegistry;
    public IHawkEventCallbackRegistry getHawkPluginCallbackRegistry() {
        return AppContainer.getInstance().getBean(HawkEventCallbackRegistry.class);
        //return hawkPluginCallbackRegistry;
    }

    // public void setHawkPluginCallbackRegistry(IHawkPluginCallbackRegistry hawkPluginCallbackRegistry) {
    //     this.hawkPluginCallbackRegistry = hawkPluginCallbackRegistry;
    // }
    @Override
    public boolean load(HawkPlugin hawkPlugin) throws HawkPluginException, HawkEventException {
        if (hawkPlugin == null) {
            throw new HawkPluginException("illegal plugin");
        }
        boolean pluginLoaded = this.loadPluginMetaData(hawkPlugin);

        if (pluginLoaded) {
            pluginLoaded = this.addPluginJars(hawkPlugin);
            /*
             if (pluginLoaded) {
             this.loadPluginConfig(hawkPlugin);
             }
             */
        }
        return pluginLoaded;
    }

    public boolean loadPluginMetaData(HawkPlugin hawkPlugin) throws HawkPluginException, HawkEventException {
        HawkPluginMetaData hawkPluginMetaData = new HawkPluginMetaData();
        try {
            String xml = hawkPlugin.getMetaDataXMLPath();
            hawkPluginMetaData = (HawkPluginMetaData) XMLUtil.unmarshal(xml, HawkPluginMetaData.class);

        } catch (Throwable th) {

            th.printStackTrace();
            throw new HawkPluginException(th);
        }
        hawkPlugin.setPluginMetaData(hawkPluginMetaData);
        return true;
    }

    private boolean addPluginJars(HawkPlugin hawkPlugin) throws HawkPluginException, HawkEventException {
        Classpath classpath = hawkPlugin.getPluginMetaData().getClasspath();

        List<String> jars = new ArrayList<>();
        classpath.getJar().stream().forEach((jar) -> {
            jars.add(hawkPlugin.getAbsolutePath(jar.getPath()));
        });
        return this.addPluginJars(jars);

    }

    private boolean addPluginJars(List<String> jars) throws HawkPluginException, HawkEventException {

        try {
            //ClazzLoaderUtil.getInstance().addJars(jars);
            ClassPathHacker.addFile(jars);
        } catch (Exception ex) {
            throw new HawkPluginException(ex);
        }
        return true;
    }

    @Override
    public boolean deploy(String hawkPluginArchive) throws HawkPluginException, HawkEventException {
        boolean isDeployed = false;
        boolean isCopied = this.copy(hawkPluginArchive);
        if (isCopied) {
            File hawkPluginArchiveFile = new File(hawkPluginArchive);
            HawkPlugin hawkPlugin = new HawkPlugin(hawkPluginArchiveFile.getName());
            hawkPlugin.setPluginRootDir(this.getPluginRootDir());
            isDeployed = this.deploy(hawkPlugin);
        } else {
            throw new HawkPluginException("unable to copy {" + hawkPluginArchive + "}");
        }
        return isDeployed;
    }

    @Override
    public boolean deploy(HawkPlugin hawkPlugin) throws HawkPluginException, HawkEventException {
        if (hawkPlugin == null) {
            throw new HawkPluginException("illegal plugin");
        }

        boolean isExtracted = false;
        logger.info("------------------------------START------------------------------------------");

        logger.info("validating the plugin " + hawkPlugin);
        boolean isValidated = hawkPlugin.validate();
        boolean isDeployed = false;
        hawkPlugin.setValidated(isValidated);
        HawkEventPayload hawkEventPayload = new HawkEventPayload();
        hawkEventPayload.setPayload(hawkPlugin);
        this.getHawkPluginCallbackRegistry().dispatch(HawkPluginValidationEvent.class, hawkEventPayload);
        if (isValidated) {
            logger.info("validation successfull!!!");
            logger.info("executing pre deployemnt tasks");
            boolean preDeploymentCheck = this.getHawkPluginCallbackRegistry().dispatch(PreHawkPluginDeploymentEvent.class, hawkEventPayload);

            if (preDeploymentCheck) {

                logger.info("pre deployment tasks executed successfully!!!");
                logger.info("extracting the plugin " + hawkPlugin);
                isExtracted = this.extract(hawkPlugin);
                hawkPlugin.setExtracted(isExtracted);
                this.getHawkPluginCallbackRegistry().dispatch(HawkPluginExtractionEvent.class, hawkEventPayload);
                if (isExtracted) {

                    logger.info("extracted successfully!!!");
                    logger.info("loading plugin information");

                    boolean isLoaded = this.load(hawkPlugin);

                    hawkPlugin.setLoaded(isLoaded);

                    this.getHawkPluginCallbackRegistry().dispatch(HawkPluginLoadingEvent.class, hawkEventPayload);

                    if (isLoaded) {
                        logger.info("plugin info loaded successfully!!!");
                    } else {
                        logger.info("unable to load plugin info");
                    }

                } else {
                    logger.info("unable to extract the plugin " + hawkPlugin);
                }
            } else {
                logger.info("unable to execute pre deployment tasks... check the log files");
            }

        } else {
            isDeployed = false;
            logger.info("validation failed");
        }
        System.out.println("in core:::" + this.getHawkPluginCallbackRegistry());
        if (isExtracted) {
            isDeployed = this.getHawkPluginCallbackRegistry().dispatch(PostHawkPluginDeploymentEvent.class, hawkEventPayload);
            if (isDeployed) {
                System.out.println(hawkPlugin.getPluginMetaData().getSoftware().getAbout());
            }
        }
        logger.info("------------------------------END------------------------------------------");
        return isDeployed;
    }

    @Override
    public boolean deploy(Set<HawkPlugin> hawkPlugins) throws HawkPluginException, HawkEventException {
        if (hawkPlugins == null) {
            throw new HawkPluginException("Illegal args");
        }
        for (HawkPlugin hawkPlugin : hawkPlugins) {
            this.deploy(hawkPlugin);
        }
        return true;
    }

    @Override
    public boolean unDeploy(String hawkPluginArchive) throws HawkPluginException, HawkEventException {
        if (StringUtil.isNullOrEmpty(hawkPluginArchive)) {
            throw new HawkPluginException("illegal args");
        }

        HawkPlugin hawkPlugin = new HawkPlugin(hawkPluginArchive);

        return this.unDeploy(hawkPlugin);
    }

    @Override
    public boolean unDeploy(HawkPlugin hawkPlugin) throws HawkPluginException, HawkEventException {
        if (hawkPlugin == null) {
            throw new HawkPluginException("illegal plugin");
        }

        boolean isValidated = hawkPlugin.validate();
        boolean isUnDeployed = false;
        HawkEventPayload hawkEventPayload = new HawkEventPayload();
        hawkEventPayload.setPayload(hawkPlugin);
        if (isValidated) {
            this.getHawkPluginCallbackRegistry().dispatch(PreHawkPluginUndeploymentEvent.class, hawkEventPayload);
            boolean isDeleted = this.remove(hawkPlugin);
            if (isDeleted) {
                logger.info("plugin " + hawkPlugin + " is unDeployed successfully!!!");
                //do the actual work    
                isUnDeployed = true;
            }

        } else {
            isUnDeployed = false;
        }
        if (isUnDeployed) {
            this.getHawkPluginCallbackRegistry().dispatch(PostHawkPluginUndeploymentEvent.class, hawkEventPayload);
        }
        return isUnDeployed;
    }

    @Override
    public boolean unDeploy(Set<HawkPlugin> hawkPlugins) throws HawkPluginException, HawkEventException {
        if (hawkPlugins == null) {
            throw new HawkPluginException("Illegal args");
        }
        for (HawkPlugin hawkPlugin : hawkPlugins) {
            this.unDeploy(hawkPlugin);
        }
        return true;
    }
    @Autowired(required = true)

    private InternalFuncTestHTMLJavaServiceImpl internalFuncTestHTMLJavaServiceImpl;

    public InternalFuncTestHTMLJavaServiceImpl getInternalFuncTestHTMLJavaServiceImpl() {
        return internalFuncTestHTMLJavaServiceImpl;
    }

    public void setInternalFuncTestHTMLJavaServiceImpl(InternalFuncTestHTMLJavaServiceImpl internalFuncTestHTMLJavaServiceImpl) {
        this.internalFuncTestHTMLJavaServiceImpl = internalFuncTestHTMLJavaServiceImpl;
    }

    @Override
    public Set<AvailablePluginHtmlJavaBean> showAvailablePlugins() throws HawkPluginException, HawkEventException {
        Set<AvailablePluginHtmlJavaBean> availablePlugins = new HashSet<>();
        List<HtmlJavaBean> list = null;
        try {
            list = this.getInternalFuncTestHTMLJavaServiceImpl().toJavaList(new URL("http://j-hawk.sourceforge.net/plugin.html"), AvailablePluginHtmlJavaBean.class);
        } catch (Exception ex) {
            Logger.getLogger(HawkPluginServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new HawkPluginException("Error while finding available plugins", ex);
        }
        list.forEach(htmlJavaBean -> {
            AvailablePluginHtmlJavaBean availablePluginHtmlJavaBean = (AvailablePluginHtmlJavaBean) htmlJavaBean;
            availablePlugins.add(availablePluginHtmlJavaBean);
        });

        return availablePlugins;
    }

    @Override
    public Set<HawkPlugin> findAvailablePlugins() throws HawkPluginException, HawkEventException {
        Set<HawkPlugin> availablePlugins = new HashSet<>();
        Set<AvailablePluginHtmlJavaBean> availablePluginHtmlJavaBeans = this.showAvailablePlugins();
        availablePluginHtmlJavaBeans.forEach(availablePluginHtmlJavaBean -> {
            HawkPlugin hawkPlugin = null;
            try {
                hawkPlugin = new HawkPlugin(availablePluginHtmlJavaBean.getPath());
            } catch (HawkPluginException ex) {
                Logger.getLogger(HawkPluginServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            hawkPlugin.setName(availablePluginHtmlJavaBean.getPlugin());
            availablePlugins.add(hawkPlugin);
        });

        return availablePlugins;
    }

    @Override
    public Set<HawkPlugin> findDownloadedPlugins() throws HawkPluginException, HawkEventException {
        Collection<File> downloadedPluginArchiveFiles = this.listAllPluginArchives();
        Set<HawkPlugin> downloadedPlugins = new TreeSet<>();
        if (downloadedPluginArchiveFiles != null) {
            for (File downloadedPluginArchivFile : downloadedPluginArchiveFiles) {
                HawkPlugin downloadedPlugin = new HawkPlugin(downloadedPluginArchivFile.getName(), this.getPluginRootDir());
                    downloadedPlugins.add(downloadedPlugin);
            }
        }
        return downloadedPlugins;
    }

    private Collection<File> listAllPluginArchives() throws HawkPluginException, HawkEventException {

        File pluginRootDir = new File(this.getPluginRootDir());
        Collection<File> result = null;
        if (pluginRootDir != null && pluginRootDir.exists()) {
            result = FileUtils.listFiles(pluginRootDir, PLUGINEXTENSIONS, true);
        }
        return result;

    }

    @Override
    public Set<HawkPlugin> findInstalledPlugins() throws HawkPluginException, HawkEventException {
        Set<HawkPlugin> installedPlugins = this.findDownloadedPlugins();
        for (Iterator<HawkPlugin> itr = installedPlugins.iterator(); itr.hasNext();) {
            HawkPlugin hawkPlugin = itr.next();
            if (!hawkPlugin.isExtracted()) {
                itr.remove();
            }else{
                this.loadPluginMetaData(hawkPlugin);
            }
        }
        return installedPlugins;
    }

    @Override
    public Set<HawkPlugin> findNYIPlugins() throws HawkPluginException, HawkEventException {
        Set<HawkPlugin> downloadedPlugins = this.findDownloadedPlugins();
        Set<HawkPlugin> installedPlugins = this.findInstalledPlugins();
        return this.diff(downloadedPlugins, installedPlugins);
    }

    @Override
    public Set<HawkPlugin> findNYDPlugins() throws HawkPluginException, HawkEventException {
        Set<HawkPlugin> availablePlugins = this.findAvailablePlugins();
        Set<HawkPlugin> downloadedPlugins = this.findDownloadedPlugins();
        return this.diff(availablePlugins, downloadedPlugins);
    }

    private Set<HawkPlugin> diff(Set<HawkPlugin> a, Set<HawkPlugin> b) {
        if (a == null) {
            return null;
        }
        if (b == null) {
            b = new TreeSet<>();
        }
        Set<HawkPlugin> result;
        a.removeAll(b);
        result = a;
        return result;
    }

    @Override
    public boolean unDeployAll() throws HawkPluginException, HawkEventException {
        boolean unDeployed = false;
        Set<HawkPlugin> allPlugins = this.findInstalledPlugins();
        if (allPlugins != null && !allPlugins.isEmpty()) {
            unDeployed = this.unDeploy(allPlugins);
        }
        return unDeployed;
    }

    @Override
    public boolean extract(HawkPlugin hawkPlugin) throws HawkPluginException, HawkEventException {
        if (hawkPlugin == null || !hawkPlugin.validate()) {
            throw new HawkPluginException("Illegal args");
        }
        boolean isExtracted = false;
        try {
            isExtracted = ZipUtil.unzip(this.getPluginArchivePath(hawkPlugin), this.getPluginRootDir());
        } catch (Exception ex) {
            throw new HawkPluginException(ex);
        }
        hawkPlugin.setExtractedJustNow(isExtracted);
        return isExtracted;
    }

    @Override
    public String getPluginArchivePath(HawkPlugin hawkPlugin) throws HawkPluginException {
        if (hawkPlugin == null || !hawkPlugin.validate()) {
            throw new HawkPluginException("Illegal args");
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.getPluginRootDir());
        sb.append(File.separator);
        sb.append(hawkPlugin.getPluginArchive());
        return sb.toString();
    }

    @Override
    public boolean remove(HawkPlugin hawkPlugin) throws HawkPluginException, HawkEventException {
        if (hawkPlugin == null || !hawkPlugin.validate()) {
            throw new HawkPluginException("Illegal args");
        }
        boolean result;
        String pluginHome = hawkPlugin.getPluginHome();

        result = FileUtil.remove(pluginHome);

        return result;
    }

    @Override
    public String getPluginRootDir() {
        return PLUGINDIR;
    }
    @Override
    public HawkPlugin getPlugin(String pluginURL) {
        File file = new File(pluginURL);

        HawkPlugin hawkPlugin = null;
        try {
            hawkPlugin = new HawkPlugin(file.getName());
        } catch (HawkPluginException ex) {
            hawkPlugin = null;
        }

        return hawkPlugin;
    }

    @Override
    public String getPluginHome(String pluginURL) {
        File file = new File(pluginURL);

        HawkPlugin hawkPlugin = null;
        try {
            hawkPlugin = new HawkPlugin(file.getName());
        } catch (HawkPluginException ex) {
            hawkPlugin = null;
        }

        return hawkPlugin.getPluginHome();
    }

    @Override
    public boolean copy(String pluginArchive) throws HawkPluginException, HawkEventException {
        boolean isCopied = false;

        isCopied = FileUtil.copy(pluginArchive, this.getPluginRootDir());

        return isCopied;
    }

    @Override
    public boolean download(URL pluginArchiveURL) throws HawkPluginException, HawkEventException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HawkPlugin findPlugin(String hawkPluginArchive) throws HawkPluginException, HawkEventException {
        if (StringUtil.isNullOrEmpty(hawkPluginArchive)) {
            throw new HawkPluginException("illegal args");
        }
        HawkPlugin hawkPlugin = new HawkPlugin(hawkPluginArchive);
        boolean isValid = hawkPlugin.validate();
        if (!isValid) {
            throw new HawkPluginException("invalid plugin");
        }
        boolean pluginLoaded = this.load(hawkPlugin);
        if (!pluginLoaded) {
            throw new HawkPluginException("unable load the details of " + hawkPluginArchive + "");
        }

        return hawkPlugin;
    }

    @Override
    public boolean downloadPlugin(HawkPlugin hawkPlugin) throws HawkPluginException, HawkEventException {
        if (hawkPlugin == null || hawkPlugin.getPluginMetaData() == null) {
            throw new IllegalArgumentException("illegal args");
        }
        String downloadURLStr = hawkPlugin.getPluginMetaData().getDownloadURL();
        if (StringUtil.isNullOrEmpty(downloadURLStr)) {
            throw new HawkPluginException("now download url... can not download");
        }
        URL downloadURL;
        try {
            downloadURL = new URL(downloadURLStr);
        } catch (MalformedURLException ex) {
            logger.error(ex);
            return false;
        }
        File downloadLocalPath = new File(hawkPlugin.getPluginHome());
        HttpUtil.download(downloadURL, downloadLocalPath);

        return true;
    }
    @Override
    public boolean downloadPlugin(String pluginURL) throws HawkPluginException, HawkEventException {
        if (StringUtil.isNullOrEmpty(pluginURL)) {
            throw new IllegalArgumentException("illegal args");
        }
       
        URL downloadURL;
        try {
            downloadURL = new URL(pluginURL);
        } catch (MalformedURLException ex) {
            logger.error(ex);
            return false;
        }
        HawkPlugin hawkPlugin = this.getPlugin(pluginURL);
        hawkPlugin.createDir();
        String downloadLocalPath = hawkPlugin.getPluginHome()+"."+hawkPlugin.getExtension();
        HttpUtil.download(downloadURL, new File(downloadLocalPath));

        return true;
    }

}
