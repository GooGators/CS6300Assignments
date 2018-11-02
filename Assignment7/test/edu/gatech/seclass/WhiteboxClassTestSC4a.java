package edu.gatech.seclass;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WhiteboxClassTestSC4a {

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
    public void test1SC4a() {
        int expected1 = 1;
        int actual1 = wc.whiteboxMethod4(-1);
        assertEquals(expected1, actual1);
    }
}