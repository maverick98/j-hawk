/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.commons.process;

import org.commons.file.FileUtil;
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
public class ProcessUtilTest {
    
    public ProcessUtilTest() {
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
     * Test of executeProcess method, of class ProcessUtil.
     */
    @Test
    public void testExecuteProcess() {
        System.out.println("executeProcess");
        String command = "ls -ltr";
        boolean expResult = false;
        boolean result = ProcessUtil.executeProcess(command);
        assertEquals(true, result);
        
        result = ProcessUtil.executeProcess("aaa");
        assertEquals(false, result);
        command = "echo abc > abc.log";
        result = ProcessUtil.executeProcess(command);
        assertEquals(true, result);
      
       
    }
    
}
