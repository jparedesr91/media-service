package com.newsnow.media.outside.driving.api.exceptions;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ExceptionManager<R> {

    public static final String LOG_EX = " [MEDIA_FINEX]";

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Map<Class<? extends Throwable>, Function<Throwable, R>> responses;

    public ExceptionManager() {
        responses = new HashMap<>();
        this.initialize(responses);
    }

    public R manage(Throwable ex) {
        if (responses.containsKey(ex.getClass())) {
            logger.error(LOG_EX+" Expected exception handled", ex);
            return responses.get(ex.getClass()).apply(ex);
        }
        if (ex instanceof RuntimeException && null != ex.getCause() && responses.containsKey(ex.getCause().getClass())) {
            logger.error(LOG_EX+" Expected (runtime) exception handled", ex.getCause());
            return responses.get(ex.getCause().getClass()).apply(ex.getCause());
        }
        logger.error(LOG_EX+" Unexpected exception handled", ex);
        return this.getDefaultResponse();
    }

    protected boolean isMapped(Throwable ex) {
        return responses.containsKey(ex.getClass());
    }

    public abstract void initialize(Map<Class<? extends Throwable>, Function<Throwable, R>> responses);

    public abstract R getDefaultResponse();

    protected Map<Class<? extends Throwable>, Function<Throwable, R>> getResponses() {
        return responses;
    }

}
