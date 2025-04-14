package com.newsnow.media.outside.driving.api.i18n;


import static com.newsnow.media.outside.driving.api.i18n.LocaleContextHolder.getLocale;
import static java.util.stream.Stream.concat;

import com.newsnow.media.outside.driving.api.util.FileUtils;
import java.util.stream.Stream;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * Utility class for the messaging context access. Conceived only for internal use.
 *
 */
public class MessageContextHolder {
    private static final MessageSource SOURCE;

    static {
        SOURCE = new ResourceBundleMessageSource();
        ((ResourceBundleMessageSource)SOURCE).setBasenames(getPropertiesFilesPaths());
        ((ResourceBundleMessageSource)SOURCE).setDefaultEncoding("UTF-8");
    }

    /**
     * Retrieves the message associated, in the system's resources bundles, to the given code.
     *
     * @param code The code of the message to resolve.
     *
     * @return The resolved message (never null).
     */
    static public String msg(String code) {
        return SOURCE.getMessage(code, null, getLocale());
    }

    /**
     * Retrieves the message associated, in the system's resources bundles, to the given code and applies to it a simple
     * formatting using the also specified args.
     *
     * @param code The code of the message to resolve.
     * @param args An array of arguments that will be filled in for params within the message (params look like "{0}",
     *            "{1,date}", "{2,time}" within a message), or null if none.
     *
     * @return The resolved message (never null).
     */
    static public String msg(String code, Object... args) {
        return SOURCE.getMessage(code, 0 != args.length ? args : null, getLocale());
    }

    /**
     * Retrieves the configured accessor-source of the system's resources bundles
     *
     * @return The manager of the system's resources bundles.
     */
    static public MessageSource getSource() {
        return SOURCE;
    }

    private static String[] getPropertiesFilesPaths() {
        Stream<String> defaultMessages = FileUtils.findFilePathsAsStream(
                "classpath*:i18n/*.properties",
                "messages",
                ".properties",
                2,
                ".");
        Stream<String> validationMessages = FileUtils.findFilePathsAsStream(
                "classpath*:org/hibernate/validator/*.properties",
                "ValidationMessages",
                ".properties",
                4,
                ".");
        return concat(defaultMessages, validationMessages).toArray(String[]::new);
    }

    private MessageContextHolder() {
        throw new AssertionError("No 'MessageContextHolder' instances for you!");
    }
}
/*
 1. By default, if no message is found for a specific code, a "NoSuchMessageException" should be thrown. This is
    important for the well functioning of the bean validation message interpolation mechanism.
*/
