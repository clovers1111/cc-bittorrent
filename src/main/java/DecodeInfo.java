import java.util.Map;

public class DecodeInfo {

    // Key for announce (e.g., announce: "https://header.com/torrent"
    private String announce;

    // Keys for info map (e.g., length: 92051)
    private Map<String, Object> info;

    DecodeInfo() {};

    DecodeInfo(Builder builder) {
        this.announce = builder.announce;
        this.info = builder.info;

    }

    public String getAnnounce() {
        return announce;
    }

    public Map<String, Object> getInfo() {
        return info;
    }

    public static class Builder {
        private String announce;
        private Map<String, Object> info;

        public Builder announce(String announce) {
            this.announce = announce;
            return this;
        }

        public Builder info(Map<String, Object> info) {
            this.info = info;
            return this;
        }

        public DecodeInfo build() {
            return new DecodeInfo(this);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (announce != null) {
            sb.append("Tracker URL: ").append(announce);
        }
        if (info != null) {
            if (info.containsKey("length"))
            sb.append("Length: ").append(info.get("length"));
        }

        return sb.toString();
    }

}
