package com.newsnow.media.lib.reactive;

import java.util.function.Function;

public interface UnitReactive<T> {
    <U> UnitReactive<U> map(Function<? super T, ? extends U> mapper);
    <U> UnitReactive<U> flatMap(Function<? super T, ? extends UnitReactive<? extends U>> transformer);
    <U> UnitReactive<U> error(Throwable t);
}