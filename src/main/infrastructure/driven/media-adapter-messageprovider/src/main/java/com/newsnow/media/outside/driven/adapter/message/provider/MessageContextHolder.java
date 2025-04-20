package com.newsnow.media.outside.driven.adapter.message.provider;

import static com.newsnow.media.outside.driven.adapter.message.provider.LocaleContextHolder.getLocale;
import static java.util.stream.Stream.concat;

import com.newsnow.media.app.ports.driven.message.MessageProvider;
import com.newsnow.media.outside.driven.adapter.message.provider.util.FileUtils;
import java.util.stream.Stream;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class MessageContextHolder implements MessageProvider {
    private static final MessageSource SOURCE;

    static {
        SOURCE = new ResourceBundleMessageSource();
        ((ResourceBundleMessageSource)SOURCE).setBasenames(getPropertiesFilesPaths());
        ((ResourceBundleMessageSource)SOURCE).setDefaultEncoding("UTF-8");
    }

    public String msg(String code) {
        return SOURCE.getMessage(code, null, getLocale());
    }

    public String msg(String code, Object... args) {
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

}