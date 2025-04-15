package com.newsnow.media.outside.driving.api.exceptions;

import static com.newsnow.media.app.exceptions.errors.Error.err;
import static com.newsnow.media.app.exceptions.errors.ErrorCode.INVALID_INPUT;
import static com.newsnow.media.outside.driving.api.i18n.MessageContextHolder.msg;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.joining;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.internalServerError;
import static reactor.core.publisher.Mono.just;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newsnow.media.app.exceptions.errors.ErrorCode;
import com.newsnow.media.outside.driving.api.GenericResponseDTO;
import com.newsnow.media.outside.driving.api.GenericResponseDTO.ResponseStatusEnum;
import com.newsnow.media.outside.driving.api.mappers.WebMapper;
import jakarta.annotation.PostConstruct;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.codec.DecodingException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebInputException;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class GlobalExceptionHandler extends ExceptionManager<ResponseEntity<GenericResponseDTO>> implements ErrorWebExceptionHandler {

    private @Autowired(required=false)
    ResponseRegister<ResponseEntity<GenericResponseDTO>> register;

    private @Autowired ObjectMapper mapper;

    private @Autowired WebMapper MAPPER;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable tw) {
        var response = this.manage(tw);
        exchange.getResponse().setStatusCode(response.getStatusCode());
        var dataBuffer = exchange.getResponse().bufferFactory().wrap(Objects.requireNonNull(this.getBody(response)));
        return exchange.getResponse().writeWith(just(dataBuffer));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<?>> handleException(Exception ex) {
        return just(this.manage(ex));
    }

    @Override
    public void initialize(Map<Class<? extends Throwable>, Function<Throwable, ResponseEntity<GenericResponseDTO>>> responses) {
        responses.put(ServerWebInputException.class, ex -> this.buildResponse((ServerWebInputException) ex));
        responses.put(TypeMismatchException.class, ex -> this.buildResponse((TypeMismatchException) ex));
        responses.put(DecodingException.class, ignored -> badRequest().body(MAPPER.toGenericResponseDTO(
            ResponseStatusEnum.ERROR, Collections.singletonList(err(INVALID_INPUT, msg("seed.error.rest.malformed_message"))))));
        responses.put(UnsupportedMediaTypeStatusException.class, ex -> this.buildResponse((UnsupportedMediaTypeStatusException) ex));
        responses.put(WebExchangeBindException.class, ex -> this.buildResponse((WebExchangeBindException) ex));
    }

    @PostConstruct
    public void initializeCustomizedResponses() {
        if (null != register) {
            register.getResponses().forEach(getResponses()::put);
        }
    }

    @Override
    public ResponseEntity<GenericResponseDTO> getDefaultResponse() {
        return internalServerError()
            .body(MAPPER.toGenericResponseDTO(ResponseStatusEnum.ERROR, Collections.singletonList(err(ErrorCode.INTERNAL_ERROR, msg("seed.error.internal_error")))));
    }

    private ResponseEntity<GenericResponseDTO> buildResponse(ServerWebInputException ex) {
        return null != ex.getCause() && this.isMapped(ex.getCause())
                ? this.manage(ex.getCause())
                : badRequest().body(MAPPER.toGenericResponseDTO(ResponseStatusEnum.ERROR, Collections.singletonList(err(INVALID_INPUT, msg("seed.error.rest.invalid_input")))));
    }

    private ResponseEntity<GenericResponseDTO> buildResponse(TypeMismatchException ex) {
        return badRequest().body(MAPPER.toGenericResponseDTO(ResponseStatusEnum.ERROR, Collections.singletonList(err(ErrorCode.INVALID_FORMAT, msg("seed.error.rest.invalid_value", ex.getValue(), ex.getRequiredType())))));
    }

    private ResponseEntity<GenericResponseDTO> buildResponse(UnsupportedMediaTypeStatusException ex) {
        return badRequest().body(MAPPER.toGenericResponseDTO(ResponseStatusEnum.ERROR, Collections.singletonList(err(INVALID_INPUT, msg("seed.error.rest.unsupported_media_type", ex.getContentType(), ex.getSupportedMediaTypes())))));
    }

    private ResponseEntity<GenericResponseDTO> buildResponse(WebExchangeBindException ex) {
        var params = ex.getFieldErrors().stream()
                .map(FieldError::getField)
                .collect(joining(", "));
        return badRequest().body(MAPPER.toGenericResponseDTO(ResponseStatusEnum.ERROR, Collections.singletonList(err(ErrorCode.INVALID_FORMAT, msg("seed.error.rest.invalid_type_params", params)))));
    }

    private byte[] getBody(ResponseEntity<GenericResponseDTO> response) {
        try {
            var body = mapper.writeValueAsString(response.getBody());
            return body.getBytes(UTF_8);
        } catch (Exception ex) {
            logger.error("Unexpected exception when trying to wrap an error response", ex);
            return null;
        }
    }
}
