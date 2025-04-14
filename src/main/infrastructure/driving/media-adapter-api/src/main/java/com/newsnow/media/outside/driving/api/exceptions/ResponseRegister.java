package com.newsnow.media.outside.driving.api.exceptions;

import java.util.Map;
import java.util.function.Function;

/**
 * Class to register custom responses within a specific microservice's layer.
 *
 */
public interface ResponseRegister<T> {

    /**
     * Retrieves the custom-mapped responses for the specific layer/case the register was conceived.
     *
     * @return A map with the registered responses. Never <b>null</b>.
     */
    Map<Class<? extends Throwable>, Function<Throwable, T>> getResponses();
}
