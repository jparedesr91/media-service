package com.newsnow.media.outside.driving.api.exceptions;

import java.util.Map;
import java.util.function.Function;

public interface ResponseRegister<T> {
    Map<Class<? extends Throwable>, Function<Throwable, T>> getResponses();
}
