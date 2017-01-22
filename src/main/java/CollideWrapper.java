import org.apache.commons.lang3.RandomStringUtils;

import java.util.*;

public class CollideWrapper {

    public static List<Collision> collide(List<Integer> bitSizes, int numDesiredCollisionsPerBitSize) {
        List<Collision> foundCollisions = new ArrayList<>();

        for(int i = 0; i < bitSizes.size(); i++) {
            Map<String,Integer> stringsAndDigests = new TreeMap<>();
            int numBits = bitSizes.get(i);
            int numFoundCollisions = 0;
            int numAttempts = 0;

            while(numFoundCollisions < numDesiredCollisionsPerBitSize) {
                String random = RandomStringUtils.randomAscii(50);
                int origDigest = sha1Helper.getSHA1TruncDigest(random, numBits);

                if(stringsAndDigests.containsValue(origDigest)) {
                    String match = getMatchingStringForDigest(stringsAndDigests, origDigest);
                    boolean addCollision = true;

                    if(match == null) {
                        System.out.println("No matching string found for digest " + origDigest);
                        addCollision = false;
                    } else {
                        // Test to make sure it's correct
                        int digest1 = sha1Helper.getSHA1TruncDigest(match, numBits);
                        int digest2 = sha1Helper.getSHA1TruncDigest(random, numBits);
                        if(digest1 != digest2) {
                            System.out.println("The two digests are not equal for these strings:\n" + "\t" + match + "\n\t" + random);
                            addCollision = false;
                        }
                    }

                    if(addCollision) {
                        Collision toBeAdded = new Collision();
                        toBeAdded.bitSize = numBits;
                        toBeAdded.numAttempts = numAttempts;
                        toBeAdded.strings.add(match);
                        toBeAdded.strings.add(random);
                        foundCollisions.add(toBeAdded);
                        numFoundCollisions++;
                        numAttempts = 0;
                        stringsAndDigests.clear();
                    } else {
                        numAttempts++;
                    }
                } else {
                    numAttempts++;
                }

                // Regardless if the digest was contained in map's value set,
                // add the string and the digest to the map
                stringsAndDigests.put(random, origDigest);
                if(stringsAndDigests.get(random) != origDigest)
                    System.out.println("String " + random + " went into map when digest information was incorrect.\n\tOrig: " +
                            origDigest + "\n\tIn map: " + stringsAndDigests.get(random));
            }
        }

        return foundCollisions;
    }

    private static String getMatchingStringForDigest(Map<String,Integer> stringsAndDigests, int digest) {
        for(Map.Entry<String,Integer> entry: stringsAndDigests.entrySet()) {
            if(entry.getValue() == digest)
                return entry.getKey();
        }
        return null;
    }

    public static List<Integer> generateNumBitsArray(int min, int max, int numElements){
        // All the bitsizes to test - 10, 13, ...
        List<Integer> bitSizes = new ArrayList<>();

        while(bitSizes.size() != numElements) {
            Random random = new Random();
            int i = random.nextInt(max);
            if(i > min - 1 && !bitSizes.contains(i))
                bitSizes.add(i);
        }

        return bitSizes;
    }
}
