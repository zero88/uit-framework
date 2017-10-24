package com.zero.selenium.config;

/**
 * Functional Constructor which creates a new Instance
 */
@FunctionalInterface
public interface FunctionalConstructor<T> {

  T newInstance(Object... arguments);

}
