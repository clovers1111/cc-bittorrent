import com.google.gson.Gson;

import java.util.Optional;
// import com.dampcake.bencode.Bencode; - available if you need it!

public class Main {
  private static final Gson gson = new Gson();

  public static void main(String[] args) throws Exception {
    // You can use print statements as follows for debugging, they'll be visible when running tests.
    System.err.println("Logs from your program will appear here!");
    
    String command = args[0];
    String decodedOutput = "";
    if("decode".equals(command)) {
      String bencodedValue = args[1];
      if (Character.isDigit(bencodedValue.charAt(0))) {
        final String decodedString = decodeBencodeString(bencodedValue);
        decodedOutput = gson.toJson(decodedString);
      } else if (Character.isAlphabetic(bencodedValue.charAt(0))) {
        decodedOutput = decodeBencodeInteger(bencodedValue);
      }
    } else {
      System.out.println("Unknown command: " + command);
    }
    System.out.println(decodedOutput);

  }

  static String decodeBencodeString(String bencodedString) {
    if (Character.isDigit(bencodedString.charAt(0))) {
      int firstColonIndex = 0;
      for(int i = 0; i < bencodedString.length(); i++) { 
        if(bencodedString.charAt(i) == ':') {
          firstColonIndex = i;
          break;
        }
      }
      int length = Integer.parseInt(bencodedString.substring(0, firstColonIndex));
      return bencodedString.substring(firstColonIndex+1, firstColonIndex+1+length);
    } else {
      throw new RuntimeException("Only strings and integers are supported at the moment");
    }
  }

  static String decodeBencodeInteger(String bencodedInteger) {
    return bencodedInteger.substring(1, bencodedInteger.length()-1);
  }
  
}
