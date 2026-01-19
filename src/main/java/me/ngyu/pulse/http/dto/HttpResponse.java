package me.ngyu.pulse.http.dto;

public record HttpResponse(
  String version,
  int statusCode,
  String reasonPhrase,
  HttpHeaders headers,
  String body
) {
  public static HttpResponse ok(HttpRequest request, String body) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "text/html; charset=utf-8");
    return new HttpResponse(request.version(), 200, "OK", headers, body);
  }

  public static HttpResponse notFound(HttpRequest request) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "text/html; charset=utf-8");
    String body = """
        <html>
        <body>
            <h1>404 Not Found</h1>
            <p>The page you requested does not exist.</p>
        </body>
        </html>
        """;
    return new HttpResponse(request.version(), 404, "Not Found", headers, body);
  }

  /**
   * HTTP 통신규약에 맞춰 응답을 내려주기 위해 구분한다.
   *
   * <ol>
   *   <li>Status Line
   *     <ul>
   *       <li>HTTP Version, Status Code, Reason Phrase</li>
   *     </ul>
   *   </li>
   *
   *   <li>Headers
   *     <ul>
   *       <li>Content-Type, Content-Length, ...</li>
   *     </ul>
   *   </li>
   *
   *   <li>Body
   *     <ul>
   *       <li>HTML Document or JSON Data or Resource, ...</li>
   *     </ul>
   *   </li>
   * </ol>
   */
  public String build() {
    StringBuilder sb = new StringBuilder();

    // Status Line
    sb.append(this.version()).append(" ")
      .append(this.statusCode()).append(" ")
      .append(this.reasonPhrase()).append("\r\n");

    // Headers
    this.headers()
      .getAll()
      .forEach((key, values) ->
        values.forEach(value ->
          sb.append(key).append(": ").append(value).append("\r\n")
        )
      );

    // 빈 줄
    sb.append("\r\n");

    // Body
    sb.append(this.body());

    return sb.toString();
  }
}
