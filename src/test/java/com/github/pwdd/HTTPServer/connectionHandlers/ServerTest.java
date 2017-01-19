package com.github.pwdd.HTTPServer.connectionHandlers;

import com.github.pwdd.HTTPServer.mocks.MockProtocolFactoryImpl;
import com.github.pwdd.HTTPServer.mocks.MockRequestParser;
import com.github.pwdd.HTTPServer.protocols.AProtocolFactory;
import com.github.pwdd.HTTPServer.requestHandlers.IRequestParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ServerTest {
  private final String rootDirectory = "src/test/java/com/pwdd/server/mocks/filesystem";
  private final int portNumber = 8080;
  private final String hostName = "localhost";
  private final AProtocolFactory protocolFactory = new MockProtocolFactoryImpl();
  private final IRequestParser requestParser = new MockRequestParser();
  private Server server;
  private Thread thread;

  @Before
  public void setUp() {
    server = new Server(portNumber, rootDirectory, protocolFactory, requestParser);
    thread = new Thread(server);
    thread.start();
  }

  @After
  public void tearDown() throws Exception {
    server.stop();
    thread.join();
  }

  @Test
  public void acceptsConnection() throws Exception  {
    try (Socket client = new Socket(hostName, portNumber)) {
      assertTrue("Server accepts connection when serverSocket is listening", client.isConnected());
    } catch (Exception e) {
      e.printStackTrace();
    }

    server.stop();
    thread.join();

    try (Socket client = new Socket(hostName, portNumber)) {
      fail("Client should be unable to connect when server socket is closed");
    } catch (Exception e) {
      assertEquals("Connection refused", e.getMessage());
    }
  }

  @Test
  public void runServerTest() throws Exception {
    Socket client = new Socket(hostName, portNumber);
    BufferedReader clientIn = new BufferedReader(new InputStreamReader(client.getInputStream()));
    PrintWriter out = new PrintWriter(client.getOutputStream());
    out.print("GET / HTTP/1.1\r\nHost: localhost\r\n\r\n");
    out.flush();
    client.setSoTimeout(500);
    String in = clientIn.readLine();
    out.close();
    assertEquals("HTTP/1.1 200 OK", in);
  }
}
