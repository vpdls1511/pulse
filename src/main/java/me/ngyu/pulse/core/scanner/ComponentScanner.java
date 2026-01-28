package me.ngyu.pulse.core.scanner;

import me.ngyu.pulse.core.annotation.Component;
import me.ngyu.pulse.core.container.BeanContainer;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ComponentScanner {

  private final BeanContainer beanContainer;

  public ComponentScanner(BeanContainer beanContainer) {
    this.beanContainer = beanContainer;
  }

  public void scan(String packageName) throws Exception {
    // 1. 패키지를 경로로 변환
    String path = packageName.replace('.', '/');

    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    URL resource = classLoader.getResource(path);

    if (resource == null) {
      throw new IllegalArgumentException("Resource not found: " + path);
    }

    File dir = new File(resource.toURI());

    // 2. .class 파일 탐색
    List<Class<?>> classes = this.findClasses(dir, packageName);

    // 3. @Component 체크 및 등록
    for (Class<?> clazz : classes) {

      if (clazz.isAnnotation()) {
        continue;
      }

      if (hasComponentAnnotation(clazz)) {
        Object instance = clazz.getDeclaredConstructor().newInstance();
        beanContainer.register(clazz, instance);
      }
    }
  }

  private List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
    List<Class<?>> classes = new ArrayList<>();

    if (!directory.exists()) {
      return classes;
    }

    File[] files = directory.listFiles();

    if (files == null) {
      return classes;
    }

    for (File file : files) {
      if (file.isDirectory()) {
        // 하위 패키지 재귀탐색
        classes.addAll(findClasses(file, packageName + "." + file.getName()));
      } else if (file.getName().endsWith(".class")) {
        // .class 파일 -> Class 객체로 로드
        String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
        Class<?> clazz = Class.forName(className);
        classes.add(clazz);
      }
    }

    return classes;
  }

  private boolean hasComponentAnnotation(Class<?> clazz) {
    if (clazz.isAnnotationPresent(Component.class)) {
      return true;
    }

    for (Annotation annotation : clazz.getAnnotations()) {
      if (annotation.annotationType().getPackage().getName().startsWith("java.lang")) {
        continue;
      }

      if (annotation.annotationType().isAnnotationPresent(Component.class)) {
        return true;
      }
    }

    return false;
  }
}
