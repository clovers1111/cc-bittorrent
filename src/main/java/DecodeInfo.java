import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;

public class DecodeInfo {

    // Key for announce (e.g., announce: "https://header.com/torrent"
    private String announce;

    // Keys for info map (e.g., length: 92051)
    private Map<String, Object> info;

    private String infoHash;

    public String getAnnounce() {
        return announce;
    }

    public Map<String, Object> getInfo() {
        return info;
    }

    public String getInfoHash() {
        return this.infoHash;
    }

    private void setInfoHash(String infoHash) {
        this.infoHash = infoHash;
    }

    public void calculateInfoHash() throws NoSuchAlgorithmException {
        if (getInfo() == null) return;

        if (getInfoHash() != null) return;

        final Gson gson = new Gson();

        final MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

        final byte[] infoHash = messageDigest.digest(
                                    gson.toJson(getInfo())
                                    .getBytes(StandardCharsets.ISO_8859_1));

        setInfoHash(Arrays.toString(infoHash));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (getAnnounce() != null) {
            sb.append("Tracker URL: ").append(getAnnounce()).append("\n");
        }
        if (getInfo() != null) {
            if (info.containsKey("length")) {
            sb.append("Length: ").append(info.get("length")).append("\n");
            }
            sb.append("Info Hash: ").append(getInfoHash()).append("\n");
        }
        return sb.toString();
    }

}
