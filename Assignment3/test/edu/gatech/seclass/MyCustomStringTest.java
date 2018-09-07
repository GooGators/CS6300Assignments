package edu.gatech.seclass;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Junit test class created for use in Georgia Tech CS6300.
 *
 * This is an test class for a simple class that represents a string, defined
 * as a sequence of characters.
 *
 * You should implement your tests in this class.  Do not change the test names.
 */

public class MyCustomStringTest {

    private MyCustomStringInterface mycustomstring;

    @Before
    public void setUp() {
        mycustomstring = new MyCustomString();
    }

    @After
    public void tearDown() {
        mycustomstring = null;
    }

    //Test Purpose: This is the first instructor example test.
    @Test
    public void testCountDuplicates1() {
        mycustomstring.setString("People are sleeping... Zzz.");
        assertEquals(4, mycustomstring.countDuplicates());
    }

    //Test Purpose: Testing out string with space duplicates
    @Test
    public void testCountDuplicates2() {
        mycustomstring.setString("H                E       LL O");
        assertEquals( 22, mycustomstring.countDuplicates());
    }

    //Test Purpose: Testing string with 0 duplicates
    @Test
    public void testCountDuplicates3() {
        mycustomstring.setString("John is having fun");
        assertEquals(0 , mycustomstring.countDuplicates());
    }

    //Test Purpose: Testing empty string
    @Test
    public void testCountDuplicates4() {
        mycustomstring.setString("");
        assertEquals(0 , mycustomstring.countDuplicates());
    }

    //Test Purpose: Testing string with repeating 7777777
    @Test
    public void testCountDuplicates5() {
        mycustomstring.setString("7777777");
        assertEquals(6 , mycustomstring.countDuplicates());
    }

    //Test Purpose: Testing complicated string
    @Test
    public void testCountDuplicates6() {
        mycustomstring.setString("ZzzzzZZ  I love to  sleep!! 112223377");
        assertEquals(13 , mycustomstring.countDuplicates());
    }

    //Test Purpose: This is the second instructor example test.
    @Test
    public void testAddDigits1() {
        mycustomstring.setString("1234!!! H3y, l3t'5 put 50me d161ts in this 5tr1n6!11!1");
        assertEquals("5678!!! H7y, l7t'9 put 94me d505ts in this 9tr5n0!55!5", mycustomstring.addDigits(4, true));
    }

    //Test Purpose: This the third instructor example test.
    @Test
    public void testAddDigits2() {
        mycustomstring.setString("1234!!! H3y, l3t'5 put 50me d161ts in this 5tr1n6!11!1");
        assertEquals("7890!!! H9y, l9t'1 put 16me d727ts in this 1tr7n2!77!7", mycustomstring.addDigits(4, false));
    }

    //Test Purpose: This for when setString is null throws NullPointerException
    @Test (expected = NullPointerException.class)
    public void testAddDigits3() {
        mycustomstring.setString(null);
        mycustomstring.addDigits(4, true);
    }

    //Test Purpose: This is for when n > 9 throws IllegalArgumentException
    @Test (expected = IllegalArgumentException.class)
    public void testAddDigits4() {
        mycustomstring.setString("1234!!! H3y, l3t'5 put 50me d161ts in this 5tr1n6!11!1");
        mycustomstring.addDigits(10, true);
    }

    //Test Purpose: This is for when n = 0 throws IllegalArgumentException
    @Test (expected = IllegalArgumentException.class)
    public void testAddDigits5() {
        mycustomstring.setString("1234!!! H3y, l3t'5 put 50me d161ts in this 5tr1n6!11!1");
        mycustomstring.addDigits(0, true);
    }

    //Test Purpose: This is for when n < 0 throws IllegalArgumentException
    @Test (expected = IllegalArgumentException.class)
    public void testAddDigits6() {
        mycustomstring.setString("1234!!! H3y, l3t'5 put 50me d161ts in this 5tr1n6!11!1");
        mycustomstring.addDigits(-1, false);
    }

    //Test Purpose: String with range of values with positive true
    @Test
    public void testAddDigits7() {
        mycustomstring.setString("h4pPy 81r7Hd4Y");
        assertEquals("h8pPy 25r1Hd8Y", mycustomstring.addDigits(4, true));
    }

    //Test Purpose: String example with positive true
    @Test
    public void testAddDigits8() {
        mycustomstring.setString("my N4M3 I2 37H4N WU 4Nd i l0V3 4PPl32");
        assertEquals("my N2M1 I0 15H2N WU 2Nd i l8V1 2PPl10", mycustomstring.addDigits(8, true));
    }

