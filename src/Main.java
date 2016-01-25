/**
 * Created by josepher on 1/23/16.
 */
public class Main {
    public static void main(String[] args) {
        // Some tests for proper truncation

        // Left-most digit is a 1
        String abc = "abc";
        String abc23Digits = "10101001100110010011111";
        int expected = Integer.parseInt(abc23Digits, 2);
        int actual = sha1Helper.getSHA1TruncDigest(abc, 23);
        assert actual == expected;



        CollideWrapper.collide();
        System.out.println("Done");
    }
}
