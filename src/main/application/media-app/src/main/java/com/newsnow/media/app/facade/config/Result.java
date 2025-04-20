package com.newsnow.media.app.facade.config;

import static java.util.Objects.requireNonNull;
import com.newsnow.media.app.exceptions.errors.Error;


import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import lombok.Getter;

/**
 * Represents the result the <b>facade</b> layer classes emits from its business operations. Encapsulates a detailed
 * response ready to be served for any distribution mode.
 *
 * @param <R> The type of the val wrapped within the result.
 *
 */
@Getter
public class Result<R> {
    private List<Error> errors;
    private R val;
    private boolean success;

    public Result() {}

    public static <O> Result<O> successful() {
        return successful(null);
    }

    public static <O> Result<O> successful(O result) {
        return new Result<O>().success(result);
    }

    public static <O>  Result<O> failed(Error error) {
        return failed(List.of(error));
    }

    public static <O>  Result<O> failed(List<Error> errors) {
        return new Result<O>().failure(errors);
    }

    public void ifSuccess(Consumer<R> successCallback) {
        if (success) {
            successCallback.accept(val);
        }
    }

    public void ifSuccessOrElse(Consumer<R> successCallback, Consumer<List<Error>> errorCallback) {
        if (success) {
            successCallback.accept(val);
        } else {
            errorCallback.accept(errors);
        }
    }

    public <V> Result<V> map(Function<? super R, ? extends V> mapper) {
        requireNonNull(mapper, "The mapping function must not be null");
        if (!success)
            throw new IllegalStateException("The result isn't success");
        var result = new Result<V>();
        result.success = true;
        result.val = mapper.apply(this.val);
        return result;
    }

    @SuppressWarnings("unchecked")
    public <V> Result<V> flatMap(Function<? super R, ? extends Result<? extends V>> mapper) {
        requireNonNull(mapper, "The mapping function must not be null");
        if (!success)
            throw new IllegalStateException("The result isn't success");
        return (Result<V>) mapper.apply(val);
    }

    private Result<R> success(R val) {
        this.val = val;
        this.success = true;
        return this;
    }

    private Result<R> failure(List<Error> errors) {
        this.errors = errors;
        this.success = false;
        return this;
    }

}
