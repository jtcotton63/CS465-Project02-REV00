import org.apache.commons.lang3.RandomStringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

class CollideWrapper {

    static Map<Integer, List<Collision>> collide(List<Integer> bitSizes, int numDesiredCollisions) {
        Map<Integer, List<Collision>> bitSizesToCollisions = new HashMap<>(bitSizes.size());

        for (Integer bitSize : bitSizes) {
            List<Collision> collisions = collide(bitSize, numDesiredCollisions);
            bitSizesToCollisions.put(bitSize, collisions);
        }

        return bitSizesToCollisions;
    }

    private static List<Collision> collide(int bitSize, int numDesiredCollisions) {
        List<Collision> foundCollisions = new ArrayList<>();

        Map<String,Integer> stringsAndDigests = new TreeMap<>();
        int numFoundCollisions = 0;
        long numAttempts = 1;

        while(numFoundCollisions < numDesiredCollisions) {
            String random = RandomStringUtils.randomAscii(50);
            int origDigest = sha1Helper.getSHA1TruncDigest(random, bitSize);

            if(stringsAndDigests.containsValue(origDigest)) {
                String match = getMatchingStringForDigest(stringsAndDigests, origDigest);
                boolean addCollision = true;

                if(match == null) {
                    System.out.println("No matching string found for digest " + origDigest);
                    addCollision = false;
                } else {
                    // Test to make sure it's correct
                    int digest1 = sha1Helper.getSHA1TruncDigest(match, bitSize);
                    int digest2 = sha1Helper.getSHA1TruncDigest(random, bitSize);
                    if(digest1 != digest2) {
                        System.out.println("The two digests are not equal for these strings:\n" + "\t" + match + "\n\t" + random);
                        addCollision = false;
                    }
                }

                if(addCollision) {
                    printStatus(numFoundCollisions, bitSize, numAttempts);
                    Collision toBeAdded = new Collision();
                    toBeAdded.bitSize = bitSize;
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

        return foundCollisions;
    }

    private static String getMatchingStringForDigest(Map<String,Integer> stringsAndDigests, int digest) {
        for(Map.Entry<String,Integer> entry: stringsAndDigests.entrySet()) {
            if(entry.getValue() == digest)
                return entry.getKey();
        }
        return null;
    }

    static List<Integer> generateNumBitsArray(){
        // All the bitsizes to test - 10, 13, ...
        List<Integer> bitSizes = new ArrayList<>();

//        while(bitSizes.size() != numElements) {
//            Random random = new Random();
//            int i = random.nextInt(max);
//            if(i > min - 1 && !bitSizes.contains(i))
//                bitSizes.add(i);
//        }

        bitSizes.add(3);
        bitSizes.add(5);
        bitSizes.add(9);
        bitSizes.add(11);
        bitSizes.add(13);
        bitSizes.add(17);
        bitSizes.add(20);
        bitSizes.add(23);

        return bitSizes;
    }

    private static void printStatus(int index, int bitSize, long attempts) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String dateString = dateFormat.format(date);
        System.out.println(dateString + " Collide Attack: found collision #" + (index + 1) + " of bit size " + bitSize + "; took " + attempts + " attempts");
    }
}
