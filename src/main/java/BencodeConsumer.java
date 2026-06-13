public class BencodeConsumer implements BencodeDecoder.BencodeListener {
    private Integer startInfoIndex;
    private Integer piecesIndex;

    @Override
    public void onInfoKey(Integer location) {
        if (startInfoIndex == null) {
            setStartInfoIndex(location);
        }
    }

    @Override
    public void onPiecesKey(Integer location) {
        if (piecesIndex == null) {
            setPiecesIndex(piecesIndex);
        }
    }

    private void setStartInfoIndex(Integer location) {
        this.startInfoIndex = location;
    }

    private void setPiecesIndex(Integer location) {
        this.piecesIndex = location;
    }

    public Integer getPiecesIndex() {
        return piecesIndex;
    }

    public Integer getStartInfoIndex() {
        return startInfoIndex;
    }
}
