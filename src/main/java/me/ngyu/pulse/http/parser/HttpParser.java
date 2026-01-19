package me.ngyu.pulse.http.parser;

import me.ngyu.pulse.http.dto.HttpHeaders;
import me.ngyu.pulse.http.dto.HttpRequest;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpParser {
  public static HttpRequest parse(BufferedReader reader) throws IOException {
    String requestLine = reader.readLine();
    String[] parts = requestLine.split(" ");

    HttpHeaders headers = HeaderParser.parser(reader);

    return new HttpRequest(parts[0], parts[1], parts[2], headers);
  }
}
