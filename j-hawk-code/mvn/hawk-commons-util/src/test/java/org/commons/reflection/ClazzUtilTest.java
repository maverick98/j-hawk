/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.commons.reflection;

import java.lang.reflect.Method;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author reemeeka
 */
public class ClazzUtilTest {

    public ClazzUtilTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getMethod method, of class ClazzUtil.
     */
    @Test
    public void testGetMethod() {
        System.out.println("getMethod");
        String field = "name";
        String getOrSet = "get";
        String expResult = "getName";
        String result = ClazzUtil.getMethod(field, getOrSet);
        assertEquals(expResult, result);

    }

    /**
     * Test of getSetterMethod method, of class ClazzUtil.
     */
    @Test
    public void testGetSetterMethod() {
        System.out.println("getSetterMethod");
        String field = "name";
        String expResult = "setName";
        String result = ClazzUtil.getSetterMethod(field);
        assertEquals(expResult, result);

    }

    /**
     * Test of getGetterMethod method, of class ClazzUtil.
     */
    @Test
    public void testGetGetterMethod() {
        System.out.println("getGetterMethod");
        String field = "name";
        String expResult = "getName";
        String result = ClazzUtil.getGetterMethod(field);
        assertEquals(expResult, result);

    }

    /**
     * Test of loadClass method, of class ClazzUtil.
     */
    @Test
    public void testLoadClass() throws Exception {
        System.out.println("loadClass");
        String clazzStr = "org.commons.reflection.ClazzUtil";
        Class expResult = null;
        Class result = ClazzUtil.loadClass(clazzStr);
        assertNotNull(result);
        try {
            result = ClazzUtil.loadClass(clazzStr + "aaa");
            fail("expecting ClassNotFoundException");
        } catch (Exception ex) {

        }
        try {
            result = ClazzUtil.loadClass(null);
            fail("expecting Exception");
        } catch (Exception ex) {

        }

        try {
            result = ClazzUtil.loadClass("");
            fail("expecting Exception");
        } catch (Exception ex) {

        }

    }

    /**
     * Test of instantiate method, of class ClazzUtil.
     */
    @Test
    public void testInstantiate_String() throws Exception {
        System.out.println("testInstantiate_String");
        String clazzStr = "org.commons.reflection.ClazzUtil";
        Object expResult = null;
        Object result = ClazzUtil.instantiate(clazzStr);
        assertTrue(result instanceof ClazzUtil);
        try {
            String clazzStr1 = null;
            ClazzUtil.instantiate(clazzStr1);
            fail("expecting Exception");
        } catch (Exception ex) {

        }
        try {
            String clazzStr1 = "";
            ClazzUtil.instantiate(clazzStr1);
            fail("expecting Exception");
        } catch (Exception ex) {

        }
    }

    /**
     * Test of instantiate method, of class ClazzUtil.
     */
    @Test
    public void testInstantiate_Class() throws Exception {
        System.out.println("testInstantiate_Class");
        Class clazz = null;
        Object expResult = null;
        Object result = ClazzUtil.instantiate(ClazzUtil.class);
        assertTrue(result instanceof ClazzUtil);

        result = ClazzUtil.instantiate(clazz);
        assertNull(result);

    }

    public class A {

        private Integer id;
        private String name;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public A(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

    }

    /**
     * Test of instantiate method, of class ClazzUtil.
     */
    @Test
    public void testInstantiate_String_Class() throws Exception {
        System.out.println("testInstantiate_String_Class");
        Object expResult = null;
        String clazzStr = "org.commons.reflection.ClazzUtil";
        ClazzUtil result = ClazzUtil.instantiate(clazzStr, ClazzUtil.class);
        assertNotNull(result);
        clazzStr = null;
        try {
            result = ClazzUtil.instantiate(clazzStr, ClazzUtil.class);
            fail("Excepting exception here");
        } catch (Exception ex) {

        }
        clazzStr = "";
        try {
            result = ClazzUtil.instantiate(clazzStr, ClazzUtil.class);
            fail("Excepting exception here");
        } catch (Exception ex) {

        }

    }

    /**
     * Test of instantiate method, of class ClazzUtil.
     */
    @Test
    public void testInstantiate_Class_Class() throws Exception {
        System.out.println("testInstantiate_Class_Class");
        Object expResult = null;
        String clazzStr = "org.commons.reflection.ClazzUtil";
        ClazzUtil result = ClazzUtil.instantiate(ClazzUtil.class, ClazzUtil.class);
        assertNotNull(result);
        clazzStr = null;
        Class clazz = null;

        result = ClazzUtil.instantiate(clazz, ClazzUtil.class);
        assertNull(result);
        try {
            ClazzUtil.instantiate(A.class, A.class);
            fail("Excepting exception here");
        } catch (Exception ex) {

        }
    }

