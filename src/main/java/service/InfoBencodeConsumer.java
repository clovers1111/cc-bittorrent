package service;

import java.util.Set;

public final class InfoBencodeConsumer implements BencodeDecodeService.BencodeListener {
    private Integer startInfoIndex;
    private Integer startPiecesIndex;
    private Integer endPiecesIndex;
    private Integer endInfoIndex;

    private final String PIECES_JSON = "\"pieces\"";

    private final String INFO_JSON = "\"info\"";

    private final Set<String> KEY_SET;


    InfoBencodeConsumer() {
        this.KEY_SET = Set.of(PIECES_JSON, INFO_JSON);
    }

    public void onInfoKey(Integer startInfoIndex) {
        if (getStartInfoIndex() == null) {
            setStartInfoIndex(startInfoIndex);
        }
    }


    public void onPiecesKey(Integer startPiecesIndex, Integer endPiecesIndex) {
        if (getStartPiecesIndex() == null && getEndPiecesIndex() == null) {
            setStartPiecesIndex(startPiecesIndex);
            setEndPiecesIndex(endPiecesIndex);
        }
    }

    public void onEndInfoKey(Integer endInfoIndex) {
        if (getEndInfoIndex() == null) {
            setEndInfoIndex(endInfoIndex + 1);
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

    @Override
    public void onKeyParsed(String key, Integer beginPosition, Integer endPosition) {
        if (KEY_SET.contains(key)) {
            if (key.equals(INFO_JSON)) {
                // Info key doesn't have an end value
                onInfoKey(beginPosition);
            }
            if (key.equals(PIECES_JSON)) {
                onPiecesKey(beginPosition, endPosition);
                onEndInfoKey(endPosition + 1);
            }
        }
    }
}
