public class BencodeConsumer implements BencodeDecoder.BencodeListener {
    private Integer startInfoIndex;
    private Integer piecesIndex;

    @Override
    public void onInfoKey(Integer location) {
        if (startInfoIndex == null) {
            System.out.println("Info location: " + location);
            setStartInfoIndex(location);
        }
    }

    @Override
    public void onPiecesKey(Integer location) {
        if (piecesIndex == null) {
            System.out.println("Info location: " + location);
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
