package me.ngyu.pulse.server;

import me.ngyu.pulse.core.container.BeanContainer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PulseServer {

  private final int port;
  private final BeanContainer beanContainer;
  private final ExecutorService executor;

  public PulseServer(int port, BeanContainer beanContainer) {
    int threadPoolSize = Runtime.getRuntime().availableProcessors();
    executor = Executors.newFixedThreadPool(Math.max(threadPoolSize, 2));
    this.beanContainer = beanContainer;
    this.port = port;
  }

  public void run() throws IOException {

    Thread thread = new Thread(() -> {
      try (ServerSocket serverSocket = new ServerSocket(port)) {
        System.out.println("Pulse Server started on port " + port);

        while (true) {
          Socket socket = serverSocket.accept();
          new Thread(new RequestProcessor(socket, beanContainer)).start();
        }

      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });

    thread.start();
    System.out.println("Server thread started.");
  }

}
