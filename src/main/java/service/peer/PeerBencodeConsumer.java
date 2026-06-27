package service.peer;

import service.BencodeDecodeService;

public class PeerBencodeConsumer implements BencodeDecodeService.BencodeListener {
    @Override
    public void onKeyParsed(String key, Integer beginPosition, Integer endPosition) {
    }
}
