package org.hawk.config.clazzloader;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.hawk.logger.HawkLogger;

/**
 * Modern JDK 21 compatible class loader utility.
 * 
 * This implementation no longer mutates the system classpath.
 * Instead, it maintains an internal plugin classloader.
 */
public class ClassPathHacker {

    private static final HawkLogger logger =
            HawkLogger.getLogger(ClassPathHacker.class.getName());

    // Dedicated plugin classloader
    private static URLClassLoader pluginClassLoader;

    private static void ensureInitialized() {
        if (pluginClassLoader == null) {
            pluginClassLoader = new URLClassLoader(
                    new URL[0],
                    Thread.currentThread().getContextClassLoader()
            );
        }
    }

    /**
     * Adds multiple jar files to the plugin classloader.
     */
    public static void addFile(List<String> jars) throws Exception {
        if (jars == null || jars.isEmpty()) {
            return;
        }

        for (String jar : jars) {
            addFile(jar);
        }
    }

    public static void addFile(String s) throws Exception {
        if (s == null || s.trim().isEmpty()) {
            return;
        }
        File f = new File(s);
        addFile(f);
    }

    public static void addFiles(String files) throws Exception {
        if (files == null || files.trim().isEmpty()) {
            return;
        }

        StringTokenizer strTok = new StringTokenizer(files, ":;");
        while (strTok.hasMoreTokens()) {
            addFile(strTok.nextToken());
        }
    }

    public static void addFile(File f) throws Exception {
        if (!f.exists()) {
            throw new Exception("jar {" + f + "} does not exist...");
        } else {
            System.out.println("loading plugin jar {" + f + "}");
        }

        try {
            addURL(f.toURI().toURL());
        } catch (MalformedURLException ex) {
            throw new Exception(ex);
        }
    }

    /**
     * Adds the content pointed by the URL to the plugin classloader.
     * 
     * In JDK 9+, we cannot modify the system classloader,
     * so we maintain our own URLClassLoader instead.
     */
    public static void addURL(URL u) throws Exception {

        ensureInitialized();

        try {
            // Create a new classloader that chains the previous one
            pluginClassLoader = new URLClassLoader(
                    new URL[]{u},
                    pluginClassLoader
            );
        } catch (Throwable t) {
            logger.error(t);
            throw new Exception("Error, could not add URL to plugin classloader");
        }
    }

    /**
     * Returns the active plugin classloader.
     */
    public static ClassLoader getPluginClassLoader() {
        ensureInitialized();
        return pluginClassLoader;
    }
}
