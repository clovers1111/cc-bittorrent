package domain;

import com.google.gson.Gson;

public record DecodeMetadata(Object value, int nextIndex) {

    public DecodeInfo toDecodeInfo() {
        final Gson gson = new Gson();
        return gson.fromJson(value().toString(), DecodeInfo.class);
    }
}