    /**
     * Test of instantiateFromSpring method, of class ClazzUtil.
     */
    @Test
    public void testInstantiateFromSpring_Class() throws Exception {
        System.out.println("testInstantiateFromSpring_Class");
        Object expResult = null;
        String clazzStr = "org.commons.reflection.TestSpringWithoutCreateAnnotation";

        TestSpringWithoutCreateAnnotation result = ClazzUtil.instantiateFromSpring(clazzStr, TestSpringWithoutCreateAnnotation.class);
        assertNull(result);
        clazzStr = "org.commons.reflection.TestSpringWithCreateAnnotation";
        TestSpringWithCreateAnnotation result1 = ClazzUtil.instantiateFromSpring(clazzStr, TestSpringWithCreateAnnotation.class);
        assertNotNull(result1);

    }

    /**
     * Test of instantiateFromSpring method, of class ClazzUtil.
     */
    @Test
    public void testInstantiateFromSpring_String() throws Exception {
        System.out.println("testInstantiateFromSpring_Class");
        Object expResult = null;
        String clazzStr = "org.commons.reflection.TestSpringWithoutCreateAnnotation";

        TestSpringWithoutCreateAnnotation result = ClazzUtil.instantiateFromSpring(TestSpringWithoutCreateAnnotation.class, TestSpringWithoutCreateAnnotation.class);
        assertNull(result);

        TestSpringWithCreateAnnotation result1 = ClazzUtil.instantiateFromSpring(TestSpringWithCreateAnnotation.class, TestSpringWithCreateAnnotation.class);
        assertNotNull(result1);

    }

    /**
     * Test of instantiateFromSpring method, of class ClazzUtil.
     */
    @Test
    public void testInstantiateFromSpring_String_Class() throws Exception {
        System.out.println("testInstantiateFromSpring_Class");
        Object expResult = null;
        String clazzStr = "org.commons.reflection.TestSpringWithoutCreateAnnotation";

        Object result = ClazzUtil.instantiateFromSpring(TestSpringWithoutCreateAnnotation.class);
        assertNull(result);

        result = ClazzUtil.instantiateFromSpring(TestSpringWithCreateAnnotation.class);
        assertNotNull(result);
    }

    /**
     * Test of instantiateFromSpring method, of class ClazzUtil.
     */
    @Test
    public void testInstantiateFromSpring_Class_Class() throws Exception {
        System.out.println("testInstantiateFromSpring_Class");
        Object expResult = null;
        String clazzStr = "org.commons.reflection.TestSpringWithoutCreateAnnotation";
        Object result = ClazzUtil.instantiateFromSpring(clazzStr);
        assertNull(result);

        clazzStr = "org.commons.reflection.TestSpringWithCreateAnnotation";
        result = ClazzUtil.instantiateFromSpring(clazzStr);
        assertNotNull(result);

    }

    /**
     * Test of findMethod method, of class ClazzUtil.
     */
    @Test
    public void testFindMethod() throws Exception {
        System.out.println("findMethod");
        Class clazz = TestSpringWithCreateAnnotation.class;
        String methodStr = "create";
        Class[] parameterTypes = null;
        Method expResult = null;
        Method result = ClazzUtil.findMethod(clazz, methodStr, parameterTypes);
        assertNotNull(result);
        assertEquals(methodStr, result.getName());

    }

    /**
     * Test of invoke method, of class ClazzUtil.
     */
    @Test
    public void testInvoke_4args_1() throws Exception {
        System.out.println("invoke");
        Object instance_2 = new TestSpringWithCreateAnnotation();
        String methodStr = "create";
        Class[] parameterTypes = null;
        Object[] args = null;
        Object expResult = null;
        String clazzStr = "org.commons.reflection.TestSpringWithCreateAnnotation";
        Object result = ClazzUtil.invoke(clazzStr, methodStr, parameterTypes, args);
        assertNotNull(result);

    }

    /**
     * Test of invoke method, of class ClazzUtil.
     */
    @Test
    public void testInvoke_4args_2() throws Exception {
        System.out.println("invoke");
        Object instance_2 = new TestSpringWithCreateAnnotation();
        String methodStr = "create";
        Class[] parameterTypes = null;
        Object[] args = null;
        Object expResult = null;
        Object result = ClazzUtil.invoke(instance_2, methodStr, parameterTypes, args);
        assertNotNull(result);
    }

    /**
     * Test of invokeZeroArg method, of class ClazzUtil.
     */
    @Test
    public void testInvokeZeroArg() throws Exception {
        System.out.println("invokeZeroArg");
        String clazzStr = "org.commons.reflection.TestSpringWithCreateAnnotation";
        String methodStr = "create";
        Object expResult = null;
        Object result = ClazzUtil.invokeZeroArg(clazzStr, methodStr);
        assertNotNull(result);
    }

    /**
     * Test of invoke method, of class ClazzUtil.
     */
    //@Test
    public void testInvoke_3args() throws Exception {
        System.out.println("invoke");
        Object instance_2 =new TestSpringWithCreateAnnotation();
        Method method = null;
        Object[] args = null;
        Object expResult = null;
        Object result = ClazzUtil.invoke(instance_2, method, args);
        assertEquals(expResult, result);
        
    }

}
