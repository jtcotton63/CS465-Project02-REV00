import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by josepher on 1/23/16.
 */
public class CollideWrapper {

    public static void collide() {
        Map<String,Integer> digestsOfStrings = new HashMap<>();
        // All the bitsizes to test - 10, 13, ...
        List<Integer> bitSizes = new ArrayList<>();
        // BitSize: 10, Number of rounds needed to find a collision
        Map<Integer,List<Collision>> bitSizesToCollisions = new HashMap<>();

        while(bitSizes.size() != 10) {
            Random random = new Random();
            int i = random.nextInt(25);
            if(i > 10 && !bitSizes.contains(i))
                bitSizes.add(i);
        }

        for(int i = 0; i < bitSizes.size(); i++) {
            int numRoundsForString = 1;
            bitSizesToCollisions.put(bitSizes.get(i), new ArrayList());

            while(bitSizesToCollisions.get(bitSizes.get(i)).size() < 3) {
                String randomString = UUID.randomUUID().toString();
                int digest = sha1Helper.getSHA1TruncDigest(randomString, bitSizes.get(i));

                // If the digestsOfStrings map contains this as the key,
                // then this is a collision
                if(digestsOfStrings.containsValue(digest)) {
                    Collision toBeAdded = new Collision();
                    toBeAdded.bitSize = bitSizes.get(i);
                    toBeAdded.numAttempts = numRoundsForString;
                    toBeAdded.strings = getKeyFromValue(digestsOfStrings, digest);
                    toBeAdded.strings.add(randomString);
                    bitSizesToCollisions.get(bitSizes.get(i)).add((toBeAdded));

                    // Print out
                    // output-10-3.txt
                    System.out.println("********************");
                    System.out.println(toBeAdded.toString());
                    System.out.println("********************");
                    String filename = "output-" + bitSizes.get(i) + "-" + bitSizesToCollisions.get(bitSizes.get(i)).size() + ".txt";
                    PrintWriter writer = null;
                    try {
                         writer = new PrintWriter(filename);
                        writer.println(toBeAdded.toString());
                    } catch (FileNotFoundException e) {

                    } finally {
                        if(writer != null) {
                            writer.flush();
                            writer.close();
                        }
                    }

                    numRoundsForString = 0;
                }

                numRoundsForString++;
            }
        }
    }

    private static List<String> getKeyFromValue(Map<String,Integer> map, int value) {
        List<String> results = new ArrayList<>();
        for(Map.Entry entry: map.entrySet()) {
            if((int) entry.getValue() == value) {
                results.add((String) entry.getKey());
                return results;
            }
        }
        return results;
    }


}
