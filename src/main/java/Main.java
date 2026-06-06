import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
// import com.dampcake.bencode.Bencode; - available if you need it!

public class Main {
  private static Predicate<String> STARTS_WITH_ARRAY =  (s) -> s.startsWith("l");
  private static Predicate<String> STARTS_WITH_INTEGER = (s) -> Character.isDigit(s.charAt(0));
  private static Predicate<String> STARTS_WITH_ALPHABETICAL = (s) -> s.startsWith("i");

  private static final Gson gson = new Gson();

  public static void main(String[] args) throws Exception {
    // You can use print statements as follows for debugging, they'll be visible when running tests.
    System.err.println("Logs from your program will appear here!");

    
    String command = args[0];
    String decodedOutput = "";
    final String bencodedValue = args[1];
    if("decode".equals(command)) {
      if (STARTS_WITH_ARRAY.test(bencodedValue)) {
        decodedOutput = Optional.of(
                decodeBencodeArray(bencodedValue).decoded())
                .orElse(List.of().toString());
      } else if (STARTS_WITH_INTEGER.test(bencodedValue)) {
        final String decodedString = decodeBencodeString(bencodedValue).decoded();
      } else {
          decodedOutput = decodeBencodeInteger(bencodedValue).decoded();
      }

      System.out.println(decodedOutput);
    } else {
      System.out.println("Unknown command: " + command);
    }


  }

  static BencodeMetadata decodeBencodeString(String bencodedString) {
    final int firstColonIndex = bencodedString.indexOf(':');
    final int encodeLength = firstColonIndex
            + 1 // Colon
            + Integer.parseInt(bencodedString.substring(0, firstColonIndex));

    final String decoded = bencodedString.substring(firstColonIndex+1, encodeLength);
    final String decodedJson = gson.toJson(decoded);

    return new BencodeMetadata(decodedJson, encodeLength);
  }

  static BencodeMetadata decodeBencodeInteger(String bencodedInteger) {
    final int encodeLength = bencodedInteger.indexOf('e') + 1;
    final String decoded = bencodedInteger.substring(1, encodeLength-1);

    return new BencodeMetadata(decoded, encodeLength);
  }

  static BencodeMetadata decodeBencodeArray(final String bencodedString) {
    final List<String> bencodeArray = new ArrayList<>();
    String mutableBencodedString = bencodedString.substring(1);
    // account for l (begin array character)
    int totalLength = 1;
    while (mutableBencodedString.charAt(0) != 'e') {

      final BencodeMetadata bencodeMetadata;

      if (STARTS_WITH_ARRAY.test(mutableBencodedString)) {
        bencodeMetadata = decodeBencodeArray(mutableBencodedString);
      } else if (STARTS_WITH_ALPHABETICAL.test(mutableBencodedString)) {
        bencodeMetadata = decodeBencodeInteger(mutableBencodedString);
      } else if (STARTS_WITH_INTEGER.test(mutableBencodedString)){
        bencodeMetadata = decodeBencodeString(mutableBencodedString);
      } else {
        return null;
      }
      bencodeArray.add(bencodeMetadata.decoded());
      totalLength += bencodeMetadata.encodedLength();
      mutableBencodedString = mutableBencodedString.substring(bencodeMetadata.encodedLength());
    }
    // account for e (sentinel character for array encoding)
    totalLength += 1;
    return new BencodeMetadata(bencodeArray.toString(), totalLength);
  }
  
}
