package com.springboot.simple.base.fun;

import java.util.Objects;

@FunctionalInterface
public interface IPredicate<T> {

    boolean test(T t) throws Exception;

    default IPredicate<T> and(IPredicate<? super T> other) {
        Objects.requireNonNull(other);
        return (t) -> test(t) && other.test(t);
    }


    default IPredicate<T> negate() {
        return (t) -> !test(t);
    }


    default IPredicate<T> or(IPredicate<? super T> other) {
        Objects.requireNonNull(other);
        return (t) -> test(t) || other.test(t);
    }


    static <T> IPredicate<T> isEqual(Object targetRef) {
        return (null == targetRef)
                ? Objects::isNull
                : targetRef::equals;
    }
}
