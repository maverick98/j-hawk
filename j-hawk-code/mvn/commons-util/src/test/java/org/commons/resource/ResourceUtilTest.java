/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.commons.resource;

import java.io.Closeable;
import java.io.IOException;
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
public class ResourceUtilTest {

    public ResourceUtilTest() {
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
     * Test of close method, of class ResourceUtil.
     */
    @Test
    public void testClose() {
        System.out.println("close");

        Closeable[] closeables = null;
        boolean expResult = false;
        boolean result = ResourceUtil.close(closeables);
        assertEquals(expResult, result);
        closeables =  new Closeable[2];
        Closeable closeable1 = new Closeable() {

            @Override
            public void close() throws IOException {
                return;
            }
        };
        closeables[0] = closeable1;

        Closeable closeable2 = new Closeable() {

            @Override
            public void close() throws IOException {
                return;
            }
        };
        closeables[1] = closeable2;
        result = ResourceUtil.close(closeables);
        assertEquals(true, result);
        
        
        
         Closeable closeable3 = new Closeable() {

            @Override
            public void close() throws IOException {
                throw new IOException("throwing");
            }
        };
         try{
         ResourceUtil.close(closeable3);
         }catch(Error ex){
             assertEquals(true,ex.getMessage().startsWith("unable to close resource"));
         }
    }

}
