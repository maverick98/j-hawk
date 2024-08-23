/*
 * This file is part of hawkeye
 *  
 *
 * 
 *
 *  
 *  
 * 3) Developers are encouraged but not required to notify the hawkeye maintainers at abeautifulmind98@gmail.com
 *  
 *
 * 
 * 
 *
 * 
 */
package org.hawk.config.clazzloader;

/**
 *
 * @author manoranjan
 */
import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.List;
import java.util.StringTokenizer;

import org.hawk.logger.HawkLogger;

/**
 * Useful class for dynamically changing the classpath, adding classes during
 * runtime...
 * My replacement has come.. can be fired any time
 * My replacement did not work as expected... rehired again
 *
 * @author unknown
 */
public class ClassPathHacker {

    /**
     * Parameters of the method to add an URL to the System classes.
     */
    private static final Class<?>[] parameters = new Class[]{URL.class};

    /**
     * Adds a file to the classpath.
     *
     * @param s a String pointing to the file
     * @throws Exception
     */
    public static void addFile(List<String> jars) throws Exception {
        if (jars == null || jars.isEmpty()) {
            return;
        }
        for (String jar : jars) {
            File f = new File(jar);
            if(!f.exists()){
                throw new Exception("jar {"+f+"} does not exist...");
            }else{
                System.out.println("adding jar {"+f+"} to system classpath");
            }
            addFile(f);
        }

    }//end method

    public static void addFile(String s) throws Exception {
        File f = new File(s);
        addFile(f);
    }//end method

    public static void addFiles(String files) throws Exception {
        if (files == null || files.trim().isEmpty()) {

            return;
        }
        StringTokenizer strTok = new StringTokenizer(files, ":");
        while (strTok.hasMoreTokens()) {
            addFile(strTok.nextToken());
        }
        strTok = new StringTokenizer(files, ";");
        while (strTok.hasMoreTokens()) {
            addFile(strTok.nextToken());
        }

    }

    /**
     * Adds a file to the classpath
     *
     * @param f the file to be added
     * @throws Exception
     */
    public static void addFile(File f) throws Exception {
        try {
            addURL(f.toURI().toURL());
        } catch (MalformedURLException ex) {
            throw new Exception(ex);
        }
    }//end method

    /**
     * Adds the content pointed by the URL to the classpath.
     *
     * @param u the URL pointing to the content to be added
     * @throws Exception
     */
    public static void addURL(URL u) throws Exception {
        URLClassLoader sysloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        Class<?> sysclass = URLClassLoader.class;
        try {
            final Method method = sysclass.getDeclaredMethod("addURL", parameters);
            AccessController.doPrivileged(new PrivilegedAction() {
                @Override
                public Object run() {
                    method.setAccessible(true);
                    return null;
                }
            });

            method.invoke(sysloader, new Object[]{u});
        } catch (Throwable t) {
            logger.error(t);
            throw new Exception("Error, could not add URL to system classloader");
        }
    }
    private static final HawkLogger logger = HawkLogger.getLogger(ClassPathHacker.class.getName());
}