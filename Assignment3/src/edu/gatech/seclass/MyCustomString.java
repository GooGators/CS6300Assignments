package edu.gatech.seclass;

public class MyCustomString implements MyCustomStringInterface {

    private String string;

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public int countDuplicates() {
        int count = 0;
        char[] ch = string.toCharArray();
        for (int i = 1; i < string.length(); i ++) {
            if (ch[i] == ch[i-1]) {
                count+=1;
            }
        }
        return count;
    }

    public String addDigits(int n, boolean positive) throws NullPointerException, IllegalArgumentException {

        if (string == null) {
            throw new NullPointerException();
        }

        if (n > 9 || n <= 0) {
            throw new IllegalArgumentException();
        }


        char[] ch = string.toCharArray();

        if (!positive) {
            n = n* -1;
        }
        for (int i = 0; i < string.length(); i++) {
            if (Character.isDigit(ch[i])) {

                int num = Character.getNumericValue(ch[i]);

                if (!positive) {
                    num += 10;
                }

                num = Math.abs(num + n) % 10;
                ch[i] = Character.forDigit(num, 10);
                string = new String(ch);
            }
        }

        return string;
    }

    public void flipLetttersInSubstring(int startPosition, int endPosition) throws NullPointerException, MyIndexOutOfBoundsException,
            IllegalArgumentException
    {
        if (string == null) {
            throw new NullPointerException();
        }
        if (endPosition > string.length() || startPosition <= 0) {
            throw new MyIndexOutOfBoundsException();
        }
        if (startPosition > endPosition) {
            throw new IllegalArgumentException();
        }

        char[] ch = string.toCharArray();

        for (int i = startPosition -1; i < endPosition; i ++) {

            if(Character.isLetter(ch[i])) {
                for (int j = endPosition - 1; j > i ; j--) {
                    if (!Character.isLetter(ch[j])) {
                        endPosition--;
                        continue;
                    }

                    //swapping
                    char temp = ch[i];
                    ch[i] = ch[j];
                    ch[j] = temp;

                    endPosition--;
                    break;
                }
            }
        }
        string = String.valueOf(ch);
    }
}
