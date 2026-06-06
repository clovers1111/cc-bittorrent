import com.google.gson.Gson;

public class Main {
  private static final Gson gson = new Gson();

  public static void main(String[] args) {
    String command = args[0];
    String bencodedValue = args[1];

    if ("decode".equals(command)) {
      final BencodeDecoder bencodeDecoder = new BencodeDecoder();
      final DecodeMetadata decodeMetadata = bencodeDecoder.decodeValue(bencodedValue, 0);
      System.out.println(decodeMetadata.value());
    } else {
      System.out.println("Unknown command: " + command);
    }
  }
}
