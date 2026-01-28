package me.ngyu.pulse.server;

import me.ngyu.pulse.core.container.BeanContainer;
import me.ngyu.pulse.core.handler.HandlerMapper;
import me.ngyu.pulse.core.handler.HandlerMethod;
import me.ngyu.pulse.handler.PingHandler;
import me.ngyu.pulse.http.HttpMethod;
import me.ngyu.pulse.http.dto.HttpRequest;
import me.ngyu.pulse.http.dto.HttpResponse;
import me.ngyu.pulse.http.parser.HttpParser;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class RequestProcessor implements Runnable {

  private final Socket connection;
  private final BeanContainer beanContainer;
  private final HandlerMapper handlerMapper;

  public RequestProcessor(Socket connection, BeanContainer beanContainer, HandlerMapper handlerMapper) {
    this.connection = connection;
    this.beanContainer = beanContainer;
    this.handlerMapper = handlerMapper;
  }

  @Override
  public void run() {
    try (
      Socket socket = this.connection;
      BufferedReader reader = new BufferedReader(
        new InputStreamReader(socket.getInputStream())
      );
      OutputStream output = socket.getOutputStream()
    ) {
      HttpRequest request = HttpParser.parse(reader);

      if (request == null) {
        return;
      }

      String response = this.route(request).build();

      output.write(response.getBytes(StandardCharsets.UTF_8));
      output.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private HttpResponse route(HttpRequest request) {
    System.out.printf("[%s] %s%n", request.method().toUpperCase(), request.path());

    HandlerMethod handler = handlerMapper.getHandler(request.path());

    if (handler == null) {
      return HttpResponse.notFound(request);
    }

    try {
      return (HttpResponse) handler.invoke(request);
    } catch (Exception e) {
      e.printStackTrace();
      return HttpResponse.serverError(request);
    }
  }
}
