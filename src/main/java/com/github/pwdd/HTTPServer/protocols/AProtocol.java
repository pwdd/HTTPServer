package com.github.pwdd.HTTPServer.protocols;

import java.io.InputStream;

public abstract class AProtocol {
  public abstract InputStream processResponse();
}
