package com.newsnow.media.outside.driving.api.adapters;

import static com.newsnow.media.domain.exceptions.errors.ErrorCode.Series.VALIDATION;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import com.newsnow.media.domain.exceptions.errors.ErrorCode;
import com.newsnow.media.domain.facade.MediaServiceContext;
import com.newsnow.media.domain.facade.Result;
import com.newsnow.media.outside.driving.api.GenericResponseDTO;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.function.BiFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import static org.springframework.http.ResponseEntity.*;
import static com.newsnow.media.outside.driving.api.mappers.WebMapper.MAPPER;

/**
 * Wrapper controller for Webflux approach.
 *
 * @param <F> The facade interface to be used by the controller to perform the business operations.
 */
public abstract class Controller<F> {

    private static final ResponseEntity<GenericResponseDTO> NOT_CONTENT = noContent().build();
    private static final ResponseEntity<GenericResponseDTO> NOT_FOUND = notFound().build();
    private @Autowired F facade;


    private Mono<MediaServiceContext> generateContext(ServerWebExchange exchange) {
        return Mono.just(MediaServiceContext.newInstance())
                    .map(context -> context.setHeaders(getHeaders(exchange)))
                    .map(context -> context.setLocale(getLocale(exchange)))
                    .switchIfEmpty(Mono.just(MediaServiceContext.newInstance()));
    }

    private Locale getLocale(ServerWebExchange exchange) {
        try {
            if (exchange.getRequest().getQueryParams().containsKey("lang")) {
                if (!exchange.getRequest().getQueryParams().get("lang").isEmpty()) {
                    if (exchange.getRequest().getQueryParams().get("lang").getFirst() != null) {
                        StringTokenizer langTokenizer = new StringTokenizer(exchange.getRequest().getQueryParams().get("lang").getFirst(), "-");
                        if (langTokenizer.hasMoreTokens()) {
                            String lang = langTokenizer.nextElement().toString();
                            if (langTokenizer.hasMoreTokens()) {
                                return Locale.of(lang, langTokenizer.nextElement().toString());
                            }
                            return Locale.of(lang);
                        }
                    }
                }
            }
        } catch (Exception ignored) {
        }
        return exchange.getLocaleContext().getLocale() != null ? exchange.getLocaleContext().getLocale() : Locale.ENGLISH;
    }

    private HashMap<String, List<String>> getHeaders(ServerWebExchange exchange) {
        Set<Map.Entry<String, List<String>>> entries = exchange.getRequest().getHeaders().entrySet();
        HashMap<String,List<String>> hashMap = new HashMap<>();
        entries.forEach(entry -> hashMap.put(entry.getKey().toLowerCase(), entry.getValue()));
        return hashMap;
    }

    protected final <V> Mono<ResponseEntity<GenericResponseDTO>> perform(BiFunction<F, MediaServiceContext, Mono<Result<V>>> function, ServerWebExchange exchange) {
        return generateContext(exchange)
            .flatMap(context -> function.apply(this.facade, context)
                .map(result -> result.isSuccess()
                    ? ok(MAPPER.toGenericResponseDTO("success", result))
                    : this.getResponse(result)));
    }

    private ResponseEntity<GenericResponseDTO> getResponse(Result<?> result) {
        return result.getErrors().stream()
                .map(error -> error.getCode().series())
                .reduce(this::reduce)
                .map(code -> {
                  if (code == VALIDATION) {
                    return result.getErrors().stream()
                        .anyMatch(error -> error.getCode() == ErrorCode.INVALID_REFERENCE)
                        ? status(NOT_FOUND.getStatusCode()).body(MAPPER.toGenericResponseDTO("error", result))
                        : badRequest().body(MAPPER.toGenericResponseDTO("error", result));
                  }
                  return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                      .body(MAPPER.toGenericResponseDTO("error", result));
                })
                .orElse(ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(MAPPER.toGenericResponseDTO("error", result)));
    }

    private ErrorCode.Series reduce(ErrorCode.Series series1, ErrorCode.Series series2) {
        if ((series1 == VALIDATION) || (series2 == VALIDATION)) {
            return series1 == VALIDATION ? series1 : series2;
        } else {
            return series1;
        }
    }
}
