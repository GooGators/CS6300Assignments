package edu.gatech.seclass;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;

import static org.junit.Assert.*;

public class WhiteboxClassTestPC2 {

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
    public void test1PC2() {
        int expected1 = 0;
        int actual1 = wc.whiteboxMethod2(0);
        assertEquals(expected1,actual1);
    }

    @Test
    public void test2PC2() {
        int expected2 = 1;
        int actual2 = wc.whiteboxMethod2(3);
        assertEquals(expected2, actual2);
    }
}