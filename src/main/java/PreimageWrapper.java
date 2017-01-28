import org.apache.commons.lang3.RandomStringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class PreimageWrapper {

    static Map<Integer, List<Collision>> preimage(List<Integer> bitSizes, int numDesiredCollisions) {
        Map<Integer, List<Collision>> bitSizesToCollisions = new HashMap<>(bitSizes.size());

        for(int i = 0; i < bitSizes.size(); i++) {
            int currentBitSize = bitSizes.get(i);
            List<Collision> collisions = preimage(currentBitSize, numDesiredCollisions);
            bitSizesToCollisions.put(currentBitSize, collisions);
        }

        return bitSizesToCollisions;
    }

    private static List<Collision> preimage(int bitSize, int numDesiredCollisions) {
        List<Collision> foundCollisions = new ArrayList<>();

        int numFoundCollisions = 0;
        long numAttempts = 1;
        String key = RandomStringUtils.randomAscii(50);
        int keyDigest = sha1Helper.getSHA1TruncDigest(key, bitSize);

        while(numFoundCollisions < numDesiredCollisions) {
            String random = RandomStringUtils.randomAscii(50);
            int trialDigest = sha1Helper.getSHA1TruncDigest(random, bitSize);

            if(trialDigest == keyDigest) {
                printStatus(numFoundCollisions, bitSize, numAttempts);
                // A string that has the same hash was found
                Collision toBeAdded = new Collision();
                toBeAdded.bitSize = bitSize;
                toBeAdded.numAttempts = numAttempts;
                toBeAdded.strings.add(key);
                toBeAdded.strings.add(random);
                foundCollisions.add(toBeAdded);
                numFoundCollisions++;
                numAttempts = 0;
                key = UUID.randomUUID().toString();
                keyDigest = sha1Helper.getSHA1TruncDigest(key, bitSize);
            } else {
                numAttempts++;
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

    private static void printStatus(int index, int bitSize, long attempts) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String dateString = dateFormat.format(date);
        System.out.println(dateString + " Preimage Attack: found collision #" + (index + 1) + " of bit size " + bitSize + "; took " + attempts + " attempts");
    }
}
