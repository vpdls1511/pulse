package me.ngyu.pulse.http;

import java.util.*;

/**
 * HTTP 헤더를 관리하는 클래스.
 * 삽입 순서를 보장하기 위해 LinkedHashMap 사용.
 */
public class HttpHeaders {
  private final Map<String, List<String>> headers = new LinkedHashMap<>();

  public void add(String key, String value) {
    headers.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
  }

  public List<String> get(String key) {
    return headers.getOrDefault(key, Collections.emptyList());
  }

  public String getFirst(String key) {
    List<String> values = get(key);
    return values.isEmpty() ? null : values.get(0);
  }
}
