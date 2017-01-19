package com.github.pwdd.HTTPServer.connectionHandlers;

import com.github.pwdd.HTTPServer.protocols.AProtocol;
import com.github.pwdd.HTTPServer.protocols.AProtocolFactory;
import com.github.pwdd.HTTPServer.requestHandlers.IRequestParser;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

public class ConnectionManager implements Runnable {
  private final Socket socket;
  private final IRequestParser requestParser;
  private final String rootDirectory;
  private AProtocolFactory protocolFactory;

  ConnectionManager(
      Socket _socket,
      String _rootDirectory,
      IRequestParser _requestParser,
      AProtocolFactory _protocolFactory) {
    this.socket = _socket;
    this.requestParser = _requestParser;
    this.protocolFactory = _protocolFactory;
    this.rootDirectory = _rootDirectory;
  }

  BufferedReader getRequestFrom(Socket socket) throws IOException {
    return new BufferedReader(new InputStreamReader(socket.getInputStream()));
  }

  void sendResponseTo(Socket socket, InputStream response) throws IOException {
    synchronized (this) {
      byte[] buf = new byte[1024];
      int bytesRead;
      BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
      while ((bytesRead = response.read(buf)) != -1) {
        out.write(buf, 0, bytesRead);
      }
      response.close();
      out.flush();
      out.close();
    }
  }

  @Override
  public void run() {
    try {
      HashMap<String, String> request = requestParser.requestMap(getRequestFrom(socket));
      AProtocol protocol = protocolFactory.createProtocol(rootDirectory, request);
      sendResponseTo(socket, protocol.processResponse());
      socket.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
