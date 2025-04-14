package com.newsnow.media.outside.driving.api.util;

import java.util.function.Consumer;
import reactor.core.publisher.Signal;
import reactor.core.publisher.SignalType;

public final class LogHelper {
    public static <T> Consumer<Signal<T>> logOnNext(
            Consumer<T> log) {
        return signal -> {
            if (signal.getType() != SignalType.ON_NEXT) return;
            log.accept(signal.get());
        };
    }
}