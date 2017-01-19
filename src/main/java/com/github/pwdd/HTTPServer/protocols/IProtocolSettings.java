package com.github.pwdd.HTTPServer.protocols;

import java.util.HashMap;

public interface IProtocolSettings {
  String version();

  HashMap<String, String> statusCodes();
}