    //Testing Purpose: String example with positive false
    @Test
    public void testAddDigits9() {
        mycustomstring.setString("1F J00r H4Ppy 4Nd j00 Kn0w 17");
        assertEquals("6F J55r H9Ppy 9Nd j55 Kn5w 62", mycustomstring.addDigits(5, false));
    }

    //Testing Purpose: String example with positive false
    @Test
    public void testAddDigits10() {
        mycustomstring.setString("m4Ry H4d 4 l177Le l4M8");
        assertEquals("m8Ry H8d 8 l511Le l8M2", mycustomstring.addDigits(6, false));
    }

    //Testing Purpose: String example with positive false
    @Test
    public void testAddDigits11() {
        mycustomstring.setString("7W1nkL3 7W1nKlw l177l3 s74r");
        assertEquals("0W4nkL6 0W4nKlw l400l6 s07r", mycustomstring.addDigits(7, false));
    }

    //Testing Purpose: String example with positive false
    @Test
    public void testAddDigits12() {
        mycustomstring.setString("h34Dz 5h0UlD3rz kn33Z 4nd 703z");
        assertEquals("h90Dz 1h6UlD9rz kn99Z 0nd 369z", mycustomstring.addDigits(4, false));
    }


    //Test Purpose: This is the fourth instructor example test.
    @Test
    public void testFlipLetttersInSubstring1() {
        mycustomstring.setString("H3y, l3t'5 put 50me D161ts in this 5tr1n6!11!!");
        mycustomstring.flipLetttersInSubstring(18, 30);
        assertEquals("H3y, l3t'5 put 50ni s161tD em this 5tr1n6!11!!", mycustomstring.getString());
    }

    //Test Purpose: This is an instructor example test to demonstrate testing for an exception.
    @Test(expected = NullPointerException.class)
    public void testFlipLetttersInSubstring2() {

        mycustomstring.flipLetttersInSubstring(200, 100);
    }

    //Test Purpose : Testing out when endPosition is greater than the string length
    @Test (expected = MyIndexOutOfBoundsException.class)
    public void testFlipLetttersInSubstring3() { 
        mycustomstring.setString("H3y, l3t'5 put 50me D161ts in this 5tr1n6!11!!");
        mycustomstring.flipLetttersInSubstring(18, 100);
    }

    //Test Purpose: Testing out when startPosition is 0
    @Test (expected = MyIndexOutOfBoundsException.class)
    public void testFlipLetttersInSubstring4() {
        mycustomstring.setString("H3y, l3t'5 put 50me D161ts in this 5tr1n6!11!!");
        mycustomstring.flipLetttersInSubstring(0, 30);
    }

    //Test Purpose: Testing when startPosition < 0
    @Test (expected = MyIndexOutOfBoundsException.class)
    public void testFlipLetttersInSubstring5() {
        mycustomstring.setString("H3y, l3t'5 put 50me D161ts in this 5tr1n6!11!!");
        mycustomstring.flipLetttersInSubstring(-1, 100);
    }

    //Test Purpose: Testing when startPosition > endPosition within bounds
    @Test (expected = IllegalArgumentException.class)
    public void testFlipLetttersInSubstring6() {
        mycustomstring.setString("H3y, l3t'5 put 50me D161ts in this 5tr1n6!11!!");
        mycustomstring.flipLetttersInSubstring(18, 17);
    }

    //Test Purpose: Trying out example single word string
    @Test
    public void testFlipLetttersInSubstring7() {
        mycustomstring.setString("Ethan");
        mycustomstring.flipLetttersInSubstring(1, 5);
        assertEquals("nahtE", mycustomstring.getString());
    }

    //Test Purpose: Trying out example string
    @Test
    public void testFlipLetttersInSubstring8() {
        mycustomstring.setString("3 Cats, 2 Dogs.");
        mycustomstring.flipLetttersInSubstring(1, 14);
        assertEquals("3 sgoD, 2 staC.", mycustomstring.getString());
    }

    //Test Purpose: Trying out example string
    @Test
    public void testFlipLetttersInSubstring9() {
        mycustomstring.setString("73Ll M3H 7h47 u L0V3 M3H");
        mycustomstring.flipLetttersInSubstring(5, 16);
        assertEquals("73Ll u3h 7H47 M L0V3 M3H", mycustomstring.getString());
    }

    //Test Purpose: Trying out an empty string with incorrect bounds should result in MyIndexOutofBounds
    @Test (expected = MyIndexOutOfBoundsException.class)
    public void testFlipLetttersInSubstring10() {
        mycustomstring.setString("");
        mycustomstring.flipLetttersInSubstring(0, 1);
    }
}
