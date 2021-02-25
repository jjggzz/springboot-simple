package com.springboot.simple.base.fun;

import java.util.Objects;

/**
 * 支持抛出异常的Function
 * @param <T>
 * @param <R>
 */
@FunctionalInterface
public interface IFunction<T,R> {


    R apply(T t) throws Exception;


    default <V> IFunction<V, R> compose(IFunction<? super V, ? extends T> before) {
        Objects.requireNonNull(before);
        return (V v) -> apply(before.apply(v));
    }


    default <V> IFunction<T, V> andThen(IFunction<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (T t) -> after.apply(apply(t));
    }


    static <T> IFunction<T, T> identity() {
        return t -> t;
    }

}
