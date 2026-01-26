package me.ngyu.pulse.handler;

 import me.ngyu.pulse.core.annotation.Autowired;
import me.ngyu.pulse.core.annotation.Component;
import me.ngyu.pulse.handler.service.TestService;

@Component
public class PingHandler {

  @Autowired
  private TestService testService;

  public PingHandler() {}

  public String getResponse() {
    testService.test();
    return "<h1> Ping from DI Container</h1>";
  }
}
