/*
 * This file is part of hawkeye
 * CopyLeft (C) BigBang<-->BigCrunch Manoranjan Sahu, All Rights are left.
 *
 *
 *
 * 
 */
package org.common.di;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.reflections.Reflections;
import org.springframework.beans.BeansException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * The client code will make use of this to get spring managed beans. clients
 * would be separated from the DI framework with this class.
 *
 * @author manoranjan
 */
public class AppContainer {

    private static final AppContainer theInstance = new AppContainer();
    private AnnotationConfigApplicationContext containerCtx ;
    private AnnotationConfigApplicationContext appContext ;
    private Map<String, AnnotationConfigApplicationContext> pluginCtxMap = new HashMap<>();
    private Reflections reflections = null;

    private Map<Class, List<Class>> subTypesMap = new HashMap<>();

    public AnnotationConfigApplicationContext getContainerCtx() {
        return containerCtx;
    }

    public void setContainerCtx(AnnotationConfigApplicationContext containerCtx) {
        this.containerCtx = containerCtx;
    }

    public AnnotationConfigApplicationContext getAppContext() {
        return appContext;
    }

    public void setAppContext(AnnotationConfigApplicationContext appContext) {
        this.appContext = appContext;
    }

    public Reflections getReflections() {
        return reflections;
    }

    public void setReflections(Reflections reflections) {
        this.reflections = reflections;
    }

   

    public boolean registerConfig(Class springConfig) {
        if(this.getAppContext() ==  null){
            this.setAppContext(new AnnotationConfigApplicationContext());
        }
        this.getAppContext().register(springConfig);
        return true;
    }

    public boolean registerContainerConfig(Class springConfig) {
         if(this.getContainerCtx() ==  null){
            this.setContainerCtx(new AnnotationConfigApplicationContext());
        }
        this.getContainerCtx().register(springConfig);
        // this.refresh();
        return true;
    }

    public Map<String, AnnotationConfigApplicationContext> getPluginCtxMap() {
        return pluginCtxMap;
    }

    public void setPluginCtxMap(Map<String, AnnotationConfigApplicationContext> pluginCtxMap) {
        this.pluginCtxMap = pluginCtxMap;
    }

    public boolean registerPluginConfig(String pluginName, Class springConfig) {
        AnnotationConfigApplicationContext pluginAppCtx = new AnnotationConfigApplicationContext();
        pluginAppCtx.register(springConfig);
        this.getPluginCtxMap().put(pluginName, pluginAppCtx);
        pluginAppCtx.refresh();
        return true;
    }

    public boolean deregisterPlugin(String pluginName) {
        AnnotationConfigApplicationContext pluginAppCtx = this.getPluginCtxMap().get(pluginName);
        boolean deregistered;
        if (pluginAppCtx != null) {
            pluginAppCtx.destroy();
            this.getPluginCtxMap().remove(pluginAppCtx);
            deregistered = true;
        } else {
            deregistered = false;
        }
        return deregistered;

    }

    public boolean refreshAppCtx() {
        this.getAppContext().refresh();
        return true;
    }
     public boolean refreshAppContainerCtx() {
        this.getContainerCtx().refresh();
        return true;
    }

    private AppContainer() {
    }

    public static AppContainer getInstance() {

        return theInstance;
    }

    public List<AnnotationConfigApplicationContext> getPluginContexts() {
        List<AnnotationConfigApplicationContext> pluginCtxs = new ArrayList<>();
        for (Entry<String, AnnotationConfigApplicationContext> entry : this.getPluginCtxMap().entrySet()) {
            AnnotationConfigApplicationContext pluginCtx = entry.getValue();
            pluginCtxs.add(pluginCtx);
        }
        return pluginCtxs;

    }

    public List<AnnotationConfigApplicationContext> getCoreContexts() {
        List<AnnotationConfigApplicationContext> coreCtxs = new ArrayList<>();
        if(this.getAppContext()!= null){
        coreCtxs.add(this.getAppContext());
        }
        return coreCtxs;
    }

    public List<AnnotationConfigApplicationContext> getContainerContexts() {
        List<AnnotationConfigApplicationContext> containerCtxs = new ArrayList<>();
        if(this.getContainerCtx()!= null){
            containerCtxs.add(this.getContainerCtx());
        }
        return containerCtxs;
    }

    public AnnotationConfigApplicationContext[] getAllContexts() {
        List<AnnotationConfigApplicationContext> containerCtxs = this.getContainerContexts();
        List<AnnotationConfigApplicationContext> coreCtxs = this.getCoreContexts();
        List<AnnotationConfigApplicationContext> pluginCtxs = this.getPluginContexts();
        List<AnnotationConfigApplicationContext> allCtxs = new ArrayList<>();
        allCtxs.addAll(containerCtxs);
        allCtxs.addAll(coreCtxs);
        allCtxs.addAll(pluginCtxs);
        return allCtxs.toArray(new AnnotationConfigApplicationContext[]{});
    }

    public Object getBeanFrom(String clazzStr, AnnotationConfigApplicationContext... ctxs) {
        Object bean = null;
        if (ctxs != null) {
            for (AnnotationConfigApplicationContext ctx : ctxs) {
                try {
                   
                    bean = ctx.getBean(clazzStr);
                    
                } catch (BeansException bex) {

                }
                if (bean != null) {
                    break;
                }
            }
        }
        return bean;
    }

    public <T extends Object> T getBeanFrom(Class<T> clazz, AnnotationConfigApplicationContext... ctxs) {
        T bean = null;
        if (ctxs != null) {
            for (AnnotationConfigApplicationContext ctx : ctxs) {
                try {
                    
                    bean = ctx.getBean(clazz);
// Ok I dint know it throws a exception if it is not present. poor API. 
//it shud not throw anything.. rather a null I wud expect
                } catch (BeansException bex) {
                  
                }
                if (bean != null) {
                    break;
                }
            }
        }
        return bean;
    }

    public <T extends Object> T getBean(String clazzStr, Class<T> type) {
        T result = (T) this.getBean(clazzStr);
        return result;
    }

    public <T extends Object> T getBean(Class<T> clazz) {

        T result;

        result = this.getBeanFrom(clazz, this.getAllContexts());

        return result;
    }

    public Object getBean(String clazzStr) {

        Object result;

        result = this.getBeanFrom(clazzStr, this.getAllContexts());

        return result;
    }

    public List<Class> getSubTypesOf(Class parentClazz) {
        List<Class> result = this.subTypesMap.get(parentClazz);
        if (result != null) {
            return result;
        }
        result = new ArrayList<>();

        if (this.getReflections() == null) {
            this.setReflections(new Reflections("org"));
        }

        Set<Class> all = this.getReflections().getSubTypesOf(parentClazz);
        if (all != null && !all.isEmpty()) {
            for (Class clazzImpl : all) {
                if (clazzImpl.isAnnotationPresent(ScanMe.class)) {
                    boolean shouldScan = ((ScanMe) clazzImpl.getAnnotation(ScanMe.class)).value();
                    if (shouldScan) {
                        result.add(clazzImpl);
                    }
                }
            }
        }
        this.subTypesMap.put(parentClazz, result);
        return result;
    }
}
