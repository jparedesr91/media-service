package com.newsnow.media.outside.driving.api.exceptions;

import static com.newsnow.media.app.exceptions.errors.Error.err;
import static com.newsnow.media.app.exceptions.errors.ErrorCode.INTERNAL_ERROR;
import static com.newsnow.media.app.exceptions.errors.ErrorCode.INVALID_INPUT;
import static com.newsnow.media.app.exceptions.errors.ErrorCode.INVALID_REFERENCE;
import static com.newsnow.media.app.exceptions.errors.ErrorCode.NOT_IMPLEMENTED_YET;
import static com.newsnow.media.app.facade.config.Result.failed;
import static reactor.core.publisher.Mono.just;

import com.newsnow.media.app.exceptions.DataIntegrityException;
import com.newsnow.media.app.exceptions.DataSourceCommunicationException;
import com.newsnow.media.app.exceptions.DuplicatedEntityException;
import com.newsnow.media.app.exceptions.EntityNotFoundException;
import com.newsnow.media.app.exceptions.ValidationException;
import com.newsnow.media.app.exceptions.errors.ErrorCode;
import com.newsnow.media.app.facade.config.Result;
import com.newsnow.media.app.ports.driven.message.MessageProvider;
import jakarta.annotation.PostConstruct;
import java.util.Map;
import java.util.function.Function;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Aspect
@Component
public class FacadeExceptionHandler extends ExceptionManager<Mono<Result>> {

    private @Autowired(required = false) ResponseRegister<Mono<Result>> register;
    private final MessageProvider messageProvider;

  public FacadeExceptionHandler(MessageProvider messageProvider) {
    this.messageProvider = messageProvider;
  }


  @Override
    public void initialize(Map<Class<? extends Throwable>, Function<Throwable, Mono<Result>>> responses) {
        responses.put(UnsupportedOperationException.class, tw -> just(failed(err(NOT_IMPLEMENTED_YET, messageProvider.msg("seed.error.unsupported_operation")))));
        responses.put(IllegalArgumentException.class, tw -> this.getResponse((IllegalArgumentException) tw));
        responses.put(DataIntegrityException.class, tw -> this.getResponse((DataIntegrityException) tw));
        responses.put(DataSourceCommunicationException.class, tw -> this.getResponse((DataSourceCommunicationException) tw, INTERNAL_ERROR));
        responses.put(DuplicatedEntityException.class, tw -> this.getResponse((DuplicatedEntityException) tw));
        responses.put(EntityNotFoundException.class, tw -> this.getResponse((EntityNotFoundException) tw, INVALID_REFERENCE));
        responses.put(ValidationException.class, tw -> this.getResponse((ValidationException) tw));
    }

    @Override
    public Mono<Result> getDefaultResponse() {
        return just(failed(err(INTERNAL_ERROR, messageProvider.msg("seed.error.internal_error"))));
    }

    @SuppressWarnings("unchecked")
    @Around(
            "execution(reactor.core.publisher.Mono com.newsnow.media.app.*FacadeImpl*+.*(..))"
    )
    public Object intercept(ProceedingJoinPoint procedure) {
        try {
            return ((Mono<Result>) procedure.proceed()).onErrorResume(this::manage);
        } catch (Throwable tw) {
          if (logger.isDebugEnabled()) {
            logger.debug("Unbounded exception handled at facade level", tw);
          }
          return this.manage(tw);
        }
    }

    @PostConstruct
    public void initializeCustomizedResponses() {
        if (null != register) {
            register.getResponses().forEach((ex, response) -> this.getResponses().put(ex, response));
        }
    }

    private Mono<Result> getResponse(RuntimeException ex) {
        return this.getResponse(ex, INVALID_INPUT);
    }

    private Mono<Result> getResponse(RuntimeException ex, ErrorCode code) {
        return just(failed(err(code, ex.getMessage())));
    }

    private Mono<Result> getResponse(ValidationException ex) {
        return just(failed(err(ex.getCode(), ex.getMessage())));
    }
}
