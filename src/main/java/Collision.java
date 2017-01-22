import java.util.ArrayList;
import java.util.List;

public class Collision {
    public int bitSize = -1;
    // The number of attempts that had to be made until a collision was found
    public long numAttempts = -1;
    public List<String> strings = new ArrayList<>();

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("bitSize: " + bitSize);
        sb.append("\n");
        sb.append("numAttempts: " + numAttempts);
        sb.append("\n");
        for(String s: strings) {
            int digest = sha1Helper.getSHA1TruncDigest(s, bitSize);
            sb.append("\t");
            sb.append(s);
            sb.append("  <-->  ");
            sb.append(digest);
            sb.append(" ");
            sb.append(Integer.toBinaryString(digest));
            sb.append("\n");
        }
        return sb.toString();
    }
}
