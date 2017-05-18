/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.commons.collections;

import java.util.Set;
import java.util.TreeSet;
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
public class CollectionUtilTest {
    
    public CollectionUtilTest() {
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
     * Test of isNullOrEmpty method, of class CollectionUtil.
     */
    @Test
    public void testIsNullOrEmpty() {
        System.out.println("isNullOrEmpty");
        Set<String> set = new TreeSet<>();
        boolean expResult = false;
        boolean result = CollectionUtil.isNullOrEmpty(set);
        assertEquals(true, result);
        result = CollectionUtil.isNullOrEmpty(set);
        assertEquals(true, result);
        set.add("India");
        result = CollectionUtil.isNullOrEmpty(set);
        assertEquals(false, result);
       
    }
    
}
