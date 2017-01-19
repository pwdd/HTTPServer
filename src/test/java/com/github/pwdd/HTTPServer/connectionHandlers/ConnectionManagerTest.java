package com.github.pwdd.HTTPServer.connectionHandlers;

import com.github.pwdd.HTTPServer.mocks.MockSocket;
import com.github.pwdd.HTTPServer.mocks.MockProtocolFactoryImpl;
import com.github.pwdd.HTTPServer.mocks.MockRequestParserImpl;
import com.github.pwdd.HTTPServer.protocols.AProtocolFactory;
import com.github.pwdd.HTTPServer.requestHandlers.IRequestParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ConnectionManagerTest {
  private Server server;
  private final AProtocolFactory factory = new MockProtocolFactoryImpl();
  private final IRequestParser requestParser = new MockRequestParserImpl();
  private final String rootDirectory = "src/test/java/com/pwdd/server/mocks/filesystem";

  @Before
  public void setUp() {
    final int portNumber = 8080;
    server = new Server(portNumber, rootDirectory, factory, requestParser);
  }

  @After
  public void tearDown() throws Exception  {
    server.stop();
  }

  @Test
  public void acceptsRequest() throws IOException {
    startServer();
    MockSocket mockSocket = new MockSocket();
    ConnectionManager connectionManager = new ConnectionManager(mockSocket, rootDirectory, requestParser, factory);
    String request = "GET / HTTP/1.1\r\nHost: localhost\r\n\r\n";
    String expected = request.trim();
    mockSocket.setRequestString(request);
    String requested = bufToString(connectionManager.getRequestFrom(mockSocket));
    assertEquals(expected, requested.trim());
  }

  @Test
  public void sendsAResponse() throws IOException {
    startServer();

    MockSocket mockSocket = new MockSocket();
    ConnectionManager connectionManager = new ConnectionManager(mockSocket, rootDirectory, requestParser, factory);
    connectionManager.sendResponseTo(mockSocket, new ByteArrayInputStream("foo".getBytes()));
    mockSocket.setStoredOutput();
    assertEquals("foo", mockSocket.storedOutput);
  }

  private void startServer() {
    try {
      server.listen();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private String bufToString(BufferedReader in) throws IOException {
    String crlf = "\r\n";
    StringBuilder builder = new StringBuilder();
    String line;

    while ((line = in.readLine()) != null && !line.equals("")) {
      builder.append(line).append(crlf);
    }
    return builder.toString();
  }
}
