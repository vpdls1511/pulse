package me.ngyu.pulse.http.parser;

import me.ngyu.pulse.http.dto.HttpHeaders;

import java.io.BufferedReader;
import java.io.IOException;

public class HeaderParser {
  public static HttpHeaders parser(BufferedReader reader) throws IOException {
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


