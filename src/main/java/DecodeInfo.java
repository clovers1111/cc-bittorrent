import java.util.Map;

public class DecodeInfo {

    // Key for announce (e.g., announce: "https://header.com/torrent"
    private String announce;

    // Keys for info map (e.g., length: 92051)
    private Map<String, Object> info;

    private String infoHash;

    private String piecesHash;

    public String getAnnounce() {
        return announce;
    }

    public Map<String, Object> getInfo() {
        return info;
    }

    public String getInfoHash() {
        return this.infoHash;
    }

    public void setInfoHash(String infoHash) {
        this.infoHash = infoHash;
    }

    public String getPiecesHash() {
        return piecesHash;
    }

    public void setPiecesHash(String piecesHash) {
        this.piecesHash = piecesHash;
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
            if (info.containsKey("piece length")) {
                sb.append("Piece Length: ").append(info.get("piece length")).append("\n");
                sb.append("Piece Hash: ").append(getPiecesHash()).append("\n");
            }

        }
        return sb.toString();
    }
}
