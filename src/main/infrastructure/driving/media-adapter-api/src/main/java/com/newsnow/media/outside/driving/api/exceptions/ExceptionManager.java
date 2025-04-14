package com.newsnow.media.outside.driving.api.exceptions;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles the exceptions by using the set of responses it should have previously configured. In general terms, this
 * manager binds exceptions against responses, providing this suitable way to handle expected failures.
 *
 * @param <R> Type of the responses the manager should provide.
 *
 */
public abstract class ExceptionManager<R> {

    public static final String LOG_EX = " [MEDIA_FINEX]";

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Map<Class<? extends Throwable>, Function<Throwable, R>> responses;

    /**
     * Initializes the set of exceptions-responses by invoking the {@link #initialize} method.
     */
    public ExceptionManager() {
        responses = new HashMap<>();
        this.initialize(responses);
    }

    /**
     * Retrieves the mapped response for the given key or the {@link #getDefaultResponse() default one} if no response
     * is associated with it.<br>
     * For the specific case of {@link RuntimeException}, if it isn't mapped an additional check will be done by
     * performing the same operation with its cause.
     *
     * @param ex The exception from which to retrieve its mapped response
     *
     * @return The response associated with the specified exception.
     */
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

    /**
     * Checks if the class of the given exception is mapped in the current configured responses.
     *
     * @param ex The exception to be checked.
     *
     * @return <b>True</b> if and only if the specified exception is present as a key within the mapped responses,
     *         <b>false</b> otherwise.
     */
    protected boolean isMapped(Throwable ex) {
        return responses.containsKey(ex.getClass());
    }

    /**
     * Initializes the set of responses this system should have for the previously known and therefore expected-exceptions.
     *
     * @param responses The map of responses that should be populated when this method is invoked.
     */
    public abstract void initialize(Map<Class<? extends Throwable>, Function<Throwable, R>> responses);

    /**
     * Default response for unexpected cases.
     *
     * @return The default response this system has for non-handled scenarios.
     */
    public abstract R getDefaultResponse();

    protected Map<Class<? extends Throwable>, Function<Throwable, R>> getResponses() {
        return responses;
    }

}
