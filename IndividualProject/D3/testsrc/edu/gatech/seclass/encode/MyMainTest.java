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
        File file = createTmpFile();

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
    private static final String FILE4 = "";
    private static final String FILE5 = " ";

    // test cases

    /*
     *   TEST CASES
     */

    // Purpose: -n Test Case Error for integer greater than 25
    // Frame #: 6
    // Failure Type: Bug#8 encode fails when optional integer is not a value between 0 and 25
    @Test
    public void encodeTest1() throws Exception {
        File inputFile1 = createInputFile(FILE3);
        String args[] = {"-n", "26", inputFile1.getPath()};
        Main.main(args);
        assertEquals("Usage: encode [-n [int]] [-r int | -l int] [-c string] [-d int] <filename>", errStream.toString().trim());
    }

    // Purpose: Testing for -c <string> -n<integer> operation
    // Frame #: 27
    @Test
    public void encodeTest2() throws Exception {
        File inputFile2 = createInputFile(FILE1);
        String args[] = {"-n", "1", "-c", "ab", inputFile2.getPath()};
        Main.main(args);
        String expected2 = "282904252601";
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
    // Failure Type: Test Failure
    @Test
    public void encodeTest4() throws Exception {
        File inputFil4 = createInputFile(FILE1);
        String args[] = {"-c", "?!", inputFil4.getPath()};
        Main.main(args);
        String expected = "abcxyz";
        String actual = getFileContent(inputFil4.getPath());
        assertEquals("The files differ", expected, actual);
    }

    // Purpose: Testing for -r <integer> no integer error (if -r || -l present no integer error)
    // Frame #: 8
    @Test
    public void encodeTest5() throws Exception {
        File inputFile5 = createInputFile(FILE1);
        String args[] = {"-r", inputFile5.getPath()};
        Main.main(args);
        assertEquals("Usage: encode [-n [int]] [-r int | -l int] [-c string] [-d int] <filename>", errStream.toString().trim());

    }

    // Purpose: Testing for -r -l mutual exclusivity (cannot exist at the same time)
    // Frame #: 7
    @Test
    public void encodeTest6() throws Exception {
        File inputFile6 = createInputFile(FILE1);
        String args[] = {"-r", "5", "-l", "5", inputFile6.getPath()};
        Main.main(args);
        assertEquals("Usage: encode [-n [int]] [-r int | -l int] [-c string] [-d int] <filename>", errStream.toString().trim());

    }

    // Purpose: Testing for -c <string> missing string input error
    // Frame #: 12
    @Test
    public void encodeTest7() throws Exception {
        File inputFile7 = createInputFile(FILE1);
        String args[] = {"-c", inputFile7.getPath()};
        Main.main(args);
        assertEquals("Usage: encode [-n [int]] [-r int | -l int] [-c string] [-d int] <filename>", errStream.toString().trim());
    }

    // Purpose: Testing for -n without optional integer value (integer value set to 13)
    // Frame #: 48
    @Test
    public void encodeTest8() throws Exception {
        File inputFile8 = createInputFile(FILE2);
        String args[] = {"-n", "-r", "1", inputFile8.getPath()};
        Main.main(args);
        String expected8 = ",3415230425 2809121225\n" +
                ".35 0113 0715091407 2015 20011105 03196300 011404 03196400 14052420 1905130519200518\n" +
                "15300904 251521 20011105 0319 6300 12011920 1905130519200518? 35 23011420 20\n" +
                "!20011105 2 03152118190519 1915 20080120 35 23091212 0718010421012005 27190116";
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
                "DId you tAkE cs 6300 lAst sEmEstEr? i wAnt to\n" +
                "tAkE 2 coursEs so thAt i wIll grAduAtE asAp!";
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
        String expected11 = "042526012829";
        String actual11 = getFileContent(inputFile11.getPath());
        assertEquals("The files differ!", expected11, actual11);
    }

    // Purpose: Testing all OPT tags utilized with no optional integer for -n
    // Frame #: 29
    @Test
    public void encodeTest12() throws Exception {
        File inputFile12 = createInputFile(FILE1);
        String args[] = {"-n", "-c", "a", "-l", "2", inputFile12.getPath()};
        Main.main(args);
        String expected12 = "032425262702";
        String actual12 = getFileContent(inputFile12.getPath());
        assertEquals("The files differ!", expected12, actual12);
    }

    // Purpose: Testing for valid integer input error for -r, -l operations (integer value set to 0)
    // Frame #: 9
    // Failure Type: Bug#1 encode fails when integer value is not greater than or equal to 1
    @Test
    public void encodeTest13() throws Exception {
        File inputFile13 = createInputFile(FILE2);
        String args[] = {"-r", "0", inputFile13.getPath()};
        Main.main(args);
        assertEquals("Usage: encode [-n [int]] [-r int | -l int] [-c string] [-d int] <filename>", errStream.toString().trim());
    }

    // Purpose: Testing for edge case int = 0 for -n, ERROR
    // Frame #: 4
    // Failure Type: Bug#8 encode fails when optional integer is not a value between 0 and 25
    @Test
    public void encodeTest14() throws Exception {
        File inputFile14 = createInputFile(FILE3);
        String args[] = {"-n", "-1", inputFile14.getPath()};
        Main.main(args);
        assertEquals("Usage: encode [-n [int]] [-r int | -l int] [-c string] [-d int] <filename>", errStream.toString().trim());
    }

    // Purpose: Error case for File not corresponding to filename
    // Frame #: 21
    @Test
    public void mainTest15() throws Exception {

        String args[] = {"-n", "File12.txt"};
        Main.main(args);

        assertEquals("File Not Found", errStream.toString().trim());
    }

    // Purpose: Testing for No Words in File
    // Frame #: 2
    // Failure Type: Bug#7 encode because it doesn't account for condition where no integer is inputted for -n operation
    @Test
    public void encodeTest16() throws Exception {
        File inputFile16 = createInputFile(FILE4);
        String args[] = {"-n", inputFile16.getPath()};
        Main.main(args);
        String expected16 = "";
        String actual16 = getFileContent(inputFile16.getPath());
        assertEquals(expected16, actual16);
    }

    // Purpose: Testing for 0 integer input for -n
    // Frame #: 3
    @Test
    public void encodeTest17() throws Exception {
        File inputFile17 = createInputFile(FILE1);
        String args[] = {"-n", "0", inputFile17.getPath()};
        Main.main(args);
        String expected17 = "010203242526";
        String actual17 = getFileContent((inputFile17.getPath()));
        assertEquals("Files differ!", expected17, actual17);
    }

    // Purpose: Testing for invalid integer input less than 0 -n
    // Frame #: 44
    @Test
    public void encodeTest18() throws Exception {
        File inputFile18 = createInputFile(FILE2);
        String args[] = {"-n", "10", "-l", "1", inputFile18.getPath()};
        Main.main(args);
        String expected18 = "25071409 3819222209,44\n" +
                " 1123 1725192417 0425 04112115 13036300 112414 13036400 24150804 0315231503041502.45\n" +
                "1914 092505 04112115 1303 6300 22110304 0315231503041502? 45 07112404 042540\n" +
                "112115 2 13250502031503 0325 04181104 45 07192222 1702111405110415 37031126!04";
        String actual18 = getFileContent(inputFile18.getPath());
        assertEquals("Files differ!", expected18, actual18);

    }

    // Purpose: Testing for invalid integer input less than 0 -r
    // Frame #: 10
    // Failure Type: Bug#1 encode fails when integer value is not greather than or equal to 1
    @Test
    public void encodeTest19() throws Exception {
        File inputFile19 = createInputFile(FILE1);
        String args[] = {"-r", "-5", inputFile19.getPath()};
        Main.main(args);
        assertEquals("Usage: encode [-n [int]] [-r int | -l int] [-c string] [-d int] <filename>", errStream.toString().trim());
    }

    // Purpose: Testing for -c with string length of 1
    // Frame #: 14
    @Test
    public void encodeTest20() throws Exception {
        File inputFile20 = createInputFile(FILE1);
        String args[] = {"-c", "a", inputFile20.getPath()};
        Main.main(args);
        String expected20 = "Abcxyz";
        String actual20 = getFileContent(inputFile20.getPath());
        assertEquals("The files differ", expected20, actual20);
    }

    // Purpose: Testing for only spaces in string input for -c
    // Frame #: 16
    // Failure Type: Test Failure
    @Test
    public void encodeTest21() throws Exception {
        File inputFile21 = createInputFile(FILE1);
        String args[] = {"-c", "   ", inputFile21.getPath()};
        Main.main(args);

        String expected21 = "abcxyz";
        String actual21 = getFileContent(inputFile21.getPath());
        assertEquals("The files differ", expected21, actual21);
    }

    // Purpose: Testing for alphanumeric characters in string input for -c
    // Frame #: 18
    // Failure Type: Test Failure
    @Test
    public void encodeTest22() throws Exception {
        File inputFile22 = createInputFile(FILE1);
        String args[] = {"-c", "ab123cd", inputFile22.getPath()};
        Main.main(args);
        String expected22 = "ABCxyz";
        String actual22 = getFileContent(inputFile22.getPath());
        assertEquals("The files differ", expected22, actual22);
    }

    // Purpose: Testing for duplicate characters in string input for -c
    // Frame #: 19
    // Failure Type: Test Failure
    @Test
    public void encodeTest23() throws Exception {
        File inputFile23 = createInputFile(FILE1);
        String args[] = {"-c", "aaaaaa", inputFile23.getPath()};
        Main.main(args);
        String expected23 = "Abcxyz";
        String actual23 = getFileContent(inputFile23.getPath());
        assertEquals("The files differ", expected23, actual23);
    }

    // Purpose: One Word File, -n <int> >0, -l <int> >0
    // Frame #: 26
    @Test
    public void encodeTest24() throws Exception {
        File inputFile24 = createInputFile(FILE1);
        String args[] = {"-n", "1", "-l", "1", inputFile24.getPath()};
        Main.main(args);
        String expected24 = "030425260102";
        String actual24 = getFileContent(inputFile24.getPath());
        assertEquals("The files differ", expected24, actual24);
    }

    // Purpose: One Word File, -n <int> >0
    // Frame #: 28
    @Test
    public void encodeTest25() throws Exception {
        File inputFile25 = createInputFile(FILE1);
        String args[] = {"-n", "1", inputFile25.getPath()};
        Main.main(args);
        String expected25 = "020304252601";
        String actual25 = getFileContent(inputFile25.getPath());
        assertEquals("The files differ!", expected25, actual25);
    }

    // Purpose: One Word File, -n no optional integer, -r <int>
    // Frame #: 30
    @Test
    public void encodeTest26() throws Exception {
        File inputFile26 = createInputFile(FILE1);
        String args[] = {"-n", "-r", "1", inputFile26.getPath()};
        Main.main(args);
        String expected26 = "260102032425";
        String actual26 = getFileContent(inputFile26.getPath());
        assertEquals("The files differ!", expected26, actual26);
    }

    // Purpose: One Word File, all parameters, -n with no optional integer
    // Frame #: 31
    @Test
    public void encodeTest27() throws Exception {
        File inputFile27 = createInputFile(FILE1);
        String args[] = {"-n", "-r", "1", "-c", "abc", inputFile27.getPath()};
        Main.main(args);
        String expected27 = "262728292425";
        String actual27 = getFileContent(inputFile27.getPath());
        assertEquals("The files differ!", expected27, actual27);
    }

    // Purpose: One word file, -n with no integer, -1 <int>
    // Frame #: 32
    @Test
    public void encodeTest28() throws Exception {
        File inputFile28 = createInputFile(FILE1);
        String args[] = {"-n", "-l", "1", inputFile28.getPath()};
        Main.main(args);
        String expected28 = "020324252601";
        String actual28 = getFileContent(inputFile28.getPath());
        assertEquals("The files differ!", expected28, actual28);
    }

    // Purpose: One word file, -n with no integer, -c<string>
    // Frame #: 33
    @Test
    public void encodeTest29() throws Exception {
        File inputFile29 = createInputFile(FILE1);
        String args[] = {"-n", "-c", "abc", inputFile29.getPath()};
        Main.main(args);
        String expected29 = "272829242526";
        String actual29 = getFileContent(inputFile29.getPath());
        assertEquals("The files differ!", expected29, actual29);
    }

    // Purpose: One word with -c and -r paramters
    // Frame #: 35
    @Test
    public void encodeTest30() throws Exception {
        File inputFIle30 = createInputFile(FILE1);
        String args[] = {"-c", "ab", "-r", "1", inputFIle30.getPath()};
        Main.main(args);
        String expected30 = "zABcxy";
        String actual30 = getFileContent(inputFIle30.getPath());
        assertEquals("The files differ!", expected30, actual30);
    }

    // Purpose: Testing for repetitive -r tag
    // New Failure Type: BUG#3: encode fails when multiple instances of -r tags is used
    @Test
    public void encodeTest31() throws Exception {
        File inputFile31 = createInputFile(FILE1);
        String args[] = {"-r", "1", "-r", "1", inputFile31.getPath()};
        Main.main(args);
        assertEquals("Usage: encode [-n [int]] [-r int | -l int] [-c string] [-d int] <filename>", errStream.toString().trim());
    }

    // Purpose: Testing for -d tag with string input
    // New Failure Type: BUG#5: encode fails when string is inputted instead of integer
    @Test
    public void encodeTest32() throws Exception {
        File inputFile32 = createInputFile(FILE1);
        String args[] = {"-d", "abc", inputFile32.getPath()};
        Main.main(args);
        assertEquals("Usage: encode [-n [int]] [-r int | -l int] [-c string] [-d int] <filename>", errStream.toString().trim());
    }

    // Purpose: Testing -d tag on empty file
    // New Failure Type: BUG#4: encode fails -d operation is run on file that doesn't have at least the integer value of characters.
    @Test
    public void encodeTest33() throws Exception {
        File inputFile33 = createInputFile(FILE4);
        String args[] = {"-d", "1", inputFile33.getPath()};
        Main.main(args);
        assertEquals("Usage: encode [-n [int]] [-r int | -l int] [-c string] [-d int] <filename>", errStream.toString().trim());
    }

    // Purpose: Testing -r tag with multiple integer inputs
    // New Failure Type: BUG#6: encode fails when operation with required integer is run with multiple integer inputs
    @Test
    public void encodeTest34() throws Exception {
        File inputFile34 = createInputFile(FILE1);
        String args[] = {"-r", "1", "2", inputFile34.getPath()};
        Main.main(args);
        assertEquals("Usage: encode [-n [int]] [-r int | -l int] [-c string] [-d int] <filename>", errStream.toString().trim());
    }

    // Purpose: Testing -c tag with repeating string (cap/non-cap)
    // New Failure Type: BUG#9: encode fails when -c operation is run with repeating string value Cap/non-cap because it does the operation twice.
    @Test
    public void encodeTest35() throws Exception {
        File inputFile35 = createInputFile(FILE1);
        String args[] = {"-c", "Aa", inputFile35.getPath()};
        Main.main(args);
        assertEquals("Usage: encode [-n [int]] [-r int | -l int] [-c string] [-d int] <filename>", errStream.toString().trim());
    }

    // Purpose: Testing -c tag with repeating string (cap/non-cap)
    // New Failure Type: BUG#2: encode fails when -c operation is run with an empty string input.
    @Test
    public void encodeTest36() throws Exception {
        File inputFile36 = createInputFile(FILE1);
        String args[] = {"-c", "", inputFile36.getPath()};
        Main.main(args);
        assertEquals("Usage: encode [-n [int]] [-r int | -l int] [-c string] [-d int] <filename>", errStream.toString().trim());
    }

    // Purpose: Testing -r on files with repeating \n and \r
    // New Failure Type: BUG#10: encode failes when -r operation is run on files with multiple return lines
    @Test
    public void encodeTest37() throws Exception {
        File inputFile37 = createInputFile("Hi my name is Ethan\n\n + I am a boy\r\r");
        String args[] = {"-r", "10", inputFile37.getPath()};
        Main.main(args);
        assertEquals("Usage: encode [-n [int]] [-r int | -l int] [-c string] [-d int] <filename>", errStream.toString().trim());
    }

}