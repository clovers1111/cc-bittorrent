import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;

public record DecodeMetadata(Object value, int nextIndex) {

    public DecodeInfo toDecodeInfo() {
        final Gson gson = new Gson();
        return gson.fromJson(value().toString(), DecodeInfo.class);
    }
}

