public class BencodeConsumer implements BencodeDecoder.BencodeListener {
    private Integer startInfoIndex;
    private Integer startPiecesIndex;
    private Integer endPiecesIndex;
    private Integer endInfoIndex;


    @Override
    public void onInfoKey(Integer startInfoIndex) {
        if (getStartInfoIndex() == null) {
            setStartInfoIndex(startInfoIndex);
        }
    }

    @Override
    public void onPiecesKey(Integer startPiecesIndex, Integer endPiecesIndex) {
        if (getStartPiecesIndex() == null && getEndPiecesIndex() == null) {
            setStartPiecesIndex(startPiecesIndex);
            setEndPiecesIndex(endPiecesIndex);
        }
    }

    @Override
    public void onEndInfoKey(Integer endInfoIndex) {
        if (getEndInfoIndex() == null) {
            setEndInfoIndex(endInfoIndex);
        }
    }

    private void setStartInfoIndex(Integer startInfoIndex) {
        this.startInfoIndex = startInfoIndex;
    }

    private void setEndInfoIndex(Integer endInfoIndex) {
        this.endInfoIndex = endInfoIndex;
    }

    private void setEndPiecesIndex(Integer endPiecesIndex) {
        this.endPiecesIndex = endPiecesIndex;
    }


    private void setStartPiecesIndex(Integer startPiecesIndex) {
        this.startPiecesIndex = startPiecesIndex;
    }

    public Integer getEndInfoIndex() {
        return endInfoIndex;
    }

    public Integer getEndPiecesIndex() {
        return endPiecesIndex;
    }

    public Integer getStartPiecesIndex() {
        return startPiecesIndex;
    }

    public Integer getStartInfoIndex() {
        return startInfoIndex;
    }
}
