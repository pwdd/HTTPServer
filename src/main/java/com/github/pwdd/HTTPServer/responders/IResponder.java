package com.github.pwdd.HTTPServer.responders;

import java.io.IOException;
import java.io.InputStream;

public interface IResponder {
  String CRLF = "\r\n";

  boolean canRespond(String fullURI);

  InputStream header(String fullURI, String date);

  InputStream body(String fullURI) throws IOException;
}
