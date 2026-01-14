package me.ngyu.pulse.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PulseServer {

  private final int port;

  public PulseServer(int port) {
    this.port = port;
  }

  public void run() throws IOException {
    ServerSocket serverSocket = new ServerSocket(port);
    System.out.println("Pulse server started on port " + port);

    while (true) {
      Socket socket = serverSocket.accept();
      socket.close();
    }
  }

}
