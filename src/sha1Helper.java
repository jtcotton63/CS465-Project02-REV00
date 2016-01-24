import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by josepher on 1/23/16.
 */
public class sha1Helper {
    public static int getSHA1TruncDigest(String input, int numBits) {
        MessageDigest d = null;
        try {
            d = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        d.reset();
        d.update(input.getBytes());
        byte[] digest = d.digest();
        byte[] temp = new byte[20];
        for(int i = 0; i < 20; i++){
            temp[i] = -128;
        }
        assert cutDigestToNumBitsLength(temp, 10) == 32896;
        return cutDigestToNumBitsLength(digest, numBits);
    }

    private static int cutDigestToNumBitsLength(byte[] digest, int numBits) {
        int numBytes = numBits / 8;
        int numExtraBits = numBits % 8;

        int[] shortDigest;
        if(numExtraBits > 0) {

            shortDigest = new int[numBytes + 1];
            for(int i = 0; i < numBytes; i++) {
                shortDigest[i] = convertByteToInt(digest[i]);
            }
            int mask = 0x00;
            switch(numExtraBits) {
                case 7:
                    mask = 0xFE;
                    break;
                case 6:
                    mask = 0xFC;
                    break;
                case 5:
                    mask = 0xF8;
                    break;
                case 4:
                    mask = 0xF0;
                    break;
                case 3:
                    mask = 0xE0;
                    break;
                case 2:
                    mask = 0xC0;
                    break;
                case 1:
                    mask = 0x80;
                    break;
            }
            shortDigest[shortDigest.length - 1] = digest[numBytes + 1] & mask; // Does this work?

        } else {
            shortDigest = new int[numBytes];
            for(int i = 0; i < numBytes; i++)
                shortDigest[i] = digest[i];
        }

        return convertToSingleInt(shortDigest); // Does this work?
    }

    private static int convertByteToInt(byte b) {
        return b & 0xff;
    }

    private static int convertToSingleInt(int[] bytes) {
        int rtn = (int) bytes[0];
        for(int i = 1; i < bytes.length; i++) {
            rtn = rtn << bitLength((int) bytes[i]);
            rtn = rtn | bytes[i];
        }
        return rtn;
    }

    private static int bitLength(int value) {
        return Integer.SIZE - Integer.numberOfLeadingZeros(value);
    }
}
