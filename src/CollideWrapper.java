import java.util.*;

/**
 * Created by josepher on 1/23/16.
 */
public class CollideWrapper {

    public List<Collision> collide(int numTrialsPerBitSize, List<Integer> bitSizes) {
        Set<Integer> digestsOfStrings = new HashSet<>();
        Set<String> triedStrings = new HashSet<>();
        List<Collision> foundCollisions = new ArrayList<>();

        for(int i = 0; i < bitSizes.size(); i++) {
            int numRoundsForString = 1;
            int numFoundMatches = 0;

            while(numFoundMatches < numTrialsPerBitSize) {
                String randomString = UUID.randomUUID().toString();
                int digest = sha1Helper.getSHA1TruncDigest(randomString, bitSizes.get(i));

                // If the digestsOfStrings map contains this as the key,
                // then this is a collision
                if(digestsOfStrings.contains(digest)) {
                    String previousMatch = getMatchingStringForDigest(triedStrings, digest, bitSizes.get(i));
                    if(previousMatch == null)
                        throw new RuntimeException("String doesn't have a match");
                    int digest1 = sha1Helper.getSHA1TruncDigest(previousMatch, bitSizes.get(i));
                    int digest2 = sha1Helper.getSHA1TruncDigest(randomString, bitSizes.get(i));
                    if(digest1 != digest2)
                        throw new RuntimeException("Digests don't match");

                    Collision toBeAdded = new Collision();
                    toBeAdded.bitSize = bitSizes.get(i);
                    toBeAdded.numAttempts = numRoundsForString;
                    toBeAdded.strings.add(previousMatch);
                    toBeAdded.strings.add(randomString);

                    foundCollisions.add(toBeAdded);
                    numRoundsForString = 0;
                    numFoundMatches++;
                } else {
                    numRoundsForString++;
                }

                digestsOfStrings.add(digest);
                triedStrings.add(randomString);
            }
        }

        return foundCollisions;
    }

    private String getMatchingStringForDigest(Set<String> triedStrings, int digest, int bitSize) {
        for(String s: triedStrings) {
            if(sha1Helper.getSHA1TruncDigest(s, bitSize) == digest)
                return s;
        }
        return null;
    }

    public List<Integer> generateNumBitsArray(int min, int max, int numElements){
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
