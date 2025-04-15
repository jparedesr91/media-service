package com.newsnow.media.app.facade.config;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import lombok.Getter;
import reactor.core.publisher.Mono;

/**
 */
@Getter
public final class MediaServiceContext {

    final Locale locale;
    final HashMap<String, List<String>> headers;

    private MediaServiceContext(Locale locale, HashMap<String, List<String>> headers) {
        this.locale = locale;
        this.headers = headers;
    }

    static public MediaServiceContext newInstance() {
        return new MediaServiceContext(Locale.ENGLISH, new HashMap<>());
    }

  public Mono<Locale> getMonoLocale() {
        return Mono.just(locale);
    }

    public MediaServiceContext setLocale(Locale lcl) {
        return new MediaServiceContext(lcl, headers);
    }

    public MediaServiceContext setHeaders(HashMap<String, List<String>> headers) {
        return new MediaServiceContext(locale, headers);
    }

}
