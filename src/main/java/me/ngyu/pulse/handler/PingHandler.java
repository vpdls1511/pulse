package me.ngyu.pulse.handler;

 import me.ngyu.pulse.core.annotation.Autowired;
import me.ngyu.pulse.core.annotation.Component;
 import me.ngyu.pulse.core.annotation.Controller;
 import me.ngyu.pulse.core.annotation.GetMapping;
 import me.ngyu.pulse.handler.service.TestService;
 import me.ngyu.pulse.http.dto.HttpRequest;
 import me.ngyu.pulse.http.dto.HttpResponse;

@Controller
public class PingHandler {

  @Autowired
  private TestService testService;

  public PingHandler() {}

  @GetMapping("/ping")
  public HttpResponse getResponse(HttpRequest request) {
    testService.test();
    return HttpResponse.ok(request, "<h1>Ping from DI Container!</h1>");
  }
}
