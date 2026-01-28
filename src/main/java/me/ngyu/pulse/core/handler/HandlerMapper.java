package me.ngyu.pulse.core.handler;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class HandlerMapper {
  private final Map<String, HandlerMethod> handlers = new HashMap<>();

  public void register(String url, Object controller, Method method) {
    handlers.put(url, new HandlerMethod(controller, method));
  }

  public HandlerMethod getHandler(String url) {
    return handlers.get(url);
  }
}
