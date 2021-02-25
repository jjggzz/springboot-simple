package com.springboot.simple.base.fun;

@FunctionalInterface
public interface ISupplier<T> {
    T get() throws Exception;
}
