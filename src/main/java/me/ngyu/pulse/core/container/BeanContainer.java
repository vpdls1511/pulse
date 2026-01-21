package me.ngyu.pulse.core.container;

import java.util.HashMap;
import java.util.Map;

/**
 * Bean을 저장하고 관리하는 컨테이너
 */
public class BeanContainer {

  private final Map<Class<?>, Object> beans = new HashMap<>();

  /**
   * Reflection 이 일어나면서 bean 이 등록된다
   *
   * @param clazz
   * @param instance
   */
  public void register(Class<?> clazz, Object instance) {
    beans.put(clazz, instance);
  }


  /**
   * Bean을 조회할 수 있다.
   * @param clazz
   * @return
   * @param <T>
   */
  public <T> T getBean(Class<T> clazz) {
    Object bean = beans.get(clazz);
    if (bean == null) {
      throw new IllegalArgumentException("No such bean: " + clazz);
    }
    return (T) beans.get(clazz);
  }

  /**
   * 모든 Bean을 조회한다.
   * @return
   */
  public Map<Class<?>, Object> getAllBeans() {
    return new HashMap<>(this.beans);
  }
}
