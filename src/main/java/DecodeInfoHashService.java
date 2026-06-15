import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DecodeInfoHashService {

    private static final int COLON_BYTE_IN_DEC = 58;

    public static String hashInfo(byte[] infoValueByteArray) throws NoSuchAlgorithmException {
        return HashEncoder.encodeToSHA1(infoValueByteArray);
    }

    public static List<String> hashPieces(byte[] piecesValueByteArray) throws Exception {
        // parse colon
        Integer firstColonIndex = null;
        for (int i = 0; i < piecesValueByteArray.length; i++) {
            if (piecesValueByteArray[i] == COLON_BYTE_IN_DEC) {
                firstColonIndex = i;
                break;
            }
        }

        if (firstColonIndex == null) {
            throw new Exception("No such index for first colon in byte array.");
        }

        final Integer lengthOfHashingPieces = piecesValueByteArray.length - firstColonIndex - 1;

        // Check if the length of the provided pieces is divisible by 20 (w/ no remainder)
        if ((lengthOfHashingPieces % 20) != 0) {
            throw new Exception("lengthOfHashingPieces is not divisible by 20");
        }

        final List<String> hashPiecesList = new ArrayList<>();
        for (int i = firstColonIndex + 1; i < piecesValueByteArray.length - 1; i += 20) {
            final StringBuilder sb = new StringBuilder();
            for (int j = i; j < i + 20; j++) {
                sb.append(String.format("%02x", piecesValueByteArray[j]));
            }
            hashPiecesList.add(sb.toString());
        }

    return hashPiecesList;
    }
}

            /*final byte[] subArrayOfPieces = Arrays.copyOfRange(piecesValueByteArray, i, i + 20);
            final String piecesEncode = HashEncoder.encodeToSHA1(subArrayOfPieces);
            hashPiecesList.add(piecesEncode);*/
