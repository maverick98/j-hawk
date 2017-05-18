/**
 * This file is part of impensa. CopyLeft (C) BigBang<->BigCrunch.All Rights are
 * left.
 *
 * 1) Modify it if you can understand. 2) If you distribute a modified version,
 * you must do it at your own risk.
 *
 *
 */
package org.commons.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Map;
import java.util.Scanner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author reemeeka
 */
public class IOUtilTest {

    public IOUtilTest() {
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
     * Test of createReader method, of class IOUtil.
     */
    @Test
    public void testCreateReader() {
        System.out.println("createReader");
        String data = "";
        Reader expResult = null;
        Reader result = IOUtil.createReader(data);
        assertNotNull("empty string reader should not be null", result);
        result = IOUtil.createReader(null);
        assertNull("null string reader should  be null", result);
        result = IOUtil.createReader("hello");
        assertNotNull("hello string reader should not be null", result);

    }

    /**
     * Test of readLineFromConsole method, of class IOUtil.
     */
    //@Test
    public void testReadLineFromConsole() throws Exception {
        System.out.println("readLineFromConsole");
        String expResult = "";
        Scanner scanner = null;
        when(scanner.nextLine()).thenReturn("console");
        String data = IOUtil.readLineFromConsole();
        assertNotNull(data);

    }

    /**
     * Test of dumpInputStreamToMap method, of class IOUtil.
     */
    @Test
    public void testDumpInputStreamToMap() throws Exception {
        System.out.println("dumpInputStreamToMap");
        InputStream in = null;
        Map<Integer, String> expResult = null;

        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("testFile.log");
        Map<Integer, String> result = IOUtil.dumpInputStreamToMap(is);
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("abc", result.get(0));
        assertEquals("def", result.get(1));
        assertEquals("ghi", result.get(2));
        result = IOUtil.dumpInputStreamToMap(null);
        assertEquals(0, result.size());

    }

    /**
     * Test of dumpInputStreamToString method, of class IOUtil.
     */
    @Test
    public void testDumpInputStreamToString() {
        System.out.println("dumpInputStreamToString");

        String expResult = "<html></html>";
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("testFile.log");

        String result = IOUtil.dumpInputStreamToString(null);
        assertEquals(expResult, result);
        result = IOUtil.dumpInputStreamToString(is);
        assertNotNull("testFile.log is not empty", result);
        assertTrue("start with <html> tag", result.startsWith("<html>"));
        assertTrue("ends with <html> tag", result.endsWith("</html>"));

        int i = result.indexOf("<br>", 0);
        assertNotEquals(-1, i);
        i = result.indexOf("<br>", i + 1);
        assertNotEquals(-1, i);
        i = result.indexOf("<br>", i + 1);
        assertNotEquals(-1, i);
        i = result.indexOf("<br>", i + 1);
        assertEquals(-1, i);

    }

    /**
     * Test of writeToPrintWriter method, of class IOUtil.
     */
    @Test
    public void testWriteToPrintWriter() throws Exception {
        System.out.println("writeToPrintWriter");
        PrintWriter output = new PrintWriter("tmp.log");
        String data = "abc";
        boolean expResult = true;
        boolean result = IOUtil.writeToPrintWriter(output, data);
        assertEquals(expResult, result);
        result = IOUtil.writeToPrintWriter(output, null);
        assertEquals(false, result);

    }

    /**
     * Test of writeToStream method, of class IOUtil.
     */
    @Test
    public void testWriteToStream() throws Exception {
        System.out.println("writeToStream");
        FileOutputStream output = new FileOutputStream("tmp.log");
        String data = "abc";
        boolean expResult = true;
        boolean result = IOUtil.writeToStream(output, data);
        assertEquals(expResult, result);
        result = IOUtil.writeToStream(output, null);
        assertEquals(false, result);

    }

    /**
     * Test of sleep method, of class IOUtil.
     */
    //@Test
    public void testSleep() {
        System.out.println("sleep");
        long thinkTime = 0L;
        IOUtil.sleep(thinkTime);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of copyImage method, of class IOUtil.
     */
    //@Test
    public void testCopyImage() {
        System.out.println("copyImage");
        String from = "";
        String formatName = "";
        File output = null;
        boolean expResult = false;
        boolean result = IOUtil.copyImage(from, formatName, output);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
