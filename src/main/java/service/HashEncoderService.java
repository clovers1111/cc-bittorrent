package service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashEncoderService {

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

    // It is assumed that strings passed here are translated into hex;
    // every two characters is one byte to be URL encoded
    public static String encodeHexToUrl(String hex) {
        final char[] hexCharArray = hex.toCharArray();
        final StringBuilder sb = new StringBuilder().append('%');
        for (int i = 0; i < hexCharArray.length; i += 2) {
            sb.append(hexCharArray[i])
                    .append(hexCharArray[i+1]);
                    if (i + 2 < hexCharArray.length)
                        sb.append('%');
        }
        return sb.toString();
    }
}
