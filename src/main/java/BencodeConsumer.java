public class BencodeConsumer implements BencodeDecoder.BencodeListener {
    private Integer startInfoIndex;
    private Integer piecesIndex;

    private final String INFO_JSON = "\"info\"";

    private final String PEICES_JSON = "\"pieces\"";

    @Override
    public void onInfoKey(Integer location) {
        if (startInfoIndex != null) {
            System.out.println("Info location: " + location);
            setStartInfoIndex(location);
        }
    }

    @Override
    public void onPiecesKey(Integer location) {
        if (piecesIndex != null) {
            System.out.println("Pieces location: " + location);
            setPiecesIndex(piecesIndex);
        }
    }

    private void setStartInfoIndex(Integer location) {
        startInfoIndex = location;
    }

    private void setPiecesIndex(Integer location) {
        piecesIndex = location;
    }

    public Integer getPiecesIndex() {
        return piecesIndex;
    }

    public Integer getStartInfoIndex() {
        return startInfoIndex;
    }
}
