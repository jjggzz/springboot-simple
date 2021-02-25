package com.springboot.simple.base.fun;

import java.util.Objects;

@FunctionalInterface
public interface ICustomer<T> {

    void accept(T t) throws Exception;

    default ICustomer<T> andThen(ICustomer<? super T> after) {
        Objects.requireNonNull(after);
        return (T t) -> { accept(t); after.accept(t); };
    }
}
