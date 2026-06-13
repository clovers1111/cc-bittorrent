import com.google.gson.Gson;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Main {
  private static final Gson gson = new Gson();

  public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
    String command = args[0];


    if ("decode".equals(command)) {
      final BencodeDecoder bencodeDecoder = new BencodeDecoder();

      final String bencodedValue = args[1];
      final DecodeMetadata decodeMetadata = bencodeDecoder.decodeValue(bencodedValue, 0);
      System.out.println(decodeMetadata.value());

    } else if ("info".equals(command)) {

      final BencodeConsumer bencodeConsumer = new BencodeConsumer();

      // Pull out index of Info key for later infohash encoding.
      final BencodeDecoder bencodeDecoder = new BencodeDecoder(bencodeConsumer);

      final Path torrentFile = Path.of(args[1]);
      final byte[] byteArray = Files.readAllBytes(torrentFile);

      final String bencodedValue = new String(byteArray, StandardCharsets.ISO_8859_1);

      final DecodeMetadata decodeMetadata = bencodeDecoder.decodeValue(bencodedValue, 0);

      final DecodeInfo decodeInfo = decodeMetadata.toDecodeInfo();

      System.out.println(bencodeConsumer.getStartInfoIndex().toString() + " " + bencodeConsumer.getPiecesIndex());

      if (bencodeConsumer.getPiecesIndex() != null && bencodeConsumer.getStartInfoIndex() != null) {
        final byte[] hashingBytes = Arrays.copyOfRange(byteArray, bencodeConsumer.getStartInfoIndex(), bencodeConsumer.getPiecesIndex());

        final String infoHash = HashEncoder.encodeToSHA1(hashingBytes);

        decodeInfo.setInfoHash(infoHash);
      }

      System.out.println(decodeInfo);

    } else {
      System.out.println("Unknown command: " + command);
    }
  }
}