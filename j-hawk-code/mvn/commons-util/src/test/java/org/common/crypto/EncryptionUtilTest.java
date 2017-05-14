/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.common.crypto;

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
public class EncryptionUtilTest {

    public EncryptionUtilTest() {
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
     * Test of encrypt method, of class EncryptionUtil.
     */
    @Test
    public void testEncrypt() throws Exception {
        System.out.println("encrypt");
        String plainPassword1 = "HelloWorld";
        String result1 = EncryptionUtil.encrypt(plainPassword1);
        assertNotNull(result1);

        String plainPassword2 = "HelloWorld";
        String result2 = EncryptionUtil.encrypt(plainPassword2);
        assertNotNull(result2);
        assertEquals(result1, result2);

        String plainPassword3 = "HelloWorld1";
        String result3 = EncryptionUtil.encrypt(plainPassword3);
        assertNotNull(result3);
        assertNotEquals(result1, result3);

    }

}
