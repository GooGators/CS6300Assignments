package edu.gatech.seclass;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;

import static org.junit.Assert.*;

public class WhiteboxClassTestSC3 {
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
    public void test1SC3() {
        int expected1 = 5;
        int actual1 = wc.whiteboxMethod3(3, 1);
        assertEquals(expected1, actual1);
    }

    @Test
    public void test2SC3() {
        int expected2 = 4;
        int actual2 = wc.whiteboxMethod3(-6, - 2);
        assertEquals(expected2, actual2);
    }
}