package me.ngyu.pulse.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
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

      handleRequest(socket);
    }
  }

  private void handleRequest(Socket socket) throws IOException {
    BufferedReader reader = new BufferedReader(
      new InputStreamReader(socket.getInputStream())
    );

    String line;
    while ((line = reader.readLine()) != null && !line.isEmpty()) {
      System.out.println(line);
    }

    OutputStream outputStream = socket.getOutputStream();
    outputStream.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());

    outputStream.flush();
    socket.close();
  }
}
