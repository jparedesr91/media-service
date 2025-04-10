package com.newsnow.media.lib.reactive;

import java.util.function.Function;

public interface CollectionReactive<T> {
   <U> CollectionReactive<U> map(Function<? super T, ? extends U> mapper);
}

