import com.google.gson.Gson;
import domain.DecodeInfo;
import domain.DecodeMetadata;
import service.BencodeDecodeService;
import service.DecodeInfoService;
import service.HashEncoderService;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;


public class Main {
  private static final Gson gson = new Gson();


  public static void main(String[] args) throws Exception {
    String command = args[0];


    if ("decode".equals(command)) {
      final BencodeDecodeService bencodeDecoder = new BencodeDecodeService();

      final String bencodedValue = args[1];
      final DecodeMetadata decodeMetadata = bencodeDecoder.decodeValue(bencodedValue, 0);
      System.out.println(decodeMetadata.value());

    } else if ("info".equals(command) || "peers".equals(command)) {

      final DecodeInfoService decodeInfoService = new DecodeInfoService();

      final Path torrentFile = Path.of(args[1]);

      final DecodeInfo decodeInfo = decodeInfoService.createDecodeInfo(torrentFile);

      if ("info".equals(command)) {
      System.out.println(decodeInfo);
      } else {
        try (HttpClient httpClient = HttpClient.newHttpClient()) {

          final String urlEncodedInfoHash = HashEncoderService.encodeHexToUrl(decodeInfo.getInfoHash());
          String queryParameters = new TrackerRequest.Builder()
                  .urlInfoHash(urlEncodedInfoHash)
                  .peerId("12345678901234567890")
                  .port(6881)
                  .uploaded(0)
                  .downloaded(0)
                  .left(decodeInfo.getLength())
                  .compact(1)
                  .build()
                  .toQueryParameters();

          final String trackerGetUrl = String.format("%s?%s", decodeInfo.getAnnounce(), queryParameters);
          final HttpRequest httpRequest = HttpRequest.newBuilder()
                  .uri(URI.create(trackerGetUrl))
                  .GET()
                  .build();

          HttpResponse<String> trackerResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString(StandardCharsets.ISO_8859_1));
          BencodeDecodeService bencodeDecoder = new BencodeDecodeService();
          DecodeMetadata decodeMetadata = bencodeDecoder.decodeValue(trackerResponse.body(), 0);
          String st = decodeMetadata.value().toString();
          //TrackerResponse response = gson.fromJson(st, TrackerResponse.class);
          System.out.println(st);
          System.out.println(decodeMetadata);


        }


        // Create a service to build the uri with query parameters
        //



      }

    } else {
      System.out.println("Unknown command: " + command);
    }
  }
}