import java.util.ArrayList;
import java.util.List;

/**
 * Created by josepher on 1/23/16.
 */
public class Collision {
    public int bitSize = -1;
    // The number of attempts that had to be made until a collision was found
    public int numAttempts = -1;
    public List<String> strings = new ArrayList<>();

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("bitSize: " + bitSize);
        sb.append("\n");
        sb.append("numAttempts: " + numAttempts);
        sb.append("\n");
        for(String s: strings) {
            sb.append("\t" + s + "  <-->  " + sha1Helper.getSHA1TruncDigest(s, bitSize));
            sb.append("\n");
        }
        return sb.toString();
    }
}
