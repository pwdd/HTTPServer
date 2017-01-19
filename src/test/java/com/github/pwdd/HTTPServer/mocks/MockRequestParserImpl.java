package com.github.pwdd.HTTPServer.mocks;

import com.github.pwdd.HTTPServer.requestHandlers.IRequestParser;

import java.io.BufferedReader;
import java.util.HashMap;

public class MockRequestParserImpl implements IRequestParser {
  @Override
  public HashMap<String, String> requestMap(BufferedReader request) {
    return null;
  }
}
