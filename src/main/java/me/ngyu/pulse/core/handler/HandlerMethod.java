package me.ngyu.pulse.core.handler;

import java.lang.reflect.Method;

public record HandlerMethod(
  Object controller,
  Method method
) {
  public Object invoke(Object... args) throws Exception {
    return method.invoke(controller, args);
  }

}
