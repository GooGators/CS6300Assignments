package edu.gatech.seclass;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static org.junit.Assert.*;

public class WhiteboxClassTestSC2 {

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
    public void test1SC2() {
        int expected1 = 1;
        int actual1 = wc.whiteboxMethod2(-1);
        assertEquals(expected1, actual1);
    }
}