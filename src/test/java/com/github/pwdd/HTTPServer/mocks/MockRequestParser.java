package com.github.pwdd.HTTPServer.mocks;

import com.github.pwdd.HTTPServer.requestHandlers.IRequestParser;

import java.io.BufferedReader;
import java.util.HashMap;

public class MockRequestParser implements IRequestParser {

  @Override
  public HashMap<String, String> requestMap(BufferedReader request) {
    return new HashMap<String, String>() {{
      put("Method", "GET");
      put("URI", "/hello");
      put("Protocol", "HTTP/1.1");
    }};
  }
}
