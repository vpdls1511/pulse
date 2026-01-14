package me.ngyu.pulse.http;

import java.io.BufferedReader;
import java.io.IOException;

public class HeaderParser {
  public static HttpHeaders parse(BufferedReader reader) throws IOException {
    HttpHeaders headers = new HttpHeaders();

    String headerLine;

    while ((headerLine = reader.readLine()) != null && !headerLine.isEmpty()) {
      String[] header = headerLine.split(":", 2);

      if (header.length == 2) {
        String key = header[0].trim();
        String value = header[1].trim();

        headers.add(key, value);
      }
    }

    return headers;
  }
}
