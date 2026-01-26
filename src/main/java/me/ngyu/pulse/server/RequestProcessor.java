package me.ngyu.pulse.server;

import me.ngyu.pulse.core.container.BeanContainer;
import me.ngyu.pulse.handler.PingHandler;
import me.ngyu.pulse.http.dto.HttpRequest;
import me.ngyu.pulse.http.dto.HttpResponse;
import me.ngyu.pulse.http.parser.HttpParser;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class RequestProcessor implements Runnable {

  private final Socket connection;
  private final BeanContainer beanContainer;

  public RequestProcessor(Socket connection, BeanContainer beanContainer) {
    this.connection = connection;
    this.beanContainer = beanContainer;
  }

  @Override
  public void run() {
    try {
      BufferedReader reader = new BufferedReader(
        new InputStreamReader(this.connection.getInputStream())
      );

      HttpRequest request = HttpParser.parse(reader);

      String response = this.route(request).build();

      OutputStream outputStream = this.connection.getOutputStream();
      outputStream.write(response.getBytes(StandardCharsets.UTF_8));

      outputStream.flush();
      this.connection.close();

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private HttpResponse route(HttpRequest request) {
    System.out.printf("[%s] %s%n", request.method().toUpperCase(), request.path());
    return switch (request.path()) {
      case "/ping" -> {
        PingHandler handler = beanContainer.getBean(PingHandler.class);
        yield HttpResponse.ok(request, handler.getResponse());
      }
      case "/pong" -> HttpResponse.ok(request, "<a> TEST - pong </a>");
      default -> HttpResponse.notFound(request);
    };
  }
}
