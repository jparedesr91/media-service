package com.newsnow.media.outside.driven.adapter.message.provider;

import java.util.Locale;

public class LocaleContextHolder {
    public static final Locale DEFAULT = Locale.ENGLISH;
    private static final String[] SUPPORTED_LANGUAGUES = new String[]{"es", "en"};

    static {
        org.springframework.context.i18n.LocaleContextHolder.setDefaultLocale(DEFAULT);
    }

    static public Locale getLocale() {
        return org.springframework.context.i18n.LocaleContextHolder.getLocale();
    }

    static public boolean isSupported(String language) {
        return SUPPORTED_LANGUAGUES[0].equals(language) || SUPPORTED_LANGUAGUES[1].equals(language);
    }

    static public boolean isSupported(Locale locale) {
        return null != locale && isSupported(locale.getLanguage());
    }

    private LocaleContextHolder() {
        throw new AssertionError("No 'LocaleContextHolder' instances for you!");
    }
}
