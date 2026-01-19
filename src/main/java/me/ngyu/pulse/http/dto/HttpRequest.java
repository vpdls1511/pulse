package me.ngyu.pulse.http.dto;

public record HttpRequest(
  String method,
  String path,
  String version,
  HttpHeaders headers
) {}
