import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

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

        // Collision
        List<Integer> bitSizes = CollideWrapper.generateNumBitsArray();
        Map<Integer, List<Collision>> bitSizesToCollisions =  CollideWrapper.collide(bitSizes, 50);

        // Print out
        printCollisions("collisions-summary.txt", bitSizesToCollisions);
        printCSV("collisions.csv", bitSizesToCollisions);

        // Preimage
        List<Integer> bitSizes2 = CollideWrapper.generateNumBitsArray();
        Map<Integer, List<Collision>> preimageBitSizesToCollisions =  PreimageWrapper.preimage(bitSizes2, 50);


        // Print out
        String preimageFilename = "preimage-summary.txt";
        printCollisions(preimageFilename, preimageBitSizesToCollisions);
        printCSV("preimages.csv", preimageBitSizesToCollisions);

        System.out.println("Done");
    }

    private static void printCollisions(String filename, Map<Integer, List<Collision>> bitSizesToCollisions) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(filename);
            for(Map.Entry<Integer, List<Collision>> entry: bitSizesToCollisions.entrySet())
                for (int j = 0; j < entry.getValue().size(); j++)
                    writer.print(entry.getValue().get(j).toString());

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if(writer != null) {
                writer.flush();
                writer.close();
            }
        }
    }

    private static void printCSV(String filename, Map<Integer, List<Collision>> bitSizesToCollisions) {
        PrintWriter writer = null;
        try {

            writer = new PrintWriter(filename);
            for(Map.Entry<Integer, List<Collision>> entry: bitSizesToCollisions.entrySet()) {
                // Print the bit size
                writer.print(entry.getKey() + ",");

                // Print all but the last one
                for (int j = 0; j < entry.getValue().size() - 1; j++)
                    writer.print(entry.getValue().get(j).numAttempts + ",");

                // Print the last one
                writer.print(entry.getValue().get(entry.getValue().size() - 1).numAttempts);

                writer.print("\n");
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if(writer != null) {
                writer.flush();
                writer.close();
            }
        }
    }
}
