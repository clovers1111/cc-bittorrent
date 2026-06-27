package service.info;

import domain.DecodeInfo;
import domain.DecodeMetadata;
import service.BencodeDecodeService;
import service.DecodeService;

import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class DecodeInfoService implements DecodeService {

    private final BencodeDecodeService bencodeDecoder;

    private final InfoBencodeConsumer bencodeConsumer;

    private final Integer START_DECODE_INDEX = 0;

    public DecodeInfoService() {
        this.bencodeConsumer = new InfoBencodeConsumer();
        this.bencodeDecoder = new BencodeDecodeService(this.bencodeConsumer);
    }

    public DecodeInfo createDecodeInfo(Path torrentFile) throws Exception {

        final byte[] byteArray = Files.readAllBytes(torrentFile);

        final DecodeInfo decodeInfo = initDecodeInfo(byteArray);

        if (validDecodeInfo()) {
            final List<String> piecesHash = createPiecesHash(byteArray);
            final String infoHash = createInfoHash(byteArray);

            decodeInfo.setInfoHash(infoHash);
            decodeInfo.setPiecesHash(piecesHash);
        }
        return decodeInfo;
    }

    private DecodeInfo initDecodeInfo(byte[] byteArray) {
        final String bencodedValue = new String(byteArray, StandardCharsets.ISO_8859_1);
        final DecodeMetadata decodeMetadata = bencodeDecoder.decodeValue(bencodedValue, START_DECODE_INDEX);
        return decodeMetadata.toDecodeInfo();
    }

    private String createInfoHash(byte[] byteArray) throws NoSuchAlgorithmException {
        final byte[] infoHashBytes = Arrays.copyOfRange(byteArray,
                bencodeConsumer.getStartInfoIndex(),
                bencodeConsumer.getEndInfoIndex());
        return DecodeInfoHashService.hashInfo(infoHashBytes);
    }

    private List<String> createPiecesHash(byte[] byteArray) throws Exception {
        final byte[] piecesHashBytes = Arrays.copyOfRange(byteArray,
                bencodeConsumer.getStartPiecesIndex(),
                bencodeConsumer.getEndPiecesIndex());
        return DecodeInfoHashService.hashPieces(piecesHashBytes);
    }

    @Override
    public boolean isValidDecode() {
        return validDecodeInfo()
    }

    private boolean validDecodeInfo(Class<? extends BencodeDecodeService.BencodeListener> clazz) {
        return Arrays.stream(BencodeConsumer.class.getDeclaredMethods())
                .filter(m -> m.getParameterCount() == 0)
                .filter(m -> m.getReturnType() == Integer.class)
                .map(m -> {
                    try {
                        m.setAccessible(true);
                        return (Integer) m.invoke(bencodeConsumer);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                })
                .allMatch(Objects::nonNull);
    }
}
