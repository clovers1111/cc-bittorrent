import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashEncoder {

    public static String encodeToSHA1(byte[] bytes) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");

        md.update(bytes);

        final byte[] encodedBytes = md.digest();

        StringBuilder hexString = new StringBuilder();
        for (byte b : encodedBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0'); // Ensure padding for single digit hex values
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
