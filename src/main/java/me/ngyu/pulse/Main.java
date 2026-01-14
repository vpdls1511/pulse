package me.ngyu.pulse;

import me.ngyu.pulse.server.PulseServer;

import java.io.IOException;

public class Main {
  public static void main(String[] args) throws IOException {
    new PulseServer(8080).run();
  }
}
