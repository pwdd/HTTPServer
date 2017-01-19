package com.github.pwdd.HTTPServer.connectionHandlers;

import com.github.pwdd.HTTPServer.protocols.AProtocolFactory;
import com.github.pwdd.HTTPServer.requestHandlers.IRequestParser;

import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Server implements Runnable {
  private Boolean listening = false;
  private ServerSocket serverSocket;
  private final int portNumber;
  private final String rootDirectory;
  private final AProtocolFactory protocolFactory;
  private final ExecutorService pool = Executors.newFixedThreadPool(4);
  private final IRequestParser requestParser;

  public Server(int _portNumber, String _rootDirectory, AProtocolFactory _protocolFactory, IRequestParser _requestParser) {
    this.portNumber = _portNumber;
    this.rootDirectory = _rootDirectory;
    this.protocolFactory = _protocolFactory;
    this.requestParser = _requestParser;
  }

  void listen() throws Exception {
    serverSocket = new ServerSocket(portNumber);
  }

  @Override
  public void run() {
    interceptTermination();
    try {
      listen();
      listening = true;
      while (listening) {
        pool.execute(new ConnectionManager(
            serverSocket.accept(),
            rootDirectory,
            requestParser,
            protocolFactory));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  void stop() throws Exception {
    serverSocket.close();
    listening = false;
  }

  private void waitTermination() {
    pool.shutdown();
    try {
      if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
        pool.shutdownNow();
      }
    } catch (InterruptedException ie) {
      pool.shutdownNow();
      Thread.currentThread().interrupt();
    }
  }

  private void interceptTermination() {
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        waitTermination();
      }
    });
  }
}
