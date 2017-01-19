package com.github.pwdd.HTTPServer.requestHandlers;

import java.io.BufferedReader;
import java.util.HashMap;

public interface IRequestParser {
  HashMap<String, String> requestMap(BufferedReader request);
}
