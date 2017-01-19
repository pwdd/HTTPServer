package com.github.pwdd.HTTPServer.mocks;

import com.github.pwdd.HTTPServer.responders.IResponder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MockResponder implements IResponder {
  @Override
  public boolean canRespond(String fullURI) {
    return true;
  }

  @Override
  public InputStream header(String fullURI, String date) {
    return new ByteArrayInputStream("HTTP/1.1 200 OK\r\n\r\n".getBytes());
  }

  @Override
  public InputStream body(String fullURI) throws IOException {
    return new ByteArrayInputStream("Hello, world".getBytes());
  }
}
