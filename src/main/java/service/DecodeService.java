package service;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Objects;

public interface DecodeService {
    boolean isValidDecode();

    default <T extends BencodeDecodeService.BencodeListener> boolean validDecodeInfo(T consumer) {
        final Class<?> clazz = consumer.getClass();
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(m -> m.getParameterCount() == 0)
                .filter(m -> m.getReturnType() == Integer.class)
                .map(m -> {
                    try {
                        m.setAccessible(true);
                        return (Integer) m.invoke(consumer);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                })
                .allMatch(Objects::nonNull);
    }
}
