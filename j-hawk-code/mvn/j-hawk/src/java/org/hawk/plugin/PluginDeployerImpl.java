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

import java.util.Set;
import org.common.di.AppContainer;
import org.commons.event.exception.HawkEventException;

import org.hawk.logger.HawkLogger;
import org.hawk.plugin.exception.HawkPluginException;
import org.commons.reflection.Create;
import org.common.di.ScanMe;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author manosahu
 */
@ScanMe(true)
//@Component(PLUGINDEPOYER)
//@Qualifier(DEFAULTQUALIFIER)
public class PluginDeployerImpl implements IPluginDeployer {

    private static final HawkLogger logger = HawkLogger.getLogger(PluginDeployerImpl.class.getName());
    @Autowired(required = true)
    //@Qualifier(HAWKPLUGINSERVICE)
    private HawkPluginServiceImpl hawkPluginServiceImpl;

    public HawkPluginServiceImpl getHawkPluginService() {
        return hawkPluginServiceImpl;
    }

    public void setHawkPluginService(HawkPluginServiceImpl hawkPluginService) {
        this.hawkPluginServiceImpl = hawkPluginService;
    }

    @Override
    public boolean deploy() throws Exception {
        System.out.println("in plugin deployment config");
        try {
            Set<HawkPlugin> plugins = this.getHawkPluginService().findInstalledPlugins();
            this.getHawkPluginService().deploy(plugins);
        } catch (HawkPluginException ex) {
            logger.error(ex);
            throw new Exception(ex);
        } catch (HawkEventException ex) {
            logger.error(ex);
            throw new Exception(ex);
        }
        return true;
    }

    @Create
    public IPluginDeployer create() {
        return AppContainer.getInstance().getBean(this.getClass());
    }
}
