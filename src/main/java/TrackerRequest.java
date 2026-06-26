public class TrackerRequest {
    private final String urlInfoHash;
    private final String peerId;
    private final Integer port;
    private final Integer compact;
    private Integer uploaded;
    private Integer downloaded;
    private Integer left;

    private TrackerRequest(Builder builder) {
        this.urlInfoHash = builder.urlInfoHash;
        this.peerId = builder.peerId;
        this.port = builder.port;
        this.compact = builder.compact;
        this.uploaded = builder.uploaded;
        this.downloaded = builder.downloaded;
        this.left = builder.left;
    }

    public String toQueryParameters() {
        final StringBuilder sb = new StringBuilder();
        sb.append("info_hash=").append(urlInfoHash)
                .append("&peer_id=").append(peerId)
                .append("&port=").append(port)
                .append("&uploaded=").append(uploaded)
                .append("&downloaded=").append(downloaded)
                .append("&left=").append(left)
                .append("&compact=").append(compact);
        return sb.toString();
    }

    public String getUrlInfoHash() {
        return urlInfoHash;
    }

    public String getPeerId() {
        return peerId;
    }

    public Integer getPort() {
        return port;
    }

    public Integer getUploaded() {
        return uploaded;
    }

    public Integer getDownloaded() {
        return downloaded;
    }

    public Integer getLeft() {
        return left;
    }

    public Integer getCompact() {
        return compact;
    }

    public void setUploaded(Integer uploaded) {
        this.uploaded = uploaded;
    }

    public void setDownloaded(Integer downloaded) {
        this.downloaded = downloaded;
    }

    public void setLeft(Integer left) {
        this.left = left;
    }

    public static class Builder {
        private String urlInfoHash;
        private String peerId; // length 20
        private Integer port;
        private Integer uploaded;
        private Integer downloaded;
        private Integer left;
        private Integer compact;

        public Builder() {
        }

        public Builder urlInfoHash(String urlInfoHash) {
            this.urlInfoHash = urlInfoHash;
            return this;
        }

        public Builder peerId(String peerId) {
            this.peerId = peerId;
            return this;
        }

        public Builder port(Integer port) {
            this.port = port;
            return this;
        }

        public Builder uploaded(Integer uploaded) {
            this.uploaded = uploaded;
            return this;
        }

        public Builder downloaded(Integer downloaded) {
            this.downloaded = downloaded;
            return this;
        }

        public Builder left(Integer left) {
            this.left = left;
            return this;
        }

        public Builder compact(Integer compact) {
            this.compact = compact;
            return this;
        }

        public TrackerRequest build() {
            return new TrackerRequest(this);
        }
    }

}
