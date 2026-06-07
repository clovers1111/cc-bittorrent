import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;


/*
{
  "announce": "http://example.com",
  "created by": "Transmission/3.00",
  "creation date": 1704067200,
  "comment": "Example Debian Linux Distribution",
  "info": {
    "name": "ubuntu-23.10-desktop-amd64.iso",
    "piece length": 262144,
    "pieces": "<binary blob of SHA-1 piece hashes>",
    "length": 4831838208
  }
}
 */
public class DecodedParser {

    public synchronized static DecodeInfo toDecodeInfo(DecodeMetadata decodeMetadata) {
        final Gson gson = new Gson();

        return gson.fromJson(decodeMetadata.value().toString(), DecodeInfo.class);
    }



}
