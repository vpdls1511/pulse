package me.ngyu.pulse.server;

import java.io.*;
import java.net.Socket;

public class RequestProcessor implements Runnable {

  private final Socket connection;

  public RequestProcessor(Socket connection) {
    this.connection = connection;
  }

  @Override
  public void run() {
    try {
      BufferedReader reader = new BufferedReader(
        new InputStreamReader(this.connection.getInputStream())
      );

      String line;
      while ((line = reader.readLine()) != null && !line.isEmpty()) {
        System.out.println(line);
      }

      OutputStream outputStream = this.connection.getOutputStream();
      outputStream.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());

      outputStream.flush();
      this.connection.close();

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
