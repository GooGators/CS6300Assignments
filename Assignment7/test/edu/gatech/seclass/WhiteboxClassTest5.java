
package edu.gatech.seclass;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;

import static org.junit.Assert.*;

public class WhiteboxClassTest5 {


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
    public void test1() {
        boolean expected1 = true;
        boolean actual1 = wc.whiteboxMethod5(true, true);
        assertEquals(expected1, actual1);
    }

    @Test
    public void test2() {
        boolean expected1 = false;
        boolean actual1 = wc.whiteboxMethod5(true, false);
        assertEquals(expected1, actual1);
    }

    @Test
    public void test3() {
        boolean expected1 = false;
        boolean actual1 = wc.whiteboxMethod5(false, true);
        assertEquals(expected1, actual1);
    }

    @Test
    public void test4() {
        boolean expected1 = true;
        boolean actual1 = wc.whiteboxMethod5(false, false);
        assertEquals(expected1, actual1);
    }

}