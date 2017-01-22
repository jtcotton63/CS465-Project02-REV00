import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class sha1Helper {
    public static byte[] getSHA1Digest(String input) {
        MessageDigest d = null;
        try {
            d = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        d.reset();
        d.update(input.getBytes());
        return d.digest();
    }

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
        return cutDigestToNumBitsLength(digest, numBits);
    }

    private static int cutDigestToNumBitsLength(byte[] digest, int numBits) {
        int[] shortDigest = new int[4];
        for(int i = 0; i < shortDigest.length; i++) {
            int temp = convertByteToInt(digest[i]);
            shortDigest[i] = temp;
        }

        long rtn = shortDigest[0];
        for(int i = 1; i < shortDigest.length; i++) {
            rtn = rtn << 8;
            rtn += shortDigest[i];
        }

        rtn = rtn >> (Integer.SIZE - numBits);
        return (int) rtn;
    }

    private static int convertByteToInt(byte b) {
        return b & 0xff;
    }

    public static String toHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X ", b));
        }
        return sb.toString();
    }
}
