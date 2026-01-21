package me.ngyu.pulse.handler;

import me.ngyu.pulse.core.annotation.Component;

@Component
public class PingHandler {

  public PingHandler() {}

  public String getResponse() {
    return "<h1> Ping from DI Container</h1>";
  }
}
