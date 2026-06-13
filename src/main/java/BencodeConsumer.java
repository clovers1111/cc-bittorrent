public class BencodeConsumer implements BencodeDecoder.BencodeListener {
    private Integer startInfoIndex;
    private Integer piecesIndex;

    @Override
    public void onInfoKey(Integer location) {
        System.out.println("Info location: " + location);
        if (startInfoIndex != null) {
            setStartInfoIndex(location);
        }
    }

    @Override
    public void onPiecesKey(Integer location) {
        System.out.println("Pieces location: " + location);
        if (piecesIndex != null) {
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
