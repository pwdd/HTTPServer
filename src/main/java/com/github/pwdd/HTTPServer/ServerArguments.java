package com.github.pwdd.HTTPServer;

import com.github.pwdd.HTTPServer.cli.ArgumentsValidation;
import com.github.pwdd.HTTPServer.protocols.AProtocolFactory;
import com.github.pwdd.HTTPServer.requestHandlers.IRequestParser;
import com.github.pwdd.HTTPServer.requestHandlers.RequestParser;

public abstract class ServerArguments {
  public abstract AProtocolFactory protocolFactory();

  public IRequestParser requestParser() { return new RequestParser(); }

  public int getPortNumber(String[] args) {
    return Integer.parseInt(ArgumentsValidation.getPortNumber(args));
  }

  public String getRootDirectory(String[] args) {
    String directory = ArgumentsValidation.getDirectory(args);
    return directory;
  }
}
