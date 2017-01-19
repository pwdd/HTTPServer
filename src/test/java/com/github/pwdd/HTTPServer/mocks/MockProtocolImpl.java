package com.github.pwdd.HTTPServer.mocks;

import com.github.pwdd.HTTPServer.protocols.AProtocol;
import com.github.pwdd.HTTPServer.responders.IResponder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;

public class MockProtocolImpl extends AProtocol {
  MockProtocolImpl(String rootDirectory, HashMap<String, String> request, IResponder[] responders) {
    super(rootDirectory, request, responders);
  }

  public InputStream errorMessage() { return new ByteArrayInputStream("HTTP/1.1 404 Not Found".getBytes()); }
}
