package edu.gatech.seclass;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

public class WhiteboxClassTestSC4b {

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
    public void test1SC4b() {
        int expected1 = 1;
        int actual1 = wc.whiteboxMethod4(3);
        assertEquals(expected1, actual1);
    }

    @Test
    public void test2SC4b() {
        int expected2 = 0;
        int actual2 = wc.whiteboxMethod4(0);
        assertEquals(expected2, actual2);
    }
}