/**
 * This file is part of impensa. CopyLeft (C) BigBang<->BigCrunch.All Rights are
 * left.
 *
 * 1) Modify it if you can understand. 2) If you distribute a modified version,
 * you must do it at your own risk.
 *
 *
 */
package org.commons.file;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
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
public class FileUtilTest {

    public FileUtilTest() {
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
     * Test of dumpFileToMap method, of class FileUtil.
     */
    @Test
    public void testDumpFileToMap_File() {
        System.out.println("dumpFileToMap");
        File scriptFile = null;
        try {
            FileUtil.dumpFileToMap(scriptFile);
        } catch (IllegalArgumentException iae) {
            assertEquals("null file", iae.getMessage());
        }
        scriptFile = new File("/c/source/test.data");
        Map<Integer, String> result = FileUtil.dumpFileToMap(scriptFile);
        assertNull(result);

        scriptFile = new File("src/test/test.data");
        result = FileUtil.dumpFileToMap(scriptFile);
        assertNotNull(result);
        assertEquals("abc", result.get(0));
        assertEquals("def", result.get(1));

    }

    /**
     * Test of dumpFileToMap method, of class FileUtil.
     */
    @Test
    public void testDumpFileToMap_String() {
        System.out.println("dumpFileToMap");
        String scriptFile = "";
        try {
            FileUtil.dumpFileToMap(scriptFile);
        } catch (IllegalArgumentException iae) {
            assertEquals("null file", iae.getMessage());
        }
        scriptFile = "/c/source/test.data";
        Map<Integer, String> result = FileUtil.dumpFileToMap(scriptFile);
        assertNull(result);

        scriptFile = "src/test/test.data";
        result = FileUtil.dumpFileToMap(scriptFile);
        assertNotNull(result);
        assertEquals("abc", result.get(0));
        assertEquals("def", result.get(1));
    }

    /**
     * Test of dumpStringToMap method, of class FileUtil.
     */
    @Test
    public void testDumpStringToMap() {
        System.out.println("dumpStringToMap");
        try {
            FileUtil.dumpStringToMap(null);
        } catch (IllegalArgumentException iae) {
            assertEquals("null filedata", iae.getMessage());
        }
        try {
            FileUtil.dumpStringToMap("");
        } catch (IllegalArgumentException iae) {
            assertEquals("null filedata", iae.getMessage());
        }
        try {
            FileUtil.dumpStringToMap(" ");
        } catch (IllegalArgumentException iae) {
            assertEquals("null filedata", iae.getMessage());
        }

        String scriptFileData = "dumpStringToMap";
        Map<Integer, String> expResult = null;
        Map<Integer, String> result = FileUtil.dumpStringToMap(scriptFileData);
        assertNotNull(result);
        assertEquals(scriptFileData, result.get(0));

    }

    /**
     * Test of readFileAsStream method, of class FileUtil.
     */
    @Test
    public void testReadFileAsStream_String() {
        System.out.println("readFileAsStream");
        String fileName = "";
        Reader expResult = null;
        try {
            FileUtil.readFileAsStream(" ");
        } catch (IllegalArgumentException iae) {
            assertEquals("Invalid fileName", iae.getMessage());
        }
        try {
            fileName = null;
            FileUtil.readFileAsStream(fileName);
        } catch (IllegalArgumentException iae) {
            assertEquals("Invalid fileName", iae.getMessage());
        }
        fileName = "src/test/test.data";
        Reader result = FileUtil.readFileAsStream(fileName);
        assertNotNull(result);

    }

    /**
     * Test of readFileAsStream method, of class FileUtil.
     */
    @Test
    public void testReadFileAsStream_File() {
        System.out.println("readFileAsStream");
        File fileName = null;
        Reader expResult = null;
        try {
            FileUtil.readFileAsStream(fileName);
        } catch (IllegalArgumentException iae) {
            assertEquals("Invalid fileName", iae.getMessage());
        }
        fileName = new File("src/test/test.data1");
        Reader result = null;
        try {
            result = FileUtil.readFileAsStream(fileName);
        } catch (IllegalArgumentException iae) {
            assertNull(result);
        }

        fileName = new File("src/test/test.data");
        result = FileUtil.readFileAsStream(fileName);
        assertNotNull(result);
    }

    /**
     * Test of readFile method, of class FileUtil.
     */
    @Test
    public void testReadFile_String_boolean() {
        System.out.println("readFile");
        String fileName = "";
        try {
            FileUtil.readFile(fileName, true);
        } catch (IllegalArgumentException iae) {
            assertEquals("Invalid fileName", iae.getMessage());
        }
        boolean readFromClazzpath = false;
        fileName = "src/test/test.data";
        String result = FileUtil.readFile(fileName, readFromClazzpath);
        assertNotNull(result);

        fileName = "src/test/test.data1";

        result = FileUtil.readFile(fileName, readFromClazzpath);
        assertNull(result);

      
    }

    /**
     * Test of readFile method, of class FileUtil.
     */
    //@Test
    public void testReadFile_File() {
        System.out.println("readFile");
        File fileName = null;
        String expResult = "";
        String result = FileUtil.readFile(fileName);
        assertEquals(expResult, result);
       
    }

    /**
     * Test of readFile method, of class FileUtil.
     */
    //@Test
    public void testReadFile_String() {
        System.out.println("readFile");
        String fileName = "";
        String expResult = "";
        String result = FileUtil.readFile(fileName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of diff method, of class FileUtil.
     */
    //@Test
    public void testDiff_3args_1() {
        System.out.println("diff");
        File f1 = null;
        File f2 = null;
        File diffFile = null;
        boolean expResult = false;
        boolean result = FileUtil.diff(f1, f2, diffFile);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of diff method, of class FileUtil.
     */
    //@Test
    public void testDiff_3args_2() {
        System.out.println("diff");
        String file1 = "";
        String file2 = "";
        String diffFileStr = "";
        boolean expResult = false;
        boolean result = FileUtil.diff(file1, file2, diffFileStr);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of writeFile method, of class FileUtil.
     */
    //@Test
    public void testWriteFile_String_String() {
        System.out.println("writeFile");
        String filePath = "";
        String data = "";
        boolean expResult = false;
        boolean result = FileUtil.writeFile(filePath, data);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of writeFile method, of class FileUtil.
     */
    //@Test
    public void testWriteFile_File_String() {
        System.out.println("writeFile");
        File filePath = null;
        String data = "";
        boolean expResult = false;
        boolean result = FileUtil.writeFile(filePath, data);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of writeFile method, of class FileUtil.
     */
    //@Test
    public void testWriteFile_3args_1() {
        System.out.println("writeFile");
        File file = null;
        String data = "";
        boolean append = false;
        boolean expResult = false;
        boolean result = FileUtil.writeFile(file, data, append);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of writeFile method, of class FileUtil.
     */
    //@Test
    public void testWriteFile_3args_2() {
        System.out.println("writeFile");
        String filePath = "";
        String data = "";
        boolean append = false;
        boolean expResult = false;
        boolean result = FileUtil.writeFile(filePath, data, append);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createPrintWriter method, of class FileUtil.
     */
    //@Test
    public void testCreatePrintWriter() {
        System.out.println("createPrintWriter");
        File file = null;
        PrintWriter expResult = null;
        PrintWriter result = FileUtil.createPrintWriter(file);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createStream method, of class FileUtil.
     */
    //@Test
    public void testCreateStream() {
        System.out.println("createStream");
        File file = null;
        OutputStream expResult = null;
        OutputStream result = FileUtil.createStream(file);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of remove method, of class FileUtil.
     */
    //@Test
    public void testRemove() {
        System.out.println("remove");
        String fileStr = "";
        boolean expResult = false;
        boolean result = FileUtil.remove(fileStr);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of copy method, of class FileUtil.
     */
    //@Test
    public void testCopy() {
        System.out.println("copy");
        String srcFileStr = "";
        String destDirStr = "";
        boolean expResult = false;
        boolean result = FileUtil.copy(srcFileStr, destDirStr);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of exists method, of class FileUtil.
     */
    //@Test
    public void testExists() {
        System.out.println("exists");
        String inputFile = "";
        boolean expResult = false;
        boolean result = FileUtil.exists(inputFile);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
