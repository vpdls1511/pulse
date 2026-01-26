package me.ngyu.pulse.core.scanner;

import me.ngyu.pulse.core.annotation.Autowired;
import me.ngyu.pulse.core.container.BeanContainer;

import java.lang.reflect.Field;
import java.util.Map;

public class DependencyInjector {

  private final BeanContainer beanContainer;

  public DependencyInjector(BeanContainer beanContainer) {
    this.beanContainer = beanContainer;
  }

  public void inject() throws Exception {
    System.out.println("💉 Injecting dependencies...");  // 추가!

    for (Map.Entry<Class<?>, Object> entry : beanContainer.getAllBeans().entrySet()) {
      Object bean = entry.getValue();
      injectFields(bean);
    }

    System.out.println("✅ Dependency injection completed");  // 추가!
  }

  private void injectFields(Object bean) throws Exception {
    Class<?> clazz = bean.getClass();

    System.out.println("  🔍 Checking: " + clazz.getSimpleName());

    Field[] fields = clazz.getDeclaredFields();
    System.out.println("    Fields found: " + fields.length);

    for (Field field : fields) {
      System.out.println("    - " + field.getName() + " | @Autowired: " + field.isAnnotationPresent(Autowired.class));

      if (field.isAnnotationPresent(Autowired.class)) {
        Class<?> fieldType = field.getType();
        Object dependency = beanContainer.getBean(fieldType);

        field.setAccessible(true);
        field.set(bean, dependency);

        System.out.println("      ✅ Injected: " + fieldType.getSimpleName());
      }
    }
  }
}
