import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;

public record DecodeMetadata(Object value, int nextIndex) {

    public DecodeInfo toDecodeInfo() {
        final Gson gson = new Gson();
        final String value = new String((byte[]) value(), StandardCharsets.ISO_8859_1);
        return gson.fromJson(value, DecodeInfo.class);
    }
}

