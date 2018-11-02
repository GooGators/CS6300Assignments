package edu.gatech.seclass;

public class WhiteboxClass {

    public void whiteboxMethod1() {
        //Creating method for 100% Branch Coverage and does not reveal fault and every
        //test suit that achieves 100% statement coverage that reveals fault is not possible.
        //This is because Branch Coverage subsumes Statement Coverage. All Test Widths that
        //satisfy branch criteria will satisfy statement criteria.
    }

    public int whiteboxMethod2(int a) {
        int b = 0;

        if (a % 2 == 0) {
            b = 1;
        }

        if (a % 2 > 0) {
            b = 2;
        }
        return a/b;
    }

    public int whiteboxMethod3(int a, int b) {
        if (a > 0)
            a += 2;
        else if (a < 0)
            a -= 2;
        return a/b;
    }

    public int whiteboxMethod4(int a) {
        int b = 0;
        if (a % 2 == 0) {
            b = 1;
        }
        if (a % 2 > 0) {
            b = 2;
        }
        return a/b;
    }

    public boolean whiteboxMethod5 (boolean a, boolean b) {
        int x = 2;
        int y = 4;
        if(a)
            x = x*2;
        else
            b = !b;
        if(b)
            y -= x;
        else
            x -= y;
        return ((x/y)>= 1);
    }

    // ================
    //
    // Fill in column “output” with T, F, or E:
    //
    // | a | b |output|
    // ================
    // | T | T |  E   |
    // | T | F |  F   |
    // | F | T |  F   |
    // | F | F |  T   |
    // ================
    //
    // Fill in the blanks in the following sentences with
    // “NEVER”, “SOMETIMES” or “ALWAYS”:
    //
    // Test suites with 100% statement coverage _SOMETIMES_ reveal the fault in this method.
    // Test suites with 100% branch coverage _SOMETIMES_ reveal the fault in this method.
    // Test suites with 100% path coverage _ALWAYS_ reveal the fault in this method.
    // ================
}
