import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BencodeDecoder {

    private final String INFO_JSON = "\"info\"";

    private final String PEICES_JSON = "\"pieces\"";

    public static BencodeListener BencodeListener;

    public interface BencodeListener {
        void onInfoKey(Integer location);
        void onPiecesKey(Integer location);
    }

    private static final char BEGIN_ARRAY = 'l';
    private static final char BEGIN_INT = 'i';
    private static final char BEGIN_MAP = 'd';
    private static final char END_CHAR = 'e';

    private final Gson gson = new Gson();

    private final BencodeListener bencodeListener;

    public BencodeDecoder() {
        this.bencodeListener = null;
    };

    public BencodeDecoder(BencodeListener bencodeListener) {
        this.bencodeListener = bencodeListener;
    }

    public DecodeMetadata decodeValue(String s, int index) {
        char c = s.charAt(index);

        if (c == BEGIN_INT) {
            return decodeInteger(s, index);
        } else if (c == BEGIN_ARRAY) {
            return decodeList(s, index);
        } else if (Character.isDigit(c)) {
            return decodeString(s, index);
        } else if (c == BEGIN_MAP) {
            return decodeMap(s, index);

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
        int end = s.indexOf(END_CHAR, index);
        Long value = Long.parseLong(s.substring(index + 1, end));
        return new DecodeMetadata(value, end + 1);
    }

    private DecodeMetadata decodeList(String s, int index) {
        List<Object> items = new ArrayList<>();
        int cursor = index + 1; // skip 'l'

        while (s.charAt(cursor) != END_CHAR) {
            DecodeMetadata item = decodeValue(s, cursor);
            items.add(item.value());
            cursor = item.nextIndex();
        }

        // Trailing spaces for objects lead to test failure
        final String trimmedArrayAsString = "[" + items.stream().map(o -> o.toString().trim()).collect(Collectors.joining(",")) + "]";

        return new DecodeMetadata(trimmedArrayAsString, cursor + 1); // skip closing 'e'
    }

    private DecodeMetadata decodeMap(String s, int index) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        int cursor = index + 1; // skip 'd'

        while (s.charAt(cursor) != END_CHAR) {
            DecodeMetadata key = decodeValue(s, cursor);
            cursor = key.nextIndex();


            // For info hash, capture start of info map
            if (this.bencodeListener != null) {
                if (key.value().toString().equals(INFO_JSON)) {
                    System.out.println("Found");
                    bencodeListener.onInfoKey(key.nextIndex());
                }
            }

            sb.append(key.value()).append(":");

            DecodeMetadata value = decodeValue(s, cursor);
            sb.append(value.value());

            if (s.charAt(value.nextIndex()) != END_CHAR) {
                sb.append(",");
            }
            cursor = value.nextIndex();

            // For info hash, capture end of info hash
            if (this.bencodeListener != null) {
                if (key.value().toString().equals(PEICES_JSON)) {
                    System.out.println("Found");
                    bencodeListener.onPiecesKey(value.nextIndex() + 1);
                }
            }

        }
        final String mapString = sb.append("}").toString();

        return new DecodeMetadata(mapString, cursor + 1); // skip closing 'e'
    }
}
