## Spring Boot 원리 이해를 위한 Java 기반 WAS 구현

```text
Java 17, Gradle, ServerSocket
Reflection API, Annotation Processing
```

### 완성된 기능:
**HTTP 서버 (Blocking I/O)**
- ServerSocket 기반 멀티스레드 처리
- HTTP Request/Response 파싱 (Status Line, Headers, Body)
- Content-Type, 404/500 상태 코드 지원

**DI Container (IoC 핵심)**
- BeanContainer: 싱글톤 Bean 저장 (일급 컬렉션)
- ComponentScanner: @Component, @Controller 스캔 (Reflection)
- DependencyInjector: @Autowired 필드 주입
- 메타 애노테이션: @Controller → @Component 상속 구조

**자동 라우팅 시스템**
- @GetMapping: URL → 메서드 매핑
- HandlerMapper: Map<String, HandlerMethod> 관리
- ControllerScanner: @GetMapping 스캔 후 자동 등록
- switch문 제거, 선언적 라우팅 달성

