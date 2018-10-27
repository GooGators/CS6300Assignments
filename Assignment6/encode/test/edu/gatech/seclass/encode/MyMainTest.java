package edu.gatech.seclass.encode;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MyMainTest {
	
/*
Place all  of your tests in this class, optionally using MainTest.java as an example.
*/

    private ByteArrayOutputStream outStream;
    private ByteArrayOutputStream errStream;
    private PrintStream outOrig;
    private PrintStream errOrig;
    private Charset charset = StandardCharsets.UTF_8;

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Before
    public void setUp() throws Exception {
        outStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outStream);
        errStream = new ByteArrayOutputStream();
        PrintStream err = new PrintStream(errStream);
        outOrig = System.out;
        errOrig = System.err;
        System.setOut(out);
        System.setErr(err);
    }

    @After
    public void tearDown() throws Exception {
        System.setOut(outOrig);
        System.setErr(errOrig);
    }

    /*
     *  TEST UTILITIES
     */

    // Create File Utility
    private File createTmpFile() throws Exception {
        File tmpfile = temporaryFolder.newFile();
        tmpfile.deleteOnExit();
        return tmpfile;
    }

    // Write File Utility
    private File createInputFile(String input) throws Exception {
        File file =  createTmpFile();

        OutputStreamWriter fileWriter =
                new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);

        fileWriter.write(input);

        fileWriter.close();
        return file;
    }


    //Read File Utility
    private String getFileContent(String filename) {
        String content = null;
        try {
            content = new String(Files.readAllBytes(Paths.get(filename)), charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    /*
     * TEST FILE CONTENT
     */
    private static final String FILE1 = "abcxyz";
    private static final String FILE2 = "Howdy Billy,\n" +
            "I am going to take cs6300 and cs6400 next semester.\n" +
            "Did you take cs 6300 last semester? I want to\n" +
            "take 2 courses so that I will graduate Asap!";
    private static final String FILE3 = "abc123";

    // test cases

    /*
     *   TEST CASES
     */

    // Purpose: -n Test Case Error for integer greater than 25
    // Frame #: 6
    @Test
    public void encodeTest1() throws Exception {
        File inputFile1 = createInputFile(FILE3);
        String args[] = {"-n", "26", inputFile1.getPath()};
        Main.main(args);
        assertEquals("-n integer value greater than 25", errStream.toString().trim());
    }

    // Purpose: Testing for -c <string> -n<integer> operation
    // Frame #: 27
    @Test
    public void encodeTest2() throws Exception {
        File inputFile2 = createInputFile(FILE1);
        String args[] = {"-n", "1", "-c", "ab", inputFile2.getPath()};
        Main.main(args);
        String expected2 = "020304252601";
        String actual2 = getFileContent(inputFile2.getPath());
        assertEquals("The files differ!", expected2, actual2);
    }

    // Purpose: Testing for -l <integer>
    // Frame #: 38
    @Test
    public void encodeTest3() throws Exception {
        File inputFile3 = createInputFile(FILE1);
        String args[] = {"-l", "1", inputFile3.getPath()};
        Main.main(args);
        String expected3 = "bcxyza";
        String actual3 = getFileContent(inputFile3.getPath());
        assertEquals("The files differ!", expected3, actual3);
    }

    // Purpose: Testing for -c <string> special characters error
    // Frame #: 17
    @Test
    public void encodeTest4() throws Exception {
        File inputFil4 = createInputFile(FILE1);
        String args[] = {"-c", "??!!!", inputFil4.getPath()};
        Main.main(args);
        assertEquals("-c string input cannot contain special characters", errStream.toString().trim());

    }

    // Purpose: Testing for -r <integer> no integer error (if -r || -l present no integer error)
    // Frame #: 8
    @Test
    public void encodeTest5() throws Exception {
        File inputFile5 = createInputFile(FILE1);
        String args[] = {"-r", inputFile5.getPath()};
        Main.main(args);
        assertEquals("Integer input missing", errStream.toString().trim());

    }

    // Purpose: Testing for -r -l mutual exclusivity (cannot exist at the same time)
    // Frame #: 7
    @Test
    public void encodeTest6() throws Exception {
        File inputFile6 = createInputFile(FILE1);
        String args[] = {"-r", "5", "-l", "5", inputFile6.getPath()};
        Main.main(args);
        assertEquals("-r & -l are mutually exculsive and cannot occur simultaneously", errStream.toString().trim());

    }

    // Purpose: Testing for -c <string> missing string input error
    // Frame #: 12
    @Test
    public void encodeTest7() throws Exception {
        File inputFile7 = createInputFile(FILE1);
        String args[] = {"-c", inputFile7.getPath()};
        Main.main(args);
        assertEquals("String input missing", errStream.toString().trim());
    }

    // Purpose: Testing for -n without optional integer value (integer value set to 13)
    // Frame #: 34
    @Test
    public void encodeTest8() throws Exception {
        File inputFile8 = createInputFile(FILE1);
        String args[] = {"-n", inputFile8.getPath()};
        Main.main(args);
        String expected8 = "141516111213";
        String actual8 = getFileContent(inputFile8.getPath());
        assertEquals("The files differ!", expected8, actual8);
    }

    // Purpose: Testing for unique case string containing Capital Letters (assume that method ignores captialization and does modification all the same)
    // Frame #: 20
    @Test
    public void encodeTest9() throws Exception {
        File inputFile9 = createInputFile(FILE2);
        String args[] = {"-c", "AeI", inputFile9.getPath()};
        Main.main(args);
        String expected9 = "Howdy BIlly,\n" +
                "i Am goIng to tAkE cs6300 And cs6400 nExt sEmEstEr.\n" +
                "DId you tAke cs 6300 lAst sEmEstEr? i wAnt to\n" +
                "tAkE 2 coursEs so that i wIll grAduAtE asAp!";
        String actual9 = getFileContent(inputFile9.getPath());
        assertEquals("The files differ!", expected9, actual9);
    }

    // Purpose: Testing for -c <string> with string length max length 26 (assume that program works converting just matching characters)
    // Frame #: 15
    @Test
    public void encodeTest10() throws Exception {
        File inputFile10 = createInputFile(FILE3);
        String args[] = {"-c", "abcdefghijklmnopqrstuvwxyz", inputFile10.getPath()};
        Main.main(args);
        String expected10 = "ABC123";
        String actual10 = getFileContent(inputFile10.getPath());
        assertEquals("The files differ!", expected10, actual10);
    }

    // Purpose: Testing for all OPT tags utilized
    // Frame #: 23
    @Test
    public void encodeTest11() throws Exception {
        File inputFile11 = createInputFile(FILE1);
        String args[] = {"-n", "1", "-c", "ab", "-l", "2", inputFile11.getPath()};
        Main.main(args);
        String expected11 = "042526010203";
        String actual11 = getFileContent(inputFile11.getPath());
        assertEquals("The files differ!", expected11, actual11);
    }

    // Purpose: Testing all OPT tags utilized with no optional integer for -n
    // Frame #: 29
    @Test
    public void encodeTest12() throws Exception {
        File inputFile12 = createInputFile(FILE1);
        String args[] = {"-n", "-c", "a", "-1", "2", inputFile12.getPath()};
        Main.main(args);
        String expected12 = "171213141516";
        String actual12 = getFileContent(inputFile12.getPath());
        assertEquals("The files differ!", expected12, actual12);
    }

    // Purpose: Testing for valid integer input error for -r, -l operations (integer value set to 0)
    // Frame #: 9
    @Test
    public void encodeTest13() throws Exception {
        File inputFile13 = createInputFile(FILE2);
        String args[] = {"-r", "0", inputFile13.getPath()};
        Main.main(args);
        assertEquals("Integer value not accepted. Integer must be greater than or equal to 1", errStream.toString().trim());
    }

    // Purpose: Testing for edge case maxint = 25 for -n
    // Frame #: 5
    @Test
    public void encodeTest14() throws Exception {
        File inputFile14 = createInputFile(FILE3);
        String args[] = {"-n", "25", inputFile14.getPath()};
        Main.main(args);
        String expected14 = "260102123";
        String actual14 = getFileContent(inputFile14.getPath());
        assertEquals("The files differ!", expected14, actual14);
    }

    // Purpose: Error case for File not corresponding to filename (there is no FILE4)
    // Frame #: 21
    @Test
    public void encodeTest15() throws Exception {
        File inputFile15 = createInputFile(FILE4);
        String args[] = {inputFile15.getPath()};
        Main.main(args);
        assertEquals("File name not present", errStream.toString().trim());
    }
}

