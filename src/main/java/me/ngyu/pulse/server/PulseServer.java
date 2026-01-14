package me.ngyu.pulse.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PulseServer {

  private final int port;

  private final ExecutorService executor;

  public PulseServer(int port) {
    int threadPoolSize = Runtime.getRuntime().availableProcessors();
    executor = Executors.newFixedThreadPool(Math.max(threadPoolSize, 2));
    this.port = port;
  }

  public void run() throws IOException {

    Thread thread = new Thread(() -> {
      try (ServerSocket serverSocket = new ServerSocket(port)) {
        System.out.println("🚀 Pulse Server started on port " + port);

        while (true) {
          Socket socket = serverSocket.accept();
          new Thread(new RequestProcessor(socket)).start();
        }

      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });

    thread.start();
    System.out.println("✅ Server thread started.");
  }

}
