package me.ngyu.pulse.core.scanner;

import me.ngyu.pulse.core.annotation.Controller;
import me.ngyu.pulse.core.annotation.HttpMethodMapping;
import me.ngyu.pulse.core.container.BeanContainer;
import me.ngyu.pulse.core.handler.HandlerMapper;
import me.ngyu.pulse.http.HttpMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class ControllerScanner {

  private final BeanContainer beanContainer;
  private final HandlerMapper handlerMapper;

  public ControllerScanner(BeanContainer beanContainer, HandlerMapper handlerMapper) {
    this.beanContainer = beanContainer;
    this.handlerMapper = handlerMapper;
  }

  public void scan() throws Exception {
    for (Object bean : beanContainer.getAllBeans().values()) {
      Class<?> clazz = bean.getClass();

      if (clazz.isAnnotationPresent(Controller.class)) {
        scanMethod(bean, clazz);
      }

    }
  }

  private void scanMethod(Object bean, Class<?> clazz) throws Exception {
    for (Method method : clazz.getDeclaredMethods()) {
      for (Annotation annotation : method.getAnnotations()) {
        Class<? extends Annotation> annotationType = annotation.annotationType();

        if(annotationType.isAnnotationPresent(HttpMethodMapping.class)) {
          HttpMethodMapping mapping = annotationType.getAnnotation(HttpMethodMapping.class);
          HttpMethod httpMethod = mapping.method();

          String url = getUrlFromAnnotation(annotation);

          handlerMapper.register(url, bean, method);
          System.out.println("[" + httpMethod + "] " + url + " → " + clazz.getSimpleName() + "." + method.getName());
        }

      }
    }
  }

  private String getUrlFromAnnotation(Annotation annotation) throws Exception {
    Method method = annotation.annotationType().getMethod("value");
    return (String) method.invoke(annotation);
  }

}
