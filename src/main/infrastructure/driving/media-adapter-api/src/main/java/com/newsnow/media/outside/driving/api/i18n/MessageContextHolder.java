package com.newsnow.media.outside.driving.api.i18n;


import static com.newsnow.media.outside.driving.api.i18n.LocaleContextHolder.getLocale;
import static java.util.stream.Stream.concat;

import com.newsnow.media.outside.driving.api.util.FileUtils;
import java.util.stream.Stream;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;

public class MessageContextHolder {
    private static final MessageSource SOURCE;

    static {
        SOURCE = new ResourceBundleMessageSource();
        ((ResourceBundleMessageSource)SOURCE).setBasenames(getPropertiesFilesPaths());
        ((ResourceBundleMessageSource)SOURCE).setDefaultEncoding("UTF-8");
    }

    static public String msg(String code) {
        return SOURCE.getMessage(code, null, getLocale());
    }

    static public String msg(String code, Object... args) {
        return SOURCE.getMessage(code, 0 != args.length ? args : null, getLocale());
    }

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