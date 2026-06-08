import com.google.gson.Gson;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;

public class Main {
  private static final Gson gson = new Gson();

  public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
    String command = args[0];

    final BencodeDecoder bencodeDecoder = new BencodeDecoder();


    if ("decode".equals(command)) {
      final String bencodedValue = args[1];
      final DecodeMetadata decodeMetadata = bencodeDecoder.decodeValue(bencodedValue, 0);
      System.out.println(decodeMetadata.value());

    } else if ("info".equals(command)) {
      final Path torrentFile = Path.of(args[1]);
      final byte[] byteArray = Files.readAllBytes(torrentFile);

      final String bencodedValue = new String(byteArray, StandardCharsets.ISO_8859_1);

      final DecodeMetadata decodeMetadata = bencodeDecoder.decodeValue(bencodedValue, 0);

      final DecodeInfo decodeInfo = decodeMetadata.toDecodeInfo();
      decodeInfo.calculateInfoHash();
      System.out.println(decodeInfo);

    } else {
      System.out.println("Unknown command: " + command);
    }
  }
}
