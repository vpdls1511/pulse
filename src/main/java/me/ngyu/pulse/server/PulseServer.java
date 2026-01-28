package me.ngyu.pulse.server;

import me.ngyu.pulse.core.container.BeanContainer;
import me.ngyu.pulse.core.handler.HandlerMapper;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PulseServer {

  private final int port;
  private final BeanContainer beanContainer;
  private final HandlerMapper handlerMapper;

  private final ExecutorService executor;

  public PulseServer(int port, BeanContainer beanContainer, HandlerMapper handlerMapper) {
    int threadPoolSize = Runtime.getRuntime().availableProcessors();
    executor = Executors.newFixedThreadPool(Math.max(threadPoolSize, 2));

    this.port = port;
    this.beanContainer = beanContainer;
    this.handlerMapper = handlerMapper;
  }

  public void run() {
    try (ServerSocket serverSocket = new ServerSocket(port)) {
      System.out.println("Pulse Server started on port " + port);

      while (true) {
        Socket socket = serverSocket.accept();
        executor.submit(new RequestProcessor(socket, beanContainer, handlerMapper));
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
