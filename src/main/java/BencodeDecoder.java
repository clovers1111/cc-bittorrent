import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class BencodeDecoder {

    private final Gson gson = new Gson();
    
    public DecodeMetadata decodeValue(String s, int index) {
        char c = s.charAt(index);

        if (c == 'i') {
            return decodeInteger(s, index);
        } else if (c == 'l') {
            return decodeList(s, index);
        } else if (Character.isDigit(c)) {
            return decodeString(s, index);
        }

        throw new IllegalArgumentException("Invalid bencode at index " + index);
    }

    private DecodeMetadata decodeString(String s, int index) {
        int colon = s.indexOf(':', index);
        int length = Integer.parseInt(s.substring(index, colon));
        int start = colon + 1;
        int end = start + length;

        String value = gson.toJson(s.substring(start, end));
        return new DecodeMetadata(value, end);
    }

    private DecodeMetadata decodeInteger(String s, int index) {
        int end = s.indexOf('e', index);
        Long value = Long.parseLong(s.substring(index + 1, end));
        return new DecodeMetadata(value, end + 1);
    }

    private DecodeMetadata decodeList(String s, int index) {
        List<Object> items = new ArrayList<>();
        int cursor = index + 1; // skip 'l'

        while (s.charAt(cursor) != 'e') {
            DecodeMetadata item = decodeValue(s, cursor);
            items.add(item.value());
            cursor = item.nextIndex();
        }

        return new DecodeMetadata(items, cursor + 1); // skip closing 'e'
    }
}
