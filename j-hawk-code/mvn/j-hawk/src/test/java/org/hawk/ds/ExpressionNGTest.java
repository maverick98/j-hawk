/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hawk.ds;

import org.commons.ds.exp.InfixExpression;
import java.util.List;
import org.commons.ds.element.IElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author user
 */
public class ExpressionNGTest {
    
    public ExpressionNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    /**
     * Test of getExpression method, of class Expression.
     */
    @Test
    public void testGetExpression() {
        System.out.println("getExpression");
        InfixExpression instance = null;
        List expResult = null;
     
        // TODO review the generated test code and remove the default call to //fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setExpression method, of class Expression.
     */
    @Test
    public void testSetExpression() {
        System.out.println("setExpression");
        List<IElement> expression = null;
        InfixExpression instance = null;
//        instance.setExpression(expression);
        // TODO review the generated test code and remove the default call to //fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class Expression.
     */
    @Test
    public void testMain() throws Exception {
        System.out.println("main");
        String[] args = null;
//        Expression.main(args);
        // TODO review the generated test code and remove the default call to //fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of toPrefix method, of class Expression.
     */
    @Test
    public void testToPrefix() throws Exception {
        System.out.println("toPrefix");
        InfixExpression instance = null;
        List expResult = null;
    //    List result = instance.toPrefix();
   //     assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to //fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of toPostfix method, of class Expression.
     */
    @Test
    public void testToPostfix() throws Exception {
        System.out.println("toPostfix");
        InfixExpression instance = null;
        List expResult = null;
   //     List result = instance.toPostfix();
   //     assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to //fail.
        //fail("The test case is a prototype.");
    }
}