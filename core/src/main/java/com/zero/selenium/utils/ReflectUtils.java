package com.zero.selenium.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@SuppressWarnings("unchecked")
public final class ReflectUtils {

  public static <P> P createInstance(Class<P> clazz) {
    try {
      Constructor<P> constructor = clazz.getDeclaredConstructor();
      constructor.setAccessible(true);
      return constructor.newInstance();
    } catch (InstantiationException | SecurityException | IllegalAccessException
        | IllegalArgumentException | NoSuchMethodException | InvocationTargetException e) {
      throw new IllegalStateException("Failed to create an instance of " + clazz.getName(), e);
    }
  }

  public static <I, R> R executeMethod(I instance, String methodName) {
    try {
      Method method = instance.getClass().getDeclaredMethod(methodName);
      method.setAccessible(true);
      return (R) method.invoke(instance);
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
        | NoSuchMethodException | SecurityException e) {
      throw new IllegalStateException("Failed to execute method '" + methodName + "' of instance "
          + instance.getClass().getName(), e);
    }
  }

  public static <I, R> R executeMethod(I instance, String methodName, Class<?>[] parameterTypes,
      Object[] parameter) {
    try {
      Method method = instance.getClass().getDeclaredMethod(methodName, parameterTypes);
      method.setAccessible(true);
      return (R) method.invoke(instance, parameter);
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
        | NoSuchMethodException | SecurityException e) {
      throw new IllegalStateException("Failed to execute method '" + methodName + "' of instance "
          + instance.getClass().getName(), e);
    }
  }

}
