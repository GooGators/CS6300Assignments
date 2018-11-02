package edu.gatech.seclass;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;

import static org.junit.Assert.*;

public class WhiteboxClassTestBC3 {

    private WhiteboxClass wc;

    @Before
    public void setUp() throws Exception {
        wc = new WhiteboxClass();
    }

    @After
    public void tearDown() throws Exception {
        wc = null;
    }

    @Test
    public void test1BC3() {
        int expected1 = 0;
        int actual1 = wc.whiteboxMethod3(0,1);
        assertEquals(expected1, actual1);
    }

    @Test
    public void test2BC3() {
        int expected2 = 5;
        int actual2 = wc.whiteboxMethod3(3, 1);
        assertEquals(expected2, actual2);
    }

    @Test
    public void test3BC3() {
        int expected3 = 4;
        int actual3 = wc.whiteboxMethod3(-6, -2);
        assertEquals(expected3, actual3);
    }

    @Test
    public void test4BC3() {
        int expected4 = 0;
        int actual4 = wc.whiteboxMethod3(0, 0);
        assertEquals(expected4, actual4);
    }
}
