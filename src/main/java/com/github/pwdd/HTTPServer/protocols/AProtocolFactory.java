package com.github.pwdd.HTTPServer.protocols;

import java.util.HashMap;

public abstract class AProtocolFactory {
  public abstract AProtocol createProtocol(String rootDirectory, HashMap<String, String> request);
}
