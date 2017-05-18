/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.common.properties;

import java.io.File;
import java.util.Properties;
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
public class PropertiesUtilTest {
    
    public PropertiesUtilTest() {
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
     * Test of saveProperties method, of class PropertiesUtil.
     */
    @Test
    public void testSaveProperties() {
        System.out.println("saveProperties");
        Properties props = new Properties();
        props.put("India","Pakistan");
        String file = "country.properties";
        boolean expResult = false;
        boolean result = PropertiesUtil.saveProperties(null, "");
        assertEquals(expResult, result);
        result = PropertiesUtil.saveProperties(null, null);
        assertEquals(expResult, result);
         result = PropertiesUtil.saveProperties(props, file);
        assertEquals(true, result);
        Properties readP = PropertiesUtil.loadProperties(file);
        assertNotNull(readP);
        assertNotNull(readP.get("India"));
        assertEquals("Pakistan",readP.get("India"));
        
        
        
       
    }

    /**
     * Test of loadConfigsFromClazzpath method, of class PropertiesUtil.
     */
    //@Test
    public void testLoadConfigsFromClazzpath() {
        System.out.println("loadConfigsFromClazzpath");
        String fileName = "";
        Properties expResult = null;
        Properties result = PropertiesUtil.loadConfigsFromClazzpath(fileName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadProperties method, of class PropertiesUtil.
     */
    //@Test
    public void testLoadProperties_String() {
        System.out.println("loadProperties");
        String file = "";
        Properties expResult = null;
        Properties result = PropertiesUtil.loadProperties(file);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadProperties method, of class PropertiesUtil.
     */
  //  @Test
    public void testLoadProperties_File() {
        System.out.println("loadProperties");
        File file = null;
        Properties expResult = null;
        Properties result = PropertiesUtil.loadProperties(file);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
