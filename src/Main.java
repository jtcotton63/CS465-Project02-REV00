import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by josepher on 1/23/16.
 */
public class Main {
    public static void main(String[] args) {
        // Some tests for proper truncation

        // Left-most digit is a 1
        String abc = "abc";
        String abc23Digits = "10101001100110010011111";
        int abc23Expected = Integer.parseInt(abc23Digits, 2);
        int abc23Actual = sha1Helper.getSHA1TruncDigest(abc, 23);
        assert abc23Actual == abc23Expected;

        String def = "def";
        String def23Digits = "01011000100111000010001";
        int def23Expected = Integer.parseInt(def23Digits, 2);
        int def23Actual = sha1Helper.getSHA1TruncDigest(def, 23);
        assert def23Actual == def23Expected;

//        List<Integer> bitSizes = CollideWrapper.generateNumBitsArray(11, 25, 10);
//        List<Collision> foundCollisions =  CollideWrapper.collide(bitSizes, 3);
//
//        // Print out
//        String collisionFilename = "collisions.txt";
//        printCollisions(collisionFilename, foundCollisions);

        List<Integer> bitSizes2 = CollideWrapper.generateNumBitsArray(11, 25, 10);
        List<Collision> foundCollisionsFromPreimage =  PreimageWrapper.preimage(bitSizes2, 3);

        // Print out
        String preimageFilename = "preimage.txt";
        printCollisions(preimageFilename, foundCollisionsFromPreimage);

        System.out.println("Done");
    }

    private static void printCollisions(String filename, List<Collision> collisions) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(filename);
            for(int j = 0; j < collisions.size(); j++)
                writer.print(collisions.get(j).toString());
        } catch (FileNotFoundException e) {

        } finally {
            if(writer != null) {
                writer.flush();
                writer.close();
            }
        }
    }
}
