import java.util.*;

/**
 * Created by josepher on 1/23/16.
 */
public class CollideWrapper {

    public static void collide() {
        Map<String,Integer> digestsOfStrings = new HashMap<>();
        // All the bitsizes to test - 10, 13, ...
        List<Integer> bitSizes = new ArrayList<>();
        // BitSize: 10, Number of rounds needed to find a collision: 300, 301, 405
        Map<Integer,List<Collision>> bitSizeToNumberOfRoundsNeededToFindACollision = new HashMap<>();

        while(bitSizes.size() != 10) {
            Random random = new Random();
            int i = random.nextInt() % 32;
            if(i > 10 && !bitSizes.contains(i))
                bitSizes.add(i);
        }

        for(int i = 0; i < bitSizes.size(); i++) {
            int numRoundsForString = 1;
            bitSizeToNumberOfRoundsNeededToFindACollision.put(bitSizes.get(i), new ArrayList());

            while(bitSizeToNumberOfRoundsNeededToFindACollision.get(bitSizes.get(i)).size() < 3) {
                String randomString = UUID.randomUUID().toString();
                int digest = sha1Helper.getSHA1TruncDigest(randomString, bitSizes.get(i));

                // If the digestsOfStrings map contains this as the key,
                // then this is a collision
                if(digestsOfStrings.containsValue(digest)) {
                    Collision toBeAdded = new Collision();
                    toBeAdded.bitSize = bitSizes.get(i);
                    toBeAdded.numAttempts = numRoundsForString;
                    toBeAdded.str1 = ;
                    toBeAdded.str2 = randomString;
                    bitSizeToNumberOfRoundsNeededToFindACollision.get(bitSizes.get(i)).add((toBeAdded));
                    numRoundsForString = 0;
                }

                numRoundsForString++;
            }
        }
    }


}
