/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.common.bean;

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
public class BeanConverterTest {

    public BeanConverterTest() {
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
     * Test of toMappingBean method, of class BeanConverter.
     */
    @Test
    public void testToMappingBean() throws Exception {
        System.out.println("toMappingBean");

        Object expResult = null;
        EntityObject nullResult = BeanConverter.toMappingBean(null, EntityObject.class);
        assertEquals(null, nullResult);
        DomainObjectWthoutMappingBeanAnnotation test = new DomainObjectWthoutMappingBeanAnnotation();
        nullResult = BeanConverter.toMappingBean(test, null);
         assertEquals(null, nullResult);
        EntityObject result = BeanConverter.toMappingBean(test, EntityObject.class);
        assertEquals(expResult, result);

    }

    /**
     * Test of fromMappingBean method, of class BeanConverter.
     */
    // @Test
    public void testFromMappingBean() throws Exception {
        System.out.println("fromMappingBean");
        Object expResult = null;
        Object result = BeanConverter.fromMappingBean(null, null);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
