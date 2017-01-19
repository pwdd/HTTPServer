package com.github.pwdd.HTTPServer.mocks;

import com.github.pwdd.HTTPServer.protocols.AProtocol;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class MockProtocolImpl extends AProtocol {
  @Override
  public InputStream processResponse() {
    return new ByteArrayInputStream("HTTP/1.1 200 OK\r\n\r\n".getBytes());
  }
}
