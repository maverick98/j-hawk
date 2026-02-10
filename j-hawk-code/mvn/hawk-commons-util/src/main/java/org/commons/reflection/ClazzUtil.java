/*
 * This file is part of j-hawk
 *  
 *
 * 
 *
 *  
 *  
 *  
 *  
 *
 * 
 * 
 *
 * 
 */
package org.commons.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;
import org.commons.logger.ILogger;
import org.commons.logger.LoggerFactory;
import org.commons.string.StringUtil;

/**
 *
 * @author manosahu
 */
public class ClazzUtil {

    private static final ILogger logger = LoggerFactory.getLogger(ClazzUtil.class.getName());

    public static String getMethod(String field, String getOrSet) {
        StringBuilder sb = new StringBuilder();
        sb.append(getOrSet);
        sb.append(String.valueOf(field.charAt(0)).toUpperCase(Locale.ENGLISH));
        sb.append(field.substring(1));
        return sb.toString();
    }

    public static String getSetterMethod(String field) {
        return getMethod(field, "set");
    }

    public static String getGetterMethod(String field) {
        return getMethod(field, "get");
    }

    public static Class loadClass(String clazzStr) throws Exception {
        if (StringUtil.isNullOrEmpty(clazzStr)) {
            throw new Exception("illegal args");
        }

        try {
            // 1 Try thread context classloader first (plugin-aware)
            ClassLoader contextLoader =
                    Thread.currentThread().getContextClassLoader();

            if (contextLoader != null) {
                return Class.forName(clazzStr, true, contextLoader);
            }

        } catch (ClassNotFoundException ignored) {
            // fallback below
        }

        try {
            // 2 Fallback to system/application loader
            return Class.forName(clazzStr);

        } catch (ClassNotFoundException cnfe) {
            logger.error(cnfe);
            throw new Exception(cnfe);
        }
    }


    private static Method findCreateMethod(Class clazz) {

        Method result = null;
        Method methods[] = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Create.class)) {
                result = method;
                break;
            }
        }
        return result;
    }

    public static Object instantiate(String clazzStr) throws Exception {
        return instantiate(loadClass(clazzStr));
    }

    public static Object instantiate(Class clazz) throws Exception {
        return createObject(clazz);
    }

    public static <T> T instantiate(String clazzStr, Class<T> type) throws Exception {
        Object instance = instantiate(clazzStr);
        return (T) instance;

    }

    public static <T> T instantiate(Class clazz, Class<T> type) throws Exception {
        Object instance = instantiate(clazz);
        return (T) instance;

    }

    private static Object createObject(Class clazz) throws Exception {
        Object result = null;
        if (clazz != null) {
            try {

                result = clazz.newInstance();

            } catch (InstantiationException ex) {
                result = null;
                logger.error(ex);
                throw new Exception(ex);
            } catch (IllegalAccessException | SecurityException | IllegalArgumentException ex) {
                result = null;
                logger.error(ex);
                throw new Exception(ex);
            }
        }
        return result;
    }

    public static Object instantiateFromSpring(Class clazz) throws Exception {

        Object result = null;
        if (clazz != null) {
            Method createMethod = findCreateMethod(clazz);
            if (createMethod != null) {
                result = invoke(createObject(clazz), createMethod, new Object[]{});
            }
        }
        return result;
    }

    public static Object instantiateFromSpring(String clazzStr) throws Exception {
        return instantiateFromSpring(loadClass(clazzStr));
    }

    public static <T> T instantiateFromSpring(String clazzStr, Class<T> type) throws Exception {
        Object instance = instantiateFromSpring(clazzStr);
        return (T) instance;

    }

    public static <T> T instantiateFromSpring(Class clazz, Class<T> type) throws Exception {
        Object instance = instantiateFromSpring(clazz);
        return (T) instance;

    }

    public static Method findMethod(Class clazz, String methodStr, Class[] parameterTypes) throws Exception {
        Method result = null;
        if (clazz == null || StringUtil.isNullOrEmpty(methodStr)) {
            return null;
        }
        try {
            result = clazz.getDeclaredMethod(methodStr, parameterTypes);

        } catch (NoSuchMethodException | SecurityException ex) {
            logger.error(ex);
            throw new Exception(ex);
        }
        return result;
    }

    public static Object invoke(Object instance, String methodStr, Class[] parameterTypes, Object[] args) throws Exception {
        Object result;
        Class clazz = instance.getClass();

        Method method = findMethod(clazz, methodStr, parameterTypes);
        result = invoke(instance, method, args);

        return result;
    }

    public static Object invoke(String clazzStr, String methodStr, Class[] parameterTypes, Object[] args) throws Exception {
        Object instance = instantiate(clazzStr);
        return invoke(instance, methodStr, parameterTypes, args);
    }

    public static Object invokeZeroArg(String clazzStr, String methodStr) throws Exception {
        return invoke(clazzStr, methodStr, new Class[]{}, new Object[]{});
    }

    public static Object invoke(Object instance, Method method, Object[] args) throws Exception {

        Object result = null;
        if (method != null) {
            try {
                result = method.invoke(instance, args);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                logger.error(ex);
                throw new Exception(ex);
            }
        }

        return result;
    }
}
