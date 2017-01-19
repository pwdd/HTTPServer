package com.github.pwdd.HTTPServer;

import com.github.pwdd.HTTPServer.cli.ArgumentsValidation;
import com.github.pwdd.HTTPServer.connectionHandlers.Server;

public class Runner {
  private static ServerArguments serverArguments;

  public Runner(ServerArguments _serverArguments) {
    serverArguments = _serverArguments;
  }

  public void start(String[] args) {
    ArgumentsValidation.exitOnInvalidArgs(args);
    int portNumber = serverArguments.getPortNumber(args);
    String rootDirectory = serverArguments.getRootDirectory(args);
    new Server(portNumber, rootDirectory, serverArguments.protocolFactory(), serverArguments.requestParser()).run();
  }
}
