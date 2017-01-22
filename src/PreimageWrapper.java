import java.util.*;

public class PreimageWrapper {

    public static List<Collision> preimage(List<Integer> bitSizes, int numDesiredCollisionsPerBitSize) {
        List<Collision> foundCollisions = new ArrayList<>();

        for(int i = 0; i < bitSizes.size(); i++) {
            int numBits = bitSizes.get(i);
            int numFoundCollisions = 0;
            int numAttempts = 0;
            String key = UUID.randomUUID().toString();
            int keyDigest = sha1Helper.getSHA1TruncDigest(key, numBits);

            while(numFoundCollisions < numDesiredCollisionsPerBitSize) {
                String random = UUID.randomUUID().toString();
                int trialDigest = sha1Helper.getSHA1TruncDigest(random, numBits);

                if(trialDigest == keyDigest) {
                    // A string that has the same hash was found
                    Collision toBeAdded = new Collision();
                    toBeAdded.bitSize = numBits;
                    toBeAdded.numAttempts = numAttempts;
                    toBeAdded.strings.add(key);
                    toBeAdded.strings.add(random);
                    foundCollisions.add(toBeAdded);
                    numFoundCollisions++;
                    numAttempts = 0;
                    key = UUID.randomUUID().toString();
                    keyDigest = sha1Helper.getSHA1TruncDigest(key, numBits);
                } else {
                    numAttempts++;
                }
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
