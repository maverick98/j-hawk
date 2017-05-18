/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.commons.string;

import java.util.HashMap;
import java.util.Map;
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
public class StringUtilTest {

    public StringUtilTest() {
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
     * Test of isNullOrEmpty method, of class StringUtil.
     */
    @Test
    public void testIsNullOrEmpty() {
        System.out.println("isNullOrEmpty");
        String input = "";
        boolean expResult = true;
        boolean result = StringUtil.isNullOrEmpty(input);
        assertEquals(expResult, result);
        result = StringUtil.isNullOrEmpty(null);
        assertEquals(expResult, result);
        result = StringUtil.isNullOrEmpty("ms");
        assertEquals(false, result);

        result = StringUtil.isNullOrEmpty("  ms ");
        assertEquals(false, result);

        result = StringUtil.isNullOrEmpty("   ");
        assertEquals(true, result);

    }

    /**
     * Test of isStringDataType method, of class StringUtil.
     */
    @Test
    public void testIsStringDataType() {
        System.out.println("isStringDataType");
        String exp = "";
        boolean expResult = false;
        boolean result = StringUtil.isStringDataType(exp);
        assertEquals(false, result);
        result = StringUtil.isStringDataType(null);
        assertEquals(false, result);
        result = StringUtil.isStringDataType("ms");
        assertEquals(false, result);

        result = StringUtil.isStringDataType("  \"ms\" ");
        assertEquals(true, result);

        result = StringUtil.isStringDataType("\"ms1\"");
        assertEquals(true, result);

    }

    /**
     * Test of stringifyArgs method, of class StringUtil.
     */
    @Test
    public void testStringifyArgs() {
        System.out.println("stringifyArgs");
        String[] args = null;
        String expResult = "";
        String result = StringUtil.stringifyArgs(args);
        assertEquals("", result);
        result = StringUtil.stringifyArgs(null);
        assertEquals("", result);
        args = new String[2];
        args[0] = "India";
        args[1] = "Pakistan";
        result = StringUtil.stringifyArgs(args);
        assertEquals("India Pakistan", result);

    }

    /**
     * Test of parseBracketData method, of class StringUtil.
     */
    @Test
    public void testParseBracketData() {
        System.out.println("(parse(Bra)cket)Data");
        String input = "(parse(Bra)cket)Data";
        String expResult = "parse(Bra)cket";
        String result = StringUtil.parseBracketData(input);
        assertEquals(expResult, result);
        result = StringUtil.parseBracketData("(aindia}");
        assertEquals(null, result);
        result = StringUtil.parseBracketData("aindia)}");
        assertEquals(null, result);

    }

    /**
     * Test of parseDelimeterData method, of class StringUtil.
     */
    @Test
    public void testParseDelimeterData_4args() {
        System.out.println("(parse(Bra)cket)Data");
        String input = "(parse(Bra)cket)Data";
        String expResult = "parse(Bra)cket";
        char startingDelimChar = '(';
        char closingDelimChar = ')';
        String result = StringUtil.parseDelimeterData(input, 0, startingDelimChar, closingDelimChar);
        assertEquals(expResult, result);
        result = StringUtil.parseDelimeterData(input, 1, startingDelimChar, closingDelimChar);
        assertEquals(null, result);
        result = StringUtil.parseDelimeterData(input, 20, startingDelimChar, closingDelimChar);
        assertEquals(null, result);

        result = StringUtil.parseDelimeterData(input, -1, startingDelimChar, closingDelimChar);
        assertEquals(null, result);

        result = StringUtil.parseDelimeterData(input, 6, startingDelimChar, closingDelimChar);
        assertEquals("Bra", result);

    }

    /**
     * Test of parseDelimeterData method, of class StringUtil.
     */
    @Test
    public void testParseDelimeterData_3args() {
        System.out.println("(parse(Bra)cket)Data");
        String input = "(parse(Bra)cket)Data";
        String expResult = "parse(Bra)cket";
        char startingDelimChar = '(';
        char closingDelimChar = ')';
        String result = StringUtil.parseDelimeterData(input, startingDelimChar, closingDelimChar);
        assertEquals(expResult, result);

        char startingDelimChar1 = '(';
        char closingDelimChar1 = '{';
        String result1 = StringUtil.parseDelimeterData(input, startingDelimChar1, closingDelimChar1);
        System.out.println(result1);
        assertEquals(null, result1);

    }

    /**
     * Test of replace method, of class StringUtil.
     */
    @Test
    public void testReplace() {
        System.out.println("replace");
        String replacementData = "{1} loves {2}";
        Map<String, String> replacements = new HashMap<>();
        replacements.put("1", "India");
        replacements.put("2", "Pakistan");
        String expResult = "India loves Pakistan";
        String result = StringUtil.replace(replacementData, replacements);
        assertEquals(expResult, result);

        replacements.put("1", null);
        replacements.put("2", "Pakistan");
        try {
            StringUtil.replace(replacementData, replacements);
        } catch (Error e) {
            assertEquals("set the parameter properly", e.getMessage());
        }

        result = StringUtil.replace(replacementData, null);
        assertEquals(replacementData, result);

        try {
            StringUtil.replace(null, replacements);
        } catch (Error e) {
            assertEquals("Empty replacementData", e.getMessage());
        }

    }

    /**
     * Test of toggle method, of class StringUtil.
     */
    @Test
    public void testToggle() {
        System.out.println("toggle");
        String str = "toggle";
        String expResult = "Toggle";
        String result = StringUtil.toggle(str);
        assertEquals(expResult, result);
        result = StringUtil.toggle(null);
        assertEquals(null, result);
        result = StringUtil.toggle("");
        assertEquals("", result);
        result = StringUtil.toggle("   ");
        assertEquals("   ", result);
        result = StringUtil.toggle("Toggle");
        assertEquals("toggle", result);

    }

}
